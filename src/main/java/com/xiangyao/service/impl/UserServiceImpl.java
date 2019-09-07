package com.xiangyao.service.impl;

import com.xiangyao.common.enums.ResultStatus;
import com.xiangyao.common.exception.GlobalException;
import com.xiangyao.common.util.MD5Util;
import com.xiangyao.common.util.RequestUtil;
import com.xiangyao.common.util.UUIDUtil;
import com.xiangyao.common.util.VerifyCodeUtil;
import com.xiangyao.domains.User;
import com.xiangyao.mapper.UserMapper;
import com.xiangyao.redis.RedisService;
import com.xiangyao.redis.key.SeckillKey;
import com.xiangyao.redis.key.UserKey;
import com.xiangyao.service.IUserService;
import com.xiangyao.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.Date;

/**
 * @author xianggua
 * @description
 * @date 2019-7-20 18:13
 * @since 1.0
 */
@Service("userService")
@Slf4j
public class UserServiceImpl implements IUserService {

    public static final String COOKIE_NAME_TOKEN = "token";

    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisService redisService;

    /**
     * 生成注册的验证码
     *
     * @return 验证码图片
     * @author xianggua
     * @date 2019-7-21 19:13
     * @since 1.0
     */
    @Override
    public BufferedImage createVerifyCodeRegister() {
        //生成随机验证码
        String verifyCode = VerifyCodeUtil.generateVerifyCode();
        //创建验证码图片
        BufferedImage bufferedImage = VerifyCodeUtil.createVerifyCode(verifyCode);
        //验证码放入redis缓存
        int rnd = VerifyCodeUtil.calc(verifyCode);
        redisService.set(SeckillKey.seckillVerifyCodeRegister, "register", rnd);
        return bufferedImage;
    }

    /**
     * 校验注册的验证码
     *
     * @param verifyCode 验证码
     * @return
     * @author xianggua
     * @date 2019-7-21 19:13
     * @since 1.0
     */
    @Override
    public boolean checkVerifyCodeRegister(int verifyCode) {
        int realVerifyCode = redisService.get(SeckillKey.seckillVerifyCodeRegister, "register", Integer.class);
        return realVerifyCode == verifyCode;
    }

    /**
     * 用户注册
     *
     * @param username 用户名
     * @param password 密码
     * @param salt     加密盐
     * @return 是否注册成功
     * @author xianggua
     * @date 2019-7-21 19:14
     * @since 1.0
     */
    @Override
    public boolean register(String username, String password, String salt) {
        User user = new User();
        user.setNickname(username);
        String dbPassword = MD5Util.formPassToDBPass(password, salt);
        user.setPassword(dbPassword);
        user.setRegisterDate(new Date());
        user.setSalt(salt);

        try {
            userMapper.insertUser(user);
            User dbUser = userMapper.getUserByNickName(username);
            if (dbUser == null) {
                return false;
            }

            //TODO 发送消息注册成功,通过MQ发送消息

            String token = UUIDUtil.uuid();
            addCookie(RequestUtil.getHttpServletResponse(), token, user);
        } catch (Exception e) {
            log.error("注册失败", e);
            return false;
        }
        return true;
    }

    /**
     * 用户登录
     *
     * @param loginVo 登录用户信息
     * @author xianggua
     * @date 2019-7-21 19:14
     * @since 1.0
     */
    @Override
    public boolean login(LoginVo loginVo) {
        if (loginVo == null) {
            throw new GlobalException(ResultStatus.SYSTEM_ERROR);
        }
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        User user = getUserByNickName(mobile);
        if (user == null) {
            throw new GlobalException(ResultStatus.MOBILE_NOT_EXIST);
        }
        String dbPass = user.getPassword();
        String saltDb = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(password, saltDb);
        if (!calcPass.equals(dbPass)) {
            throw new GlobalException(ResultStatus.PASSWORD_ERROR);
        }
        //生成cookie,返回给浏览器
        String token = UUIDUtil.uuid();
        addCookie(RequestUtil.getHttpServletResponse(), token, user);
        return true;
    }

    private void addCookie(HttpServletResponse response, String token, User user) {
        redisService.set(UserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        //设置有效期
        cookie.setMaxAge(UserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @Override
    public User getUserByNickName(String nickName) {
        //从缓存中获取
        User user = redisService.get(UserKey.nickNameKey, "" + nickName, User.class);
        if (user != null) {
            return user;
        }
        //缓存未命中,从数据库中获取,并写入缓存
        user = userMapper.getUserByNickName(nickName);
        if (user != null) {
            redisService.set(UserKey.nickNameKey, "" + nickName, user);
        }
        return user;
    }

    @Override
    public User getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        User user = redisService.get(UserKey.token, token, User.class);
        if (user != null) {
            addCookie(response, token, user);
        }
        return user;
    }

}
