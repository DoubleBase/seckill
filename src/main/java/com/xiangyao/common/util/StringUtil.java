package com.xiangyao.common.util;

import com.alibaba.fastjson.JSON;
import org.apache.logging.log4j.util.Strings;
import org.thymeleaf.util.StringUtils;

/**
 * @author xianggua
 * @description
 * @date 2019-7-4 22:24
 * @since 1.0
 */
public class StringUtil {

    @SuppressWarnings("unchecked")
    public static <T> T stringToBean(String str, Class<T> clazz) {
        if (StringUtils.isEmpty(str) || clazz == null) {
            return null;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(str);
        } else if (clazz == String.class) {
            return (T) str;
        } else if (clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(str);
        } else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }

    public static <T> String beanToString(T value) {
        if (value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class) {
            return Strings.EMPTY + value;
        } else if (clazz == String.class) {
            return (String) value;
        } else if (clazz == long.class || clazz == Long.class) {
            return Strings.EMPTY + value;
        } else {
            return JSON.toJSONString(value);
        }
    }

}
