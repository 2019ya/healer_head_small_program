package com.sbnh.healer_head_small_program.utils;

import com.sbnh.healer_head_small_program.bean.bo.CacheSidBo;
import com.sbnh.healer_head_small_program.constants.RedisCacheKeyConstants;
import com.sbnh.healer_head_small_program.constants.exception.ForbiddenException;
import com.sbnh.healer_head_small_program.constants.exception.RequestParamException;
import com.sbnh.healer_head_small_program.redis.RedisDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

/***
 * sid 工具类
 */
@Slf4j
@Service
public class SessionUtils {

    @Autowired
    private RedisDao redisDao;

    /**
     * 缓存有效时间 (秒)
     */
    private final static Integer TIME =30*24*60*60;

    /**
     * 生成sid
     * @param userId 用户id
     * @param salt 盐
     * @return sid
     */
    public String generateSid(String userId,String salt){
        StringBuilder sb = new StringBuilder();
        sb.append(userId).append(salt);
        byte[] bytes = new byte[0];
        try {
            bytes = sb.toString().getBytes("UTF-8");
        } catch (Exception e) {
            log.error("sid生成失败:{}",e.getMessage());
            Assert.pop(new RequestParamException("sid生成失败"));
        }
        return DigestUtils.md5Hex(bytes);

    }

    /**
     * 盐值
     * @return
     */
    public String generateSalt(){
        Random random = new Random();
        StringBuilder sb = new StringBuilder(8);
        sb.append(random.nextInt(99999999));
        int len = sb.length();
        if (len < 8) {
            for (int i = 0; i < 8 - len; i++) {
                sb.append("0");
            }
        }
        return sb.toString();
    }

    /**
     * 缓存sid
     * @param bo 实体类
     * @param sid sid
     */
    public void cacheSid(CacheSidBo bo,String sid){
        redisDao.set(RedisCacheKeyConstants.CACHE_SESSION_ID+sid,bo,TIME);
    }


    /**
     * 自己传入sid
     * @return
     */
    public CacheSidBo getCacheSid(String sid){
        boolean hasKey = redisDao.hasKey(RedisCacheKeyConstants.CACHE_SESSION_ID + sid);
        if (hasKey){
            return   redisDao.get(RedisCacheKeyConstants.CACHE_SESSION_ID + sid,CacheSidBo.class);
        }else {
            Assert.pop(new ForbiddenException("用户未登陆"));
        }
        return null;
    }

    /**
     * 系统获取sid
     * @return
     */
    public CacheSidBo getCacheSid(){
      return   getCacheSid(getSid());
    }
    /**
     * 获取sid
     *
     * @return
     */
    private static String getSid() {
        // 获取request对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        String sid = request.getHeader("sid");
        if (StringUtils.isEmpty(sid)) {
            Assert.pop(new RequestParamException("无法获取sid"));
        }
        return sid;
    }


}
