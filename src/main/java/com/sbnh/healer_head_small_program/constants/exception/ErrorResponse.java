package com.sbnh.healer_head_small_program.constants.exception;


import com.sbnh.healer_head_small_program.constants.enums.CommonResponseEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Description 基础返回结果
 * @Author hzj
 * @Date 2020/4/26
 */
@Getter
@Setter
@ToString
public class ErrorResponse {
    /**
     * 返回码
     */
    protected String error;
    /**
     * 返回消息
     */
    protected String message;

    public ErrorResponse(CommonResponseEnum commonResponseEnum) {
        this.error = commonResponseEnum.getError();
        this.message = commonResponseEnum.getMessage();
    }

    public ErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
    }
}
