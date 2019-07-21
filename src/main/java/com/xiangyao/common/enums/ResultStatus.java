package com.xiangyao.common.enums;

/**
 * @author xianggua
 * @description 返回结果错误码信息
 * @date 2019-6-17 1:05
 * @since 1.0
 */
public enum ResultStatus {

    //通用错误信息
    SUCCESS(0,"成功"),
    FAILED(-1,"失败"),
    EXCEPTION(10001,"系统异常"),
    PARAM_ERROR(10002,"参数错误"),
    SYSTEM_ERROR(10003,"系统错误"),

    //注册登录
    REGISTER_SUCCESS(20001,"注册成功!"),
    REGISTER_FAILED(20002,"注册失败!"),
    CODE_FAILED(20003,"验证码不一致"),
    MOBILE_NOT_EXIST(20004,"手机号码不存在"),
    PASSWORD_ERROR(20005,"密码错误"),

    //秒杀信息
    VERIFY_CODE_CHECK_FAILED(50001,"验证码校验失败"),
    VERIFY_CODE_ERROR(50002,"验证码错误"),
    SECKILL_ERROR(50003,"秒杀失败"),
    SECKILL_REPEATE(50004,"重复秒杀"),
    SECKILL_OVER(50005,"商品秒杀结束")
    ;

    /**
     * 错误码
     */
    private int code;
    /**
     * 错误信息
     */
    private String msg;

    ResultStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }}
