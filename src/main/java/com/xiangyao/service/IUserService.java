package com.xiangyao.service;

import com.xiangyao.domains.User;
import com.xiangyao.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

/**
 * @author xianggua
 * @description
 * @date 2019-7-20 18:12
 * @since 1.0
 */
public interface IUserService {

    BufferedImage createVerifyCodeRegister();

    boolean checkVerifyCodeRegister(int verifyCode);

    boolean register(String username, String password, String salt);

    boolean login(LoginVo loginVo);

    User getUserByNickName(String nickname);

    User getByToken(HttpServletResponse response,String token);
}
