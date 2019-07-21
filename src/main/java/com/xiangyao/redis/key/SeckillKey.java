package com.xiangyao.redis.key;

/**
 * @author xianggua
 * @description
 * @date 2019-7-20 16:54
 * @since 1.0
 */
public class SeckillKey extends BaseKeyPrefix {

    public SeckillKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static SeckillKey seckillVerifyCodeRegister = new SeckillKey(300, "register");
    public static SeckillKey seckillVerifyCode = new SeckillKey(300, "vc");

}
