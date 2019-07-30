package com.xiangyao.service;

import com.xiangyao.domains.Order;
import com.xiangyao.domains.User;
import com.xiangyao.vo.GoodsVo;

import java.awt.image.BufferedImage;

/**
 * @author xianggua
 * @description
 * @date 2019-7-20 16:40
 * @since 1.0
 */
public interface ISeckillService {

    /**
     * 创建验证码
     * @param user 登录用户
     * @param goodsId 商品ID
     * @return
     * @author   xianggua
     * @date   2019-7-20 17:30
     * @since 1.0
     */
    BufferedImage createVerifyCode(User user, int goodsId);

    /**
     * 校验验证码是否正确
     * @param user 登录用户
     * @param verifyCode 验证码
     * @param goodsId 商品ID
     * @return
     * @author   xianggua
     * @date   2019-7-20 17:30
     * @since 1.0
     */
    boolean checkVerifyCode(User user,int verifyCode,long goodsId);

    /**
     * 秒杀业务
     * @param user
     * @param goodsVo
     * @return
     */
    Order seckill(User user, GoodsVo goodsVo);

}
