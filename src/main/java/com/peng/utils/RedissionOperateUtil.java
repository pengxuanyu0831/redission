package com.peng.utils;

import com.peng.config.RedissionConfig;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    public boolean setnx(String key, Object value) {
        return redissonClient.getBucket(key).trySet(value,60,TimeUnit.SECONDS);
    }

    public boolean setnxandexpire(String key, int seconds) {
        return redissonClient.getBucket(key).expire(seconds, TimeUnit.SECONDS);
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

    public List<Object> getList1(String key) {
        return redissonClient.getList(key).readAll();
    }

    /**
     * 添加给list添加元素
     * @param key
     * @param value
     */
    public void lpush(String key,Object value) {
        redissonClient.getList(key).add(value);
    }

    /**
     * 红包个数增减
     * @param key
     * @param count
     */
    public void lpushmutli(String key, int count) {
        List<String> list1 = new ArrayList<>();
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                list1.add("1");
            }
            redissonClient.getList(key).addAll(list1);
        }
    }

    public boolean lpop(String key) {
        return redissonClient.getList(key).remove("1");
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
