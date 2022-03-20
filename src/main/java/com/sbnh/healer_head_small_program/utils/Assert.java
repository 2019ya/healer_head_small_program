package com.sbnh.healer_head_small_program.utils;



import com.sbnh.healer_head_small_program.constants.exception.XBRuntimeException;

import java.util.Collection;
import java.util.Map;

/**
 * 断言工具类
 *
 * @author Liutong
 */
public class Assert {

    /**
     * 校验obj不为空，为空则抛出指定异常e
     *
     * @param obj 参数
     * @param e 指定异常
     */
    public static void notNull(Object obj, XBRuntimeException e) {
        if (obj == null) {
            throw e;
        }
    }

    /**
     * 断言obj为空,若obj != null则抛出指定异常e
     *
     * @param obj 参数
     * @param e 指定异常
     */
    public static void isNull(Object obj, XBRuntimeException e) {
        if (obj != null) {
            throw e;
        }
    }

    /**
     * 校验expression为true，为false则抛出指定异常e
     *
     * @param expression 布尔值
     * @param e 指定异常
     */
    public static void isTrue(boolean expression, XBRuntimeException e) {
        if (!expression) {
            throw e;
        }
    }

    /**
     * 校验expression为true，为false则抛出指定异常e
     *
     * @param expression 布尔值
     * @param e 指定异常
     */
    public static void isTrue(boolean expression, RuntimeException e) {
        if (!expression) {
            throw e;
        }
    }

    /**
     * 校验expression为false，为true则抛出指定异常e
     *
     * @param expression 布尔值
     * @param e 指定异常
     */
    public static void isFalse(boolean expression, XBRuntimeException e) {
        if (expression) {
            throw e;
        }
    }

    /**
     * 校验字符串不为空，为空则抛出指定异常e
     *
     * @param expression 字符串
     * @param e 指定异常
     */
    public static void notEmpty(String expression, XBRuntimeException e) {
        if (expression == null || "".equals(expression)) {
            throw e;
        }
    }

    /**
     * 校验map集合不为空，为空则抛出指定异常e
     *
     * @param map map集合
     * @param e 指定异常
     */
    public static void notEmpty(Map<?, ?> map, XBRuntimeException e) {
        if (map == null || map.isEmpty()) {
            throw e;
        }
    }

    /**
     * 校验集合不为空，为空则抛出指定异常e
     *
     * @param collection 集合
     * @param e 指定异常
     */
    public static void notEmpty(Collection<?> collection, XBRuntimeException e) {
        if (collection == null || collection.isEmpty()) {
            throw e;
        }
    }

    /**
     * 断言参数相等 通过obj.isEquals()判断
     * 若equals为false则抛出指定异常e
     *
     * @param expected 期望值
     * @param actual 实际值
     * @param e 指定异常
     */
    public static void isEquals(Object expected, Object actual, XBRuntimeException e) {
        if (!expected.equals(actual)) {
            throw e;
        }
    }

    /**
     * 直接抛出异常
     *
     * @param e
     */
    public static void pop(XBRuntimeException e) {
        throw e;
    }

}
