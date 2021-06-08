package com.peng.utils;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program redission
 * @description: redission客户端操作工具类
 *  参考文档 ： https://github.com/redisson/redisson/wiki
 * @author: pengxuanyu
 * @create: 2021/06/07 21:53
 */
@Slf4j
@Service
public class RedissionOperateUtil<T> {
    private static RedissionOperateUtil redissionOperateUtil;

    private RedissionOperateUtil() {}

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 获取字符串对象
     * @param key
     * @param <T>
     * @return
     */
    public <T> RBucket<T> getBucket(String key) {
        RBucket<T> bucket = redissonClient.getBucket(key,StringCodec.INSTANCE);
        return bucket;
    }

    public void setBucket(String key, T value) {
        RBucket<T> bucket = redissonClient.getBucket(key, StringCodec.INSTANCE);
        bucket.set(value);
    }







}
