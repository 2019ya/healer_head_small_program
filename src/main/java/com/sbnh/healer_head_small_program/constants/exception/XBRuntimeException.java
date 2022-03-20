package com.sbnh.healer_head_small_program.constants.exception;

/**
 * 基础异常类
 * @author Liutong
 */
public class XBRuntimeException extends RuntimeException {

    /**
     * Creates a new XBRuntimeException.
     */
    public XBRuntimeException() {
        super();
    }

    /**
     * Constructs a new XBRuntimeException.
     *
     * @param message the reason for the exception
     */
    public XBRuntimeException(String message) {
        super(message);
    }

    /**
     * Constructs a new XBRuntimeException.
     *
     * @param cause the underlying Throwable that caused this exception to be thrown.
     */
    public XBRuntimeException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new XBRuntimeException.
     *
     * @param message the reason for the exception
     * @param cause the underlying Throwable that caused this exception to be thrown.
     */
    public XBRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

}
