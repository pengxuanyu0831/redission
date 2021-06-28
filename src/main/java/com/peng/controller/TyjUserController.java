package com.peng.controller;


import com.peng.mapper.TyjUserMapper;
import com.peng.model.TyjUser;
import com.peng.service.impl.DistributerLock;
import com.peng.utils.RedissionOperateUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author pengxuanyu
 * @since 2021-05-30
 */
@Slf4j
@Component
@RestController
@RequestMapping("/tyj-user")
public class TyjUserController {
/*    @Autowired
    DistributerLock distributerLock;*/

    @Autowired
    RedissionOperateUtil redissionOperateUtil;

/*    @Autowired
    TyjUserMapper tyjUserMapper;*/

    /*@GetMapping("/subIncome")
    @Transactional
    public void subIncome() {
        String key = "session:key:sub";
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 获取锁等待时间为5s 持有时间为10s
                    boolean isGetLock = distributerLock.tryLock(key, TimeUnit.SECONDS, 5, 10);
                    if (isGetLock) {
                        log.info("开始扣减income 线程{}获取到锁", Thread.currentThread().getName());
                        TyjUser tyjUser = tyjUserMapper.selectIncoome();
                        if (tyjUser.getIncome() < 0) {
                            log.info("库存为零");
                            return;
                        }
                        log.info("扣减前库存为{}，开始扣减", tyjUser.getIncome());
                        tyjUserMapper.updateIncome(tyjUser.getUserId());


                        TyjUser tyjUser1 = tyjUserMapper.selectIncoome();
                        log.info("扣减完成，此时库存为{}，并释放锁", tyjUser1.getIncome());
                        distributerLock.unlock(key);
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        });
        thread.start();
    }*/

    @PostMapping("/test")
    public void setBucket(String key, String value) {
        redissionOperateUtil.setBucket(key, value);
    }


    @GetMapping("/test111")
    public Object getBucket(String key) {
        RBucket str = redissionOperateUtil.getBucket(key);
        return str.get();
    }

/*    @GetMapping("/getlist")
    public <V> RList<V> getlist(String key) {
        redissionOperateUtil.lpushmutli(key, 10);
        return redissionOperateUtil.getList(key);*/
/*    }*/

    @GetMapping("/poplist")
    public boolean poplist(String key) {
        return redissionOperateUtil.lpop(key);
    }

/*    @GetMapping("/getlist1")
    public List<Object> getlist1(String key) {
        return redissionOperateUtil.getLists(key);
    }*/

    @GetMapping("/getlist2")
    public boolean getlist2(String key,String value) {
        return redissionOperateUtil.setnx(key,value);
    }

    @GetMapping("/getlock")
    public boolean getlock(String key) throws InterruptedException {
        return redissionOperateUtil.locks("redislock");
    }

    @GetMapping("/unlock")
    public void getlocks(String key) {
        redissionOperateUtil.unlocks(key);
    }

    @GetMapping("/setnumber")
    public void setnumber(String key, long number) {
        redissionOperateUtil.setAtomicMuli(key, number);
    }

    @GetMapping("/add")
    public long add(String key) {
        return redissionOperateUtil.incrAtomic(key);
    }

    @GetMapping("/sub")
    public long sub(String key) {
        return redissionOperateUtil.decrAtomic(key);
    }


}
