package com.xiangyao.controller;

import com.xiangyao.common.action.BaseAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/to_login")
    public String toLogin(){

        return "";
    }



}
