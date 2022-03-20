package com.sbnh.healer_head_small_program.controller;

import com.sbnh.healer_head_small_program.bean.vo.WeiXinLoginVo;
import com.sbnh.healer_head_small_program.service.UserInfoService;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserInfoController {
     @Autowired
   private UserInfoService userInfoService;


    @GetMapping("/weiXinLogin")
    @ApiModelProperty("微信登陆")
    public WeiXinLoginVo weiXinLogin(@RequestParam @ApiParam(value = "code码",name = "code") String code){
      return userInfoService.weiXinLogin(code);
    }


}
