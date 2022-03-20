package com.sbnh.healer_head_small_program.constants.exception;

/**
 * web请求参数错误异常
 * @author Liutong
 */
public class RequestParamException extends XBRuntimeException {

    public RequestParamException() {
        super();
    }

    public RequestParamException(String message) {
        super(message);
    }

    public RequestParamException(Throwable cause) {
        super(cause);
    }

    public RequestParamException(String message, Throwable cause) {
        super(message, cause);
    }
}
