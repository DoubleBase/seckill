package com.xiangyao.redis.key;

/**
 * @author xianggua
 * @description
 * @date 2019-7-21 14:07
 * @since 1.0
 */
public class UserKey extends BaseKeyPrefix {

    public static final int TOKEN_EXPIRE = 3600 * 24 * 2;

    public UserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static UserKey token = new UserKey(TOKEN_EXPIRE, "tk");
    public static UserKey nickNameKey = new UserKey(0, "nickName");
}
