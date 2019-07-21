package com.xiangyao.service;

import com.xiangyao.vo.LoginVo;

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

}
