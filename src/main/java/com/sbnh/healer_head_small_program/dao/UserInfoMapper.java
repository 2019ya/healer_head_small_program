package com.sbnh.healer_head_small_program.dao;

import com.sbnh.healer_head_small_program.bean.po.UserInfo;
import org.springframework.data.mongodb.core.query.Update;

public interface UserInfoMapper {
    void save(UserInfo userInfo);

    UserInfo findByOpenId(String openId);

    void updateById(Update update, String id);
}
