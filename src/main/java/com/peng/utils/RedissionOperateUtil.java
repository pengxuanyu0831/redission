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
    /**
     * 设定一个数字，原子性的增减
     */
    public void setAtomicMuli(String key, long count) {
        redissonClient.getAtomicLong(key).set(count);
    }

    public Long decrAtomic(String key) {
        return redissonClient.getAtomicLong(key).decrementAndGet();
    }

    public Long incrAtomic(String key) {
        return redissonClient.getAtomicLong(key).incrementAndGet();
    }

    public void setBucket(String key, T value) {
        RBucket<T> bucket = redissonClient.getBucket(key, StringCodec.INSTANCE);
        bucket.set(value);
    }

    /**
     * 时间是key的存在时间
     * @param key
     * @param value
     * @return
     */
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
    public <V> RList<V> getRList(String key) {
        return redissonClient.getList(key);
    }

    public List<Object> getList(String key) {
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

    /**
     * 至多30s等待获取锁时间，默认10s可以传参设置
     * hash类型的K-V value 是线程号，如果不释放，redission 的watch-dog机制会一直续时，故而使用tryLock上锁尽量手动释放锁
     * trylock()可以设定持有时间和等待时间
     * lock()只能设定持有时间
     * @param key
     * @return
     * @throws InterruptedException
     */
    public Boolean locks(String key,int sec,TimeUnit timeUnit) throws InterruptedException {
        return redissonClient.getLock(key).tryLock(sec,timeUnit);
       //  redissonClient.getLock(key).lock(10,TimeUnit.SECONDS);
    }

    /**
     * 默认15s等待时间
     * @param key
     * @return
     * @throws InterruptedException
     */
    public Boolean locks(String key) throws InterruptedException {
        return redissonClient.getLock(key).tryLock(15,TimeUnit.SECONDS);
    }

    public void unlocks(String key) {
        redissonClient.getLock(key).unlock();
    }

    /**
     * 公平锁&非公平锁
     * 这里的时间是持有锁的时间
     * @param key
     */
    public void fairlock(String key) throws InterruptedException {
        redissonClient.getFairLock(key).tryLock(10,TimeUnit.SECONDS);
    }












}
