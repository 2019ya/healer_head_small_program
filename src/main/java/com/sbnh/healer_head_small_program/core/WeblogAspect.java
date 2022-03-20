package com.sbnh.healer_head_small_program.core;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sbnh.healer_head_small_program.bean.convert.StringIdGsonSerializer;
import com.sbnh.healer_head_small_program.bean.convert.SysTimeGsonSerializer;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Description 日志切面
 * @Author Liutong
 * @Date 2020/9/23 13:07
 */
@Aspect
@Slf4j
public class WeblogAspect {

    private static final String LINE_SEPARATOR = System.lineSeparator();
    private final static Gson GSON = new GsonBuilder()
            .registerTypeAdapter(String.class, StringIdGsonSerializer.INSTANCE)
            .registerTypeAdapter(Long.class, SysTimeGsonSerializer.INSTANCE)
            .create();
    /**
     * 慢查询监测时间
     */
    private final static int SLOW_REQUEST_TIME = 500;
    private final static Map<String, Boolean> CACHE_PRINT = Maps.newHashMap();

    /**
     * 记录接口请求开始时间
     */
    private static ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("within(com.sbnh.healer_head_small_program.controller..*)")
    public void controllerLog() {
    }

    @Around("controllerLog()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //记录请求开始时间
        startTime.set(System.currentTimeMillis());
        //生产环境不打印
        //检测是否需要打印
        if (needlessPrint(joinPoint)) {
            return joinPoint.proceed();
        }
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 打印请求相关参数
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("\n================================= Start ============================ \n");
        // 打印请求 url
        stringBuilder.append(String.format("URL            : %s", request.getRequestURL().toString())).append("\n");
        // 打印 Http method
        stringBuilder.append(String.format("HTTP Method    : %s", request.getMethod())).append("\n");
        // 打印调用 controller 的全路径以及执行方法
        stringBuilder.append(String.format("Class Method   : %s.%s", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName())).append("\n");
        // 打印请求的 IP
        stringBuilder.append(String.format("IP             : %s", request.getRemoteAddr())).append("\n");
        // 打印请求用户sessionId信息
        stringBuilder.append(String.format("Cookie         : %s", request.getHeader("Cookie"))).append("\n");
        // 打印请求用户sessionId信息
        stringBuilder.append(String.format("XBCookie       : %s", request.getHeader("XBCookie"))).append("\n");
        // 打印请求入参
        stringBuilder.append(String.format("Request Args   : %s", GSON.toJson(joinPoint.getArgs()))).append("\n");

        // 执行切入方法，打印请求出参
        Object ret = joinPoint.proceed();
        //截取128位字符
        String result = GSON.toJson(ret);
        if (result.length() >= 128) {
            result = GSON.toJson(ret).substring(0, 127);
        }
        stringBuilder.append(String.format("Response Args   : %s", result)).append("\n");
        //记录当前请求所消耗时间
        long costTime = System.currentTimeMillis() - startTime.get();
        stringBuilder.append("=======> 耗费时间: ").append(costTime).append("ms \n");
        // 接口结束后换行，方便分割查看
        stringBuilder.append("================================= End ==============================");
        log.info(stringBuilder.toString());
        if (costTime > SLOW_REQUEST_TIME) {
            log.error("慢请求: " + stringBuilder.toString());
        }
        return ret;
    }


    /**
     * 判断是否需要打印日志
     */
    private boolean needlessPrint(JoinPoint joinPoint) throws NoSuchMethodException {
        // 获取当前拦截方法的对象
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodName = methodSignature.getName();
        Boolean res = CACHE_PRINT.get(methodName);
        if (res != null) {
            return res;
        }
        Method targetMethod = joinPoint.getTarget().getClass()
                .getDeclaredMethod(methodName, methodSignature.getMethod().getParameterTypes());
        Annotation annotation = targetMethod.getAnnotation(NoWeblog.class);
        if (annotation != null) {
            CACHE_PRINT.put(methodName, true);
            return true;
        }
        //确认方法上面是否有注解
        annotation = targetMethod.getAnnotation(Weblog.class);
        if (annotation == null) {
            //确认类上面是否有注解
            annotation = targetMethod.getDeclaringClass().getAnnotation(Weblog.class);
            if (annotation == null) {
                CACHE_PRINT.put(methodName, true);
                return true;
            }
            //确认类注解上面是否排除当前方法
            String[] excludeMethod = targetMethod.getDeclaringClass().getAnnotation(Weblog.class).excludeMethod();
            boolean exist = Sets.newHashSet(excludeMethod).contains(methodName);
            CACHE_PRINT.put(methodName, exist);
            return exist;
        }
        CACHE_PRINT.put(methodName, false);
        return false;
    }

}