package com.sbnh.healer_head_small_program.constants.exception;

/**
 * description
 *
 * @author yong
 * @date 2018/5/11 19:17
 */
public class XBException extends Exception {

    /**
     * Creates a new DHException.
     */
    public XBException() {
        super();
    }

    /**
     * Constructs a new DHException.
     *
     * @param message the reason for the exception
     */
    public XBException(String message) {
        super(message);
    }

    /**
     * Constructs a new DHException.
     *
     * @param cause the underlying Throwable that caused this exception to be thrown.
     */
    public XBException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new DHException.
     *
     * @param message the reason for the exception
     * @param cause the underlying Throwable that caused this exception to be thrown.
     */
    public XBException(String message, Throwable cause) {
        super(message, cause);
    }

}
