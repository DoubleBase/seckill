package com.xiangyao.common.util;


import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author xianggua
 * @description
 * @date 2019-7-21 18:07
 * @since 1.0
 */
public class MD5Util {

    private static final String SALT = "1a2b3c4d";

    public static String md5(String key) {
        return DigestUtils.md5Hex(key);
    }

    public static String inputPassFormPass(String inputPass) {
        String str = "" + SALT.charAt(0) + SALT.charAt(2) + inputPass + SALT.charAt(5) + SALT.charAt(4);
        return md5(str);
    }

    public static String formPassToDBPass(String formPass, String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDBPass(String inputPass, String salt) {
        String formPass = inputPassFormPass(inputPass);
        String dbPass = formPassToDBPass(formPass, salt);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println(inputPassToDBPass("123456", "1a2b3c4d"));
    }

}
