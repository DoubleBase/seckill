package com.xiangyao.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xianggua
 * @description 每个请求的request和response变量存储
 * @date 2019-7-4 21:24
 * @since 1.0
 */
public class RequestUtil {

    private static ThreadLocal<HttpServletRequest> requestThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<HttpServletResponse> responseThreadLocal = new ThreadLocal<>();

    public static void addHttpServletRequest(HttpServletRequest request){
        requestThreadLocal.set(request);
    }

    public static HttpServletRequest getHttpServletRequest() {
        return requestThreadLocal.get();
    }

    public static void removeRequest(){
        requestThreadLocal.remove();
    }

    public static void addHttpServletResponse(HttpServletResponse response){
        responseThreadLocal.set(response);
    }

    public static HttpServletResponse getHttpServletResponse(){
        return responseThreadLocal.get();
    }

    public static void removeResponse(){
        responseThreadLocal.remove();
    }

}
