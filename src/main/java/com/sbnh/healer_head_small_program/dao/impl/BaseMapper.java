package com.sbnh.healer_head_small_program.dao.impl;

import com.sbnh.healer_head_small_program.redis.impl.RedissonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BaseMapper{
    @Autowired(required = false)
     MongoTemplate mongoTemplate;
    @Autowired
     RedisTemplate redisTemplate;
    @Autowired
     RedissonService redissonService;

    public String ID_KEY ="id";
    public String MOBILE_KEY ="mobile";
    public String OPEN_ID_KEY ="openId";
    public String STATUS_KEY ="status";
    public String CREATE_TIME_KEY ="createTime";


}
