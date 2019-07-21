package com.xiangyao.redis.key;


import com.xiangyao.common.util.TimeUtil;

/**
 * @author xianggua
 * @description
 * @date 2019-7-4 22:04
 * @since 1.0
 */
public class GoodsKey extends BaseKeyPrefix {

    private GoodsKey(int expireSeconds,String prefix){
        super(expireSeconds,prefix);
    }

    public static GoodsKey goodListKey = new GoodsKey(TimeUtil.MINUTE,"gl");
    public static GoodsKey goodDetailKey = new GoodsKey(TimeUtil.MINUTE,"gd");
    public static GoodsKey goodStockKey = new GoodsKey(0,"gs");

}
