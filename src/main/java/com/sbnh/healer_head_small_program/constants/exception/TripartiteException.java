package com.sbnh.healer_head_small_program.constants.exception;

/**
 * 请求第三方异常
 *
 * @author Liutong
 */
public class TripartiteException extends XBRuntimeException {

    public TripartiteException() {
        super();
    }

    public TripartiteException(String message) {
        super(message);
    }

    public TripartiteException(Throwable cause) {
        super(cause);
    }

    public TripartiteException(String message, Throwable cause) {
        super(message, cause);
    }
}