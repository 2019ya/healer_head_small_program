package com.sbnh.healer_head_small_program.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 微信登陆返回
 */
@Data
public class WeiXinLoginVo {
    @ApiModelProperty("sid")
    private String sid;

    @ApiModelProperty("微信用户唯一标识")
    private String openId;
}
