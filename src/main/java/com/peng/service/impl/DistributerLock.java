package com.peng.service.impl;

import com.peng.service.IDistributerLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @program redission
 * @description:
 * @author: pengxuanyu
 * @create: 2021/05/30 11:27
 */
/*@Component*/
public class DistributerLock implements IDistributerLock {
    @Autowired
    private RedissonClient redissonClient;

    @Override
    public RLock lock(String lockKey) {
        RLock rLock = redissonClient.getLock(lockKey);
        rLock.lock();
        return rLock;
    }
    @Override
    public RLock lock(String lockKey, long timeout) {
        RLock rLock = redissonClient.getLock(lockKey);
        rLock.lock(timeout,TimeUnit.SECONDS);
        return rLock;
    }
    @Override
    public RLock lock(String lockKey, TimeUnit unit, long timeout) {
        RLock rLock = redissonClient.getLock(lockKey);
        rLock.lock(timeout,unit);
        return rLock;
    }
    @Override
    public boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }
    @Override
    public void unlock(String lockKey) {
        RLock rLock = redissonClient.getLock(lockKey);
        rLock.unlock();
    }
    @Override
    public void unlock(RLock lock) {
        lock.unlock();
    }
}
