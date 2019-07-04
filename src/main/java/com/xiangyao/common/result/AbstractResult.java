package com.xiangyao.common.result;

import com.xiangyao.common.enums.ResultStatus;

/**
 * @author xianggua
 * @description
 * @date 2019-7-3 22:44
 * @since 1.0
 */
public class AbstractResult {

    private int code;
    private String message;

    protected AbstractResult(ResultStatus status, String message) {
        this.code = status.getCode();
        this.message = message;
    }

    protected AbstractResult(ResultStatus status) {
        this.code = status.getCode();
        this.message = status.getMsg();
    }

    public static boolean isSuccess(AbstractResult result) {
        return result != null && result.getCode() == ResultStatus.SUCCESS.getCode();
    }

    public AbstractResult withError(ResultStatus status) {
        return new AbstractResult(status);
    }

    public AbstractResult withError(String message) {
        return new AbstractResult(ResultStatus.SYSTEM_ERROR,message);
    }

    public AbstractResult withError(int code, String message) {
        this.code = code;
        this.message = message;
        return this;
    }

    public AbstractResult success() {
        return new AbstractResult(ResultStatus.SUCCESS);
    }

    public String getMessage() {
        return this.message;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
