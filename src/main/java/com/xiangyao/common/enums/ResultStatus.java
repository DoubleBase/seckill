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
    SYSTEM_ERROR(10003,"系统错误")

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
