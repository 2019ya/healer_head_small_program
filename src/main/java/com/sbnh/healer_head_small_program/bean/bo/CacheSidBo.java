package com.sbnh.healer_head_small_program.bean.bo;

import lombok.Data;

@Data
public class CacheSidBo {
    /**
     * id
     */
    private String id;

    /**
     *创建时间
     */
    private Long createTime;

    /**
     *头像
     */
    private String header;

    /**
     *昵称
     */
    private String nickname;

    /**
     *微信用户唯一标识
     */
    private String openId;

    /**
     *会话密钥
     */
    private String sessionKey;

    /**
     *用户在开放平台的唯一标识符
     */
    private String unionId;

    /**
     *手机号
     */
    private String mobile;
}
