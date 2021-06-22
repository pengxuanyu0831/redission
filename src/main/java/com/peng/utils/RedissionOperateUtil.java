package com.peng.utils;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.*;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Qualifier("getRedission")
    @Autowired
    private RedissonClient redissonClient;

    /**
     * 操作字符串对象
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

    /**
     * 获取map对象，就是redis里的hash，还可以实现缓存的功能
     * @param key
     * @param <K>
     * @param <V>
     * @return
     */
    public <K, V> RMap<K, V> getMap(String key) {
        RMap<K, V> map = redissonClient.getMap(key);
        return map;
    }

    /**
     * 操作list对象
     * @param key
     * @param <V>
     * @return
     */
    public <V> RList<V> getList(String key) {
        return redissonClient.getList(key);
    }

    /**
     * 获取从index=0 到index=end
     * @param key
     * @param end
     * @param <V>
     * @return
     */
    public <V> RList<V> range(String key,int end) {
        return (RList<V>) redissonClient.getList(key).range(end);
    }

    /**
     * 从index=start 到index=end
     * @param key
     * @param start
     * @param end
     * @param <V>
     * @return
     */
    public <V> RList<V> range(String key, int start, int end) {
        return (RList<V>) redissonClient.getList(key).range(start, end) ;
    }

    /**
     * 删除list中元素，count 删除个数，返回true---删除至少一个  返回false---没找到
     * @param key
     * @param element
     * @param count
     * @return
     */
    public boolean remove(String key,Object element, int count) {
        return redissonClient.getList(key).remove(element, count);
    }

    /**
     * 返回list长度
     * @param key
     * @return
     */
    public int size(String key) {
        return redissonClient.getList(key).size();
    }


    /**
     * 获取set
     * @param key
     * @param <V>
     * @return
     */
    public <V> RSet<V> getSet(String key) {
        return redissonClient.getSet(key);
    }













}
