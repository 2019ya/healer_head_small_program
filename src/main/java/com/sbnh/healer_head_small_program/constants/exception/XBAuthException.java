package com.sbnh.healer_head_small_program.constants.exception;

/**
 * description
 *
 * @author yong
 * @date 2018/10/24 14:55
 */
public class XBAuthException extends XBRuntimeException {

    private static final String DEFAULT_MSG = "授权异常";

    public XBAuthException() {
        super(DEFAULT_MSG);
    }

    public XBAuthException(String message) {
        super(message);
    }

    public XBAuthException(Throwable cause) {
        super(cause);
    }

    public XBAuthException(String message, Throwable cause) {
        super(message, cause);
    }

}
