package com.xiangyao.common.util;

import java.util.UUID;

/**
 * @author xianggua
 * @description
 * @date 2019-7-20 18:44
 * @since 1.0
 */
public class UUIDUtil {

    /**
     * 生成随机UUID,不包含短杠线
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
