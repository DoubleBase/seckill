package com.xiangyao.controller;

import com.xiangyao.common.action.BaseAction;
import com.xiangyao.common.constant.BasicConstant;
import com.xiangyao.common.result.ActionResult;
import com.xiangyao.redis.RedisLua;
import com.xiangyao.service.IUserService;
import com.xiangyao.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author xianggua
 * @description
 * @date 2019-7-5 0:28
 * @since 1.0
 */
@Controller
@RequestMapping("/login")
public class LoginAction extends BaseAction {

    private Logger logger = LoggerFactory.getLogger(LoginAction.class);

    @Resource
    private IUserService userService;

    @RequestMapping("/to_login")
    public String toLogin(LoginVo loginVo, Model model) {
        logger.info(loginVo.toString());
        RedisLua.visitorCount(BasicConstant.COUNT_LOGIN);
        String count = RedisLua.getVisitorCount(BasicConstant.COUNT_LOGIN).toString();
        logger.info("网站的访问次数为：{}", count);
        model.addAttribute("count", count);
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public ActionResult<Boolean> doLogin(@Valid LoginVo loginVo) {
        ActionResult<Boolean> result = ActionResult.build();
        logger.info(loginVo.toString());
        userService.login(loginVo);
        return result;
    }

}
