package com.xiangyao.common.action;

import com.xiangyao.common.result.ActionResult;
import com.xiangyao.common.util.RequestUtil;
import com.xiangyao.redis.RedisService;
import com.xiangyao.redis.key.KeyPrefix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author xianggua
 * @description
 * @date 2019-6-17 1:18
 * @since 1.0
 */
@Component
public class BaseAction {

    private static Logger logger = LoggerFactory.getLogger(BaseAction.class);
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;
    @Autowired
    private RedisService redisService;

    protected String render(Model model, String htmlName) {
        HttpServletRequest request = getServletRequest();
        HttpServletResponse response = getServletResponse();
        WebContext context = new WebContext(request, response,
                request.getServletContext(), response.getLocale(), model.asMap());
        String html = thymeleafViewResolver.getTemplateEngine().process(htmlName, context);
        out(response, html);
        return null;
    }

    protected String renderV2(Model model, String htmlName,
                              KeyPrefix keyPrefix, String key) {
        HttpServletRequest request = getServletRequest();
        HttpServletResponse response = getServletResponse();
        String cacheHtml = redisService.get(keyPrefix, key, String.class);
        //缓存命中,直接返回
        if (!StringUtils.isEmpty(cacheHtml)) {
            out(response, cacheHtml);
            return null;
        }

        WebContext context = new WebContext(request, response,
                request.getServletContext(), response.getLocale(), model.asMap());
        String html = thymeleafViewResolver.getTemplateEngine().process(htmlName, context);
        if (!StringUtils.isEmpty(html)) {
            redisService.set(keyPrefix, key, html);
        }
        out(response, html);
        return null;
    }

    public static void out(HttpServletResponse response, String html) {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        try {
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(html.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            logger.error("BaseAction out error", e);
        }
    }

    public static HttpServletRequest getServletRequest() {
        return RequestUtil.getHttpServletRequest();
    }

    public static HttpServletResponse getServletResponse() {
        return RequestUtil.getHttpServletResponse();
    }

}
