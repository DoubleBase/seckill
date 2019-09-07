package com.xiangyao.common.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author xianggua
 * @description 拦截器配置
 * @date 2019-7-4 21:15
 * @since 1.0
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Autowired
    private RequestInterceptor requestInterceptor;
    @Autowired
    private UserArgumentResolver resolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestInterceptor).addPathPatterns("/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(resolver);
    }
}
