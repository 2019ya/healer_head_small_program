package com.sbnh.healer_head_small_program.service.impl;

import com.sbnh.healer_head_small_program.bean.bo.CacheSidBo;
import com.sbnh.healer_head_small_program.bean.po.UserInfo;
import com.sbnh.healer_head_small_program.bean.vo.WeiXinLoginVo;
import com.sbnh.healer_head_small_program.constants.exception.RequestParamException;
import com.sbnh.healer_head_small_program.dao.UserInfoMapper;
import com.sbnh.healer_head_small_program.service.UserInfoService;
import com.sbnh.healer_head_small_program.utils.Assert;
import com.sbnh.healer_head_small_program.utils.HttpUtils;
import com.sbnh.healer_head_small_program.utils.IdGen;
import com.sbnh.healer_head_small_program.utils.SessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private SessionUtils sessionUtils;
    /**
     * 微信小程序登陆url
     */
    @Value("weixin.appid")
    private String appid;

    @Value("weixin.secret")
    private String secret;

    private final static String  JS_CODE_SESSION_PATH = "/sns/jscode2session";
    private final static String  JS_CODE_SESSION_HOST = "https://api.weixin.qq.com";

    @Override
    public WeiXinLoginVo weiXinLogin(String code) {
        Assert.notNull(code,new RequestParamException("code不能为空"));
        WeiXinLoginVo weiXinLoginVo = new WeiXinLoginVo();
        Map<String,String > map = new HashMap<>();
        map.put("code",code);
        map.put("appid",appid);
        map.put("secret",secret);
        map.put("grant_type","authorization_code");
        try {
            HttpResponse httpResponse = HttpUtils.doGet(JS_CODE_SESSION_HOST, JS_CODE_SESSION_PATH, map);
            Assert.isTrue(HttpStatus.SC_OK==httpResponse.getStatusLine().getStatusCode(),new RequestParamException("登录游戏失败"));
            String responseBody = EntityUtils.toString(httpResponse.getEntity());
            JSONObject jsonObject = JSON.parseObject(responseBody);
            String successCode = jsonObject.getString("errcode");
            //0是成功
            if (successCode.equals(String.valueOf(0))) {
                String openId = jsonObject.getString("openid");
                String sessionKey = jsonObject.getString("session_key");
                String unionId = jsonObject.getString("unionid");

                //查询之前是否有登陆过
                UserInfo userInfoByOpenId =  userInfoMapper.findByOpenId(openId);
                //没有登陆过的就去新增
                if (Objects.isNull(userInfoByOpenId)) {
                    UserInfo userInfo = new UserInfo();
                    userInfo.setOpenId(openId);
                    userInfo.setSessionKey(sessionKey);
                    userInfo.setUnionId(unionId);
                    String id = IdGen.uuid();
                    userInfo.setId(id);
                    //获取盐
                    String salt = sessionUtils.generateSalt();
                    userInfo.setSalt(salt);
                    userInfoMapper.save(userInfo);
                    //获取sid
                    String sid = sessionUtils.generateSid(id, salt);
                    //缓存
                    CacheSidBo cacheSidBo = new CacheSidBo();
                    BeanUtils.copyProperties(userInfo, cacheSidBo);
                    sessionUtils.cacheSid(cacheSidBo, sid);
                }else {
                    //有的话就去更新
                    Update update = new Update();
                    update.set("sessionKey",sessionKey);
                    userInfoMapper.updateById(update,userInfoByOpenId.getId());
                    //获取盐
                    String salt = sessionUtils.generateSalt();
                    //获取sid
                    String sid = sessionUtils.generateSid(userInfoByOpenId.getId(), salt);
                    CacheSidBo cacheSidBo = new CacheSidBo();
                    cacheSidBo.setHeader(sessionKey);
                    BeanUtils.copyProperties(userInfoByOpenId, cacheSidBo);
                    sessionUtils.cacheSid(cacheSidBo, sid);
                }

            }else {
                String errmsg = jsonObject.getString("errmsg");
                log.error("errcode:{},errmsg:{}",successCode,errmsg);
                Assert.pop(new RequestParamException("登陆失败"));
            }
        } catch (Exception e) {
            Assert.pop(new RequestParamException("请求微信失败"));
        }
        return null;
    }
}
