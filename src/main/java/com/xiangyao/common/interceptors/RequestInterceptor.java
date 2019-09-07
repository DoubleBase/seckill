package com.xiangyao.common.interceptors;

import com.xiangyao.common.util.RequestUtil;
import com.xiangyao.domains.User;
import com.xiangyao.redis.RedisService;
import com.xiangyao.service.IUserService;
import com.xiangyao.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xianggua
 * @description
 * @date 2019-7-4 0:48
 * @since 1.0
 */
@Service
public class RequestInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private IUserService userService;

    @Resource
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //不满足条件的url可以直接过滤
        RequestUtil.addHttpServletRequest(request);
        RequestUtil.addHttpServletResponse(response);
        if (handler instanceof HandlerMethod) {
            User user = getUser(request,response);
            RequestUtil.setUser(user);
        }

        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        RequestUtil.removeRequest();
        RequestUtil.removeResponse();
        RequestUtil.removeUser();
    }

    private User getUser(HttpServletRequest request, HttpServletResponse response) {
        String paramToken = request.getParameter(UserServiceImpl.COOKIE_NAME_TOKEN);
        String cookieToken = getCookieValue(request, UserServiceImpl.COOKIE_NAME_TOKEN);
        if (StringUtils.isEmpty(paramToken) && StringUtils.isEmpty(cookieToken)) {
            return null;
        }
        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        return userService.getByToken(response, token);
    }

    private String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length <= 0) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookieName.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
