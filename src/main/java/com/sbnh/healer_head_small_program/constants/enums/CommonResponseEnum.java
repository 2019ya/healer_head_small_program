package com.sbnh.healer_head_small_program.constants.enums;


/**
 * @Description 通用返回结果
 * @Author hzj
 * @Date 2020/4/27
 */
public enum CommonResponseEnum {

    /**
     * 服务器异常
     */
    SERVER_ERROR("Server error", "网络异常"),
    /**
     * 客户端异常
     */
    CLIENT_ERROR("Client error", "客户端异常");

    CommonResponseEnum(String error, String message) {
        this.error = error;
        this.message = message;
    }

    /**
     * 返回码
     */
    private final String error;
    /**
     * 返回消息
     */
    private final String message;

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
