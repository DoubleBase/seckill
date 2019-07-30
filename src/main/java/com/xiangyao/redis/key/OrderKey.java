package com.xiangyao.redis.key;

/**
 * @author xianggua
 * @description
 * @date 2019-7-30 23:50
 * @since 1.0
 */
public class OrderKey extends BaseKeyPrefix {

    public OrderKey(String prefix) {
        super(prefix);
    }

    public static OrderKey getSeckillOrderByUidGid = new OrderKey("soug");
}
