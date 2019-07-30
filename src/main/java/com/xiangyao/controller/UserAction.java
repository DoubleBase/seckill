package com.xiangyao.controller;

import com.xiangyao.common.action.BaseAction;
import com.xiangyao.common.enums.ResultStatus;
import com.xiangyao.common.result.ActionResult;
import com.xiangyao.domains.User;
import com.xiangyao.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author xianggua
 * @description
 * @date 2019-7-20 17:50
 * @since 1.0
 */
@Controller
@RequestMapping("/user")
public class UserAction extends BaseAction {

    private Logger logger = LoggerFactory.getLogger(UserAction.class);

    @Resource
    private IUserService userService;

    @RequestMapping("/registerIndex")
    public String registerIndex() {
        return "register";
    }

    /**
     * 生成注册验证码
     *
     * @return
     * @author xianggua
     * @date 2019-7-20 17:17
     * @since 1.0
     */
    @RequestMapping(value = "/verifyCodeRegister", method = RequestMethod.GET)
    @ResponseBody
    public ActionResult<String> verifyCodeRegister() {
        ActionResult<String> result = ActionResult.build();
        OutputStream out = null;
        try {
            BufferedImage image = userService.createVerifyCodeRegister();
            out = getServletResponse().getOutputStream();
            ImageIO.write(image, "JPEG", out);

            return result;
        } catch (IOException e) {
            logger.error("生成注册验证码错误", e);
            result.withError(ResultStatus.VERIFY_CODE_ERROR.getCode(), ResultStatus.VERIFY_CODE_ERROR.getMsg());
            return result;
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    logger.error("验证码图片流关闭失败!");
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 用户注册
     *
     * @param username   用户名
     * @param password   密码
     * @param verifyCode 验证码
     * @param salt       加密盐
     * @return
     */
    @RequestMapping("/register")
    @ResponseBody
    public ActionResult<String> register(@RequestParam("username") String username,
                                         @RequestParam("password") String password,
                                         @RequestParam("verifyCode") String verifyCode,
                                         @RequestParam("salt") String salt) {
        ActionResult<String> result = ActionResult.build();
        //校验验证码
        boolean check = userService.checkVerifyCodeRegister(Integer.valueOf(verifyCode));
        if (!check) {
            result.withError(ResultStatus.VERIFY_CODE_CHECK_FAILED);
            return result;
        }
        //查询相同手机号码是否已存在
        User dbUser = userService.getUserByNickName(username);
        if (dbUser != null) {
            result.withError(ResultStatus.MOBILE_EXIST);
            return result;
        }
        boolean register = userService.register(username, password, salt);
        if (!register) {
            result.withError(ResultStatus.REGISTER_FAILED);
            return result;
        }
        return result;
    }


}
