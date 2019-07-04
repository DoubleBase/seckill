package com.xiangyao.redis.key;

/**
 * @author xianggua
 * @description
 * @date 2019-7-4 22:01
 * @since 1.0
 */
public class BaseKeyPrefix implements KeyPrefix {

    /**
     * 默认0代表永不过期
     */
    private int expireSeconds;

    private String prefix;

    public BaseKeyPrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    BaseKeyPrefix(String prefix) {
        this(0, prefix);
    }

    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    /**
     * 获取唯一key
     * @return
     */
    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }
}
