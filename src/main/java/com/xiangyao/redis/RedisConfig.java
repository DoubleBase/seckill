package com.xiangyao.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author xianggua
 * @description
 * @date 2019-7-4 22:13
 * @since 1.0
 */
@Component
@Setter
@Getter
@ConfigurationProperties(prefix = "redis")
public class RedisConfig {
    private int database;
    private String host;
    private int port;
    private int timeout;
    private String password;
    private int poolMaxIdle;
    private int poolMaxTotal;
    private int poolMaxWaitMillis;

    @Bean
    public JedisPool jedisPoolFactory() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(poolMaxIdle);
        poolConfig.setMaxTotal(poolMaxTotal);
        poolConfig.setMaxWaitMillis(poolMaxWaitMillis);
        JedisPool jedisPool = new JedisPool(poolConfig, host, port,
                timeout * 1000, password, database);
        return jedisPool;
    }
}
