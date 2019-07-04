package com.xiangyao.common.exception;

import com.xiangyao.common.enums.ResultStatus;
import com.xiangyao.common.result.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author xianggua
 * @description 拦截异常
 * @date 2019-7-3 23:20
 * @since 1.0
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public ActionResult<String> exceptionHandler(HttpServletRequest request,Exception e){
        logger.error("global exception handler",e);
        if(e instanceof GlobalException){
            GlobalException exception = (GlobalException) e;
            return ActionResult.error(exception.getStatus());
        }else if(e instanceof BindException){
            BindException ex = (BindException) e;
            List<ObjectError> errors = ex.getAllErrors();
            ObjectError error = errors.get(0);
            String msg = error.getDefaultMessage();
            assert msg != null;
            logger.error(String.format(msg, msg));
            return ActionResult.error(ResultStatus.SYSTEM_ERROR);
        }else{
            return ActionResult.error(ResultStatus.SYSTEM_ERROR);
        }
    }

}
