package com.xiangyao.common.result;

import com.xiangyao.common.enums.ResultStatus;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author xianggua
 * @description
 * @date 2019-6-17 0:43
 * @since 1.0
 */
@Setter
@Getter
public class ActionResult<T> extends AbstractResult implements Serializable {

    private T data;

    public ActionResult(ResultStatus status, String message) {
        super(status, message);
    }

    public ActionResult(ResultStatus status) {
        super(status);
    }

    public static <T> ActionResult<T> build() {
        return new ActionResult<>(ResultStatus.SUCCESS);
    }

    public static <T> ActionResult<T> build(String message) {
        return new ActionResult<>(ResultStatus.SUCCESS, message);
    }

    public static <T> ActionResult<T> error(ResultStatus status) {
        return new ActionResult<>(status);
    }

    public void success(T value) {
        this.success();
        this.data = value;
    }

}
