package com.sbnh.healer_head_small_program.constants.exception;

/**
 * web请求参数错误异常
 * @author Liutong
 */
public class ForbiddenException extends XBRuntimeException {

    public ForbiddenException() {
        super();
    }

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(Throwable cause) {
        super(cause);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }
}
