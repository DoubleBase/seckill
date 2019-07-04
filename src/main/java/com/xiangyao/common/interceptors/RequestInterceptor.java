package com.xiangyao.common.interceptors;

import com.xiangyao.common.util.RequestUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xianggua
 * @description
 * @date 2019-7-4 0:48
 * @since 1.0
 */
@Component
public class RequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //不满足条件的url可以直接过滤
        RequestUtil.addHttpServletRequest(request);
        RequestUtil.addHttpServletResponse(response);
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        RequestUtil.removeRequest();
        RequestUtil.removeResponse();
    }
}
