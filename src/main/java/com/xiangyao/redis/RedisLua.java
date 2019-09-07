package com.xiangyao.redis;

import com.xiangyao.redis.manager.RedisManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xianggua
 * @description
 * @date 2019-7-20 15:18
 * @since 1.0
 */
public class RedisLua {

    private static final Logger logger = LoggerFactory.getLogger(RedisLua.class);

    /**
     * 统计访问次数
     *
     * @param key
     * @return
     */
    public static Object getVisitorCount(String key) {
        Jedis jedis = null;
        Object object = null;
        try {
            jedis = RedisManager.getJedis();
            String count = "local num = redis.call('get',KEYS[1]) return num";
            List<String> keys = new ArrayList<>();
            keys.add(key);
            List<String> argves = new ArrayList<>();
            jedis.auth("admin");
            String luaScript = jedis.scriptLoad(count);
            System.out.println(luaScript);
            object = jedis.evalsha(luaScript, keys, argves);
        } catch (Exception e) {
            logger.error("获取统计访问次数失败", e);
            return "0";
        }
        return object;
    }

    /**
     * 统计访问次数
     *
     * @param key
     */
    public static void visitorCount(String key) {
        Jedis jedis = null;
        Object object = null;
        try {
            jedis = RedisManager.getJedis();
            String count = "local num = redis.call('incr',KEYS[1]) return num";
            List<String> keys = new ArrayList<>();
            keys.add(key);
            List<String> argves = new ArrayList<>();
            jedis.auth("admin");
            String luaScript = jedis.scriptLoad(count);
            System.out.println(luaScript);
            jedis.evalsha(luaScript, keys, argves);
        } catch (Exception e) {
            logger.error("统计访问次数失败", e);
        }
    }

}
