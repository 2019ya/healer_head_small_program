package com.sbnh.healer_head_small_program.dao.impl;

import com.sbnh.healer_head_small_program.bean.po.UserInfo;
import com.sbnh.healer_head_small_program.dao.UserInfoMapper;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class UserInfoMapperImpl extends BaseMapper implements UserInfoMapper {
    @Override
    public void save(UserInfo userInfo) {
     mongoTemplate.save(userInfo);
    }

    @Override
    public UserInfo findByOpenId(String openId) {
        return mongoTemplate.findOne(Query.query(Criteria.where(OPEN_ID_KEY).is(openId)),UserInfo.class);
    }

    @Override
    public void updateById(Update update, String id) {
        mongoTemplate.updateFirst(Query.query(Criteria.where(ID_KEY).is(id)),update,UserInfo.class);
    }
}
