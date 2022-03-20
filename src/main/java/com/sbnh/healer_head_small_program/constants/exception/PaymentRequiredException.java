package com.sbnh.healer_head_small_program.constants.exception;

/**
 * @author: weis
 * @create: 2021-10-13-15:35
 * @note: 402异常
 */
public class PaymentRequiredException extends XBRuntimeException{
    public PaymentRequiredException() {
        super();
    }

    public PaymentRequiredException(String message) {
        super(message);
    }

    public PaymentRequiredException(Throwable cause) {
        super(cause);
    }

    public PaymentRequiredException(String message, Throwable cause) {
        super(message, cause);
    }

}
