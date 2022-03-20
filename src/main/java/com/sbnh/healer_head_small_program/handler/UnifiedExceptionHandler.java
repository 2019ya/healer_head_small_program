package com.sbnh.healer_head_small_program.handler;

import com.sbnh.healer_head_small_program.constants.enums.CommonResponseEnum;
import com.sbnh.healer_head_small_program.constants.exception.ErrorResponse;
import com.sbnh.healer_head_small_program.constants.exception.ForbiddenException;
import com.sbnh.healer_head_small_program.constants.exception.PaymentRequiredException;
import com.sbnh.healer_head_small_program.constants.exception.RequestParamException;
import lombok.extern.slf4j.Slf4j;

import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.xml.bind.ValidationException;


/**
 * @Description 全局异常处理器
 * @Author hzj
 * @Date 2020/04/27 11:02
 */
@ControllerAdvice
@RestController
@Slf4j
public class UnifiedExceptionHandler {

    /**
     * 生产环境
     */
    private final static String ENV_PROD = "prod";

    /**
     * 当前环境
     */
    @Value("${spring.profiles.active}")
    private String profile;



    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ErrorResponse methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("绑定请求参数异常", e);
        return new ErrorResponse(CommonResponseEnum.CLIENT_ERROR.getError(), "请求参数错误");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RequestParamException.class)
    public ErrorResponse requestParamException(RequestParamException e) {
        log.error("错误:{}", e.getMessage());
        return new ErrorResponse(CommonResponseEnum.CLIENT_ERROR.getError(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ErrorResponse missingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("请求参数异常:{}", e.getMessage());
        return new ErrorResponse(CommonResponseEnum.CLIENT_ERROR.getError(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public ErrorResponse forbiddenException(ForbiddenException e) {
        log.error("服务器拒绝请求:{}", e.getMessage());
        return new ErrorResponse(CommonResponseEnum.CLIENT_ERROR.getError(), e.getMessage());
    }


    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ErrorResponse httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("请求方式异常", e);
        return new ErrorResponse(CommonResponseEnum.CLIENT_ERROR.getError(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ErrorResponse missingRequestHeaderException(MissingRequestHeaderException e) {
        log.error("请求Header参数异常", e);
        return new ErrorResponse(CommonResponseEnum.CLIENT_ERROR.getError(),
                "请求Headers【" + e.getHeaderName() + "】不能为空");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResponse httpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("参数异常", e);
        return new ErrorResponse(CommonResponseEnum.CLIENT_ERROR.getError(), "请求参数错误");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthenticatedException.class)
    public ErrorResponse unauthenticatedException(UnauthenticatedException e) {
        log.error("认证异常", e);
        return new ErrorResponse(CommonResponseEnum.CLIENT_ERROR.getError(), "用户未登录");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({UnknownAccountException.class})
    public ErrorResponse unknownAccountException(UnknownAccountException e) {
        log.error("登录异常，【{}】", e.getMessage());
        return new ErrorResponse(CommonResponseEnum.CLIENT_ERROR.getError(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({UnauthorizedException.class})
    public ErrorResponse unauthorizedException(UnauthorizedException e) {
        log.error("无此操作权限，【{}】", e.getMessage());
        return new ErrorResponse(CommonResponseEnum.CLIENT_ERROR.getError(), "您的帐号未授予权限，请联系管理员。");
    }
    @ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
    @ExceptionHandler({PaymentRequiredException.class})
    public ErrorResponse paymentRequiredException(Exception e) {
        log.error("数据不存在，【{}】", e.getMessage());
        return new ErrorResponse(CommonResponseEnum.CLIENT_ERROR.getError(), e.getMessage());
    }

    /**
     * 验证异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ValidationException.class)
    @ResponseBody
    public ErrorResponse handleBaseException(ValidationException e) {
        log.error("验证异常", e);
        return new ErrorResponse(CommonResponseEnum.CLIENT_ERROR.getError(), "参数异常");
    }

    /**
     * 参数绑定异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public ErrorResponse handleBindException(BindException e) {
        log.error("参数绑定校验异常", e);
        return wrapperBindingResult(e.getBindingResult());
    }

    /**
     * 参数校验异常，将校验失败的所有异常组合成一条错误信息
     *
     * @param e 异常
     * @return 异常结果
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse handleValidException(MethodArgumentNotValidException e) {
        ObjectError error = e.getBindingResult().getAllErrors().get(0);
        if (error instanceof FieldError) {
            log.error("对象：{}；字段：{}；异常信息：【{}】", error.getObjectName(), ((FieldError) error).getField(),
                    error.getDefaultMessage());
        } else {
            log.error("参数绑定校验异常", e);
        }
        return wrapperBindingResult(e.getBindingResult());
    }

    /**
     * 包装绑定异常结果
     *
     * @param bindingResult 绑定结果
     * @return 异常结果
     */
    private ErrorResponse wrapperBindingResult(BindingResult bindingResult) {
        StringBuilder msg = new StringBuilder();
        for (ObjectError error : bindingResult.getAllErrors()) {
            msg.append(", ");
            msg.append(error.getDefaultMessage() == null ? "" : error.getDefaultMessage());
        }
        return new ErrorResponse(CommonResponseEnum.CLIENT_ERROR.getError(), msg.substring(2));
    }

    /**
     * 未定义异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public ErrorResponse handleException(RuntimeException e) {
        log.error("未定义异常", e);
        return new ErrorResponse(CommonResponseEnum.SERVER_ERROR);
    }
}