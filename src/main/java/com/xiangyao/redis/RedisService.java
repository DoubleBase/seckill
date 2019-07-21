package com.xiangyao.redis;

import com.xiangyao.common.util.StringUtil;
import com.xiangyao.redis.key.KeyPrefix;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author xianggua
 * @description
 * @date 2019-7-4 21:59
 * @since 1.0
 */
@Component
@Slf4j
public class RedisService {

    @Autowired
    private JedisPool jedisPool;

    /**
     * 获取key对应的值
     *
     * @param keyPrefix key前缀
     * @param key       key
     * @param clazz     类对象
     * @param <T>       返回类型
     * @return 返回对象
     */
    public <T> T get(KeyPrefix keyPrefix, String key, Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = keyPrefix.getPrefix() + key;
            String str = jedis.get(realKey);
            return StringUtil.stringToBean(str, clazz);
        } finally {
            close(jedis);
        }
    }

    /**
     * 获取指定key对应的值
     *
     * @param key key
     * @return
     */
    public String get(String key) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.get(key);
        } catch (Exception e) {
            log.error("get key:{} error", key, e);
        } finally {
            close(jedis);
        }
        return result;
    }

    /**
     * 设置redis key-value对
     *
     * @param prefix key前缀
     * @param key    key
     * @param value  值
     * @param <T>    值类型
     * @return
     */
    public <T> boolean set(KeyPrefix prefix, String key, T value) {
        Jedis jedis = null;
        try {
            String str = StringUtil.beanToString(value);
            if (StringUtils.isEmpty(str)) {
                return false;
            }
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            int expireSeconds = prefix.expireSeconds();
            if (expireSeconds <= 0) {
                jedis.set(realKey, str);
            } else {
                jedis.setex(realKey, expireSeconds, str);
            }
            return true;
        } catch (Exception e) {
            log.error("set key:{} error", key, e);
            return false;
        } finally {
            close(jedis);
        }
    }

    /**
     * 删除redis值
     * @param keyPrefix key前缀
     * @param key key
     * @return
     */
    public boolean delete(KeyPrefix keyPrefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = keyPrefix.getPrefix() + key;
            long ret = jedis.del(realKey);
            return ret > 0;
        } finally {
            close(jedis);
        }
    }

    /**
     * 减少值
     * @param keyPrefix key前缀
     * @param key key
     * @return
     */
    public Long decr(KeyPrefix keyPrefix,String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            //生成真正的key
            String realKey = keyPrefix.getPrefix() + key;
            return jedis.decr(realKey);
        }finally {
            close(jedis);
        }
    }


    /**
     * 关闭jedis
     *
     * @param jedis
     */
    public void close(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

}
