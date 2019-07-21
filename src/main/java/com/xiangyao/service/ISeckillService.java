package com.xiangyao.service;

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
     * @param goodsId
     * @return
     * @author   xianggua
     * @date   2019-7-20 17:30
     * @since 1.0
     */
    BufferedImage createVerifyCode(int goodsId);

    /**
     * 校验验证码是否正确
     * @param verifyCode 验证码
     * @param goodsId 商品ID
     * @return
     * @author   xianggua
     * @date   2019-7-20 17:30
     * @since 1.0
     */
    boolean checkVerifyCode(int verifyCode,long goodsId);

}
