package com.sbnh.healer_head_small_program.core;

import java.lang.annotation.*;

/**
 * @Description 自定义注解
 * @Author hzj
 * @Date 2020/05/11 10:03
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface Weblog {

    /**
     * 排除方法
     *
     * @return
     */
    String[] excludeMethod() default {};
}