package com.xiangyao.common.exception;

import com.xiangyao.common.enums.ResultStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xianggua
 * @description 自定义全局异常
 * @date 2019-6-19 1:19
 * @since 1.0
 */
@Setter
@Getter
public class GlobalException extends RuntimeException {

    private ResultStatus status;

    public GlobalException(ResultStatus status) {
        super();
        this.status = status;
    }

}
