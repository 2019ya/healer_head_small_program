package com.sbnh.healer_head_small_program.bean.po;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("UserInfo")
public class UserInfo extends BasePo{
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

    /**
     *盐
     */
    private String salt;
}
