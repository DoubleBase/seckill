package com.xiangyao.redis.key;

/**
 * @author xianggua
 * @description
 * @date 2019-7-4 22:00
 * @since 1.0
 */
public interface KeyPrefix {

    int expireSeconds();

    String getPrefix();

}
