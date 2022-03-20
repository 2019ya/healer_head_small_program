package com.sbnh.healer_head_small_program.redis.impl;

import com.google.gson.Gson;
import org.redisson.api.*;
import org.redisson.client.codec.Codec;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * redisson操作类
 */
@Service("redissonService")
public class RedissonService {

    private static Logger logger = LoggerFactory.getLogger(RedissonService.class);

    @Autowired
    private RedissonClient redissonClient;

    @Value("${spring.redis.db}")
    private Integer redisDB;

    public void getRedissonClient() throws IOException {
        Config config = redissonClient.getConfig();
        System.out.println(config.toJSON().toString());
    }

    /**
     * `
     * 获取字符串对象
     *
     * @param objectName
     * @return
     */
    public <T> RBucket<T> getRBucket(String objectName) {
        RBucket<T> bucket = redissonClient.getBucket(objectName, new StringCodec());
        return bucket;
    }


    public void setRBucket(String key, String value) {
        RBucket rBucket = redissonClient.getBucket(key, new StringCodec());
        rBucket.set(value);
    }

    public void delete(String key) {
        RBucket rBucket = redissonClient.getBucket(key);
        rBucket.delete();
    }

    public void setRBucket(String key, String value, long expireTime) {
        RBucket rBucket = redissonClient.getBucket(key, new StringCodec());
        rBucket.set(value, expireTime, TimeUnit.MILLISECONDS);
    }

    public void set(String key, Object value, long expireTime) {
        Gson gson = new Gson();
        RBucket rBucket = redissonClient.getBucket(key, new StringCodec());
        rBucket.set(gson.toJson(value), expireTime, TimeUnit.MILLISECONDS);
    }


    public Long remainTimeToLiveAsync(String key) {
        try {
            RBucket rBucket = redissonClient.getBucket(key);
            RFuture<Long> remainTimeRFuture = rBucket.remainTimeToLiveAsync();
            Long remainTime = remainTimeRFuture.get();
            return remainTime;
        } catch (Exception e) {
            logger.error("", e);
        }
        return -1l;
    }

    public <T> T get(String key, Class<T> clazz) {
        RBucket<String> rBucket = redissonClient.getBucket(key, new StringCodec());
        Gson gson = new Gson();
        return gson.fromJson(rBucket.get(), clazz);
    }


    /**
     * 自增+1
     *
     * @param objectName
     * @return
     */
    public long incr(String objectName) {
        RAtomicLong rAtomicLong = getRAtomicLong(objectName);
        return rAtomicLong.incrementAndGet();
    }

    /**
     * 获取Map对象
     *
     * @param objectName
     * @return
     */
    public RMap getRMap(String objectName) {
        RMap map = redissonClient.getMap(objectName);
        return map;
    }

    public <K, V> RMap<K, V> getRMap(String objectName, Codec codec) {
        RMap<K, V> map = redissonClient.getMap(objectName, codec);
        return map;
    }


    /**
     * 模糊匹配keys
     *
     * @param keys
     * @return
     */
    public List<String> getRKeys(String keys) {
        RKeys rKeys = redissonClient.getKeys();
        Iterable<String> itKeys = rKeys.getKeysByPattern(keys);
        List<String> keyList = new ArrayList<>();
        if (itKeys != null) {
            Iterator<String> iterator = itKeys.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                logger.info("key>>>>>>>>>>>>>>>>>:" + key);
                keyList.add(key);
            }
        }
        return keyList;
    }

    /**
     * 获取有序集合
     *
     * @param objectName
     * @return
     */
    public <V> RSortedSet<V> getRSortedSet(String objectName) {
        RSortedSet<V> sortedSet = redissonClient.getSortedSet(objectName);
        return sortedSet;
    }

    /**
     * 获取集合
     *
     * @param objectName
     * @return
     */
    public <V> RSet<V> getRSet(String objectName) {
        RSet<V> rSet = redissonClient.getSet(objectName);
        return rSet;
    }

    /**
     * 获取列表
     *
     * @param objectName
     * @return
     */
    public <V> RList<V> getRList(String objectName) {
        RList<V> rList = redissonClient.getList(objectName, new StringCodec());
        return rList;
    }

    /**
     * 获取队列
     *
     * @param objectName
     * @return
     */
    public <V> RQueue<V> getRQueue(String objectName) {
        RQueue<V> rQueue = redissonClient.getQueue(objectName);
        return rQueue;
    }

    /**
     * 获取双端队列
     *
     * @param objectName
     * @return
     */
    public <V> RDeque<V> getRDeque(String objectName) {
        RDeque<V> rDeque = redissonClient.getDeque(objectName);
        return rDeque;
    }


    /**
     * 获取锁
     *
     * @param objectName
     * @return
     */
    public RLock getRLock(String objectName) {
        RLock rLock = redissonClient.getLock(objectName);
        return rLock;
    }

    /**
     * 获取读取锁
     *
     * @param objectName
     * @return
     */
    public RReadWriteLock getRWLock(String objectName) {
        RReadWriteLock rwlock = redissonClient.getReadWriteLock(objectName);
        return rwlock;
    }

    /**
     * 获取原子数
     *
     * @param objectName
     * @return
     */
    public RAtomicLong getRAtomicLong(String objectName) {
        RAtomicLong rAtomicLong = redissonClient.getAtomicLong(objectName);
        return rAtomicLong;
    }

    /**
     * 获取记数锁
     *
     * @param objectName
     * @return
     */
    public RCountDownLatch getRCountDownLatch(String objectName) {
        RCountDownLatch rCountDownLatch = redissonClient.getCountDownLatch(objectName);
        return rCountDownLatch;
    }

    /**
     * 获取消息的Topic
     *
     * @param objectName
     * @return
     */
    public RTopic getRTopic(String objectName) {
        RTopic rTopic = redissonClient.getTopic(objectName, new StringCodec());
        return rTopic;
    }

}