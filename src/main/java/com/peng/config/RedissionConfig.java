package com.peng.config;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import sun.security.krb5.KrbException;

import java.io.IOException;

/**
 * @program redission
 * @description:
 * @author: pengxuanyu
 * @create: 2021/05/30 11:16
 */
@Slf4j
@Configuration
@EnableTransactionManagement
public class RedissionConfig {
    /*@Value("${spring.redis.cluster.nodes}")
    private String clusters;*/

    /**
     * redis cluster 模式
     */
    /*public RedissonClient getRedission() throws IOException {
        log.info("创建RedissionClient");
        String[] nodes = clusters.split(",");
        for(int i=0;i<nodes.length;i++){
            nodes[i] = "redis://"+nodes[i];
        }
        RedissonClient redissonClient = null;

        Config config = new Config();
        config.useClusterServers().addNodeAddress(nodes).setScanInterval(20000);
        redissonClient = Redisson.create(config);
        log.info("RedissionClietn配置为：{}",redissonClient.getConfig().toJSON().toString());
        return redissonClient;
    }*/


    /**
     * 单节点模式
     */
    @Bean
    public RedissonClient getRedission() {
        RedissonClient redissonClient = null;
        log.info("加载redission单点模式....");

        Config config = new Config();
        config.setCodec(new StringCodec()).useSingleServer().setAddress("redis://127.0.0.1:6379").setDatabase(15);
        redissonClient = Redisson.create(config);
        return redissonClient;
    }
}
