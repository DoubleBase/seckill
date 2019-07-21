package com.xiangyao.service.impl;

import com.xiangyao.common.util.VerifyCodeUtil;
import com.xiangyao.redis.RedisService;
import com.xiangyao.redis.key.SeckillKey;
import com.xiangyao.service.ISeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;

/**
 * @author xianggua
 * @description
 * @date 2019-7-20 16:40
 * @since 1.0
 */
@Service("seckillService")
@Slf4j
public class SeckillServiceImpl implements ISeckillService {

    @Resource
    private RedisService redisService;

    /**
     * 生成秒杀的验证码
     * @param goodsId
     * @return
     */
    @Override
    public BufferedImage createVerifyCode(int goodsId) {
        //生成随机验证码
        String verifyCode = VerifyCodeUtil.generateVerifyCode();
        //创建验证码图片
        BufferedImage bufferedImage = VerifyCodeUtil.createVerifyCode(verifyCode);
        //验证码放入redis缓存
        int rnd = VerifyCodeUtil.calc(verifyCode);
        redisService.set(SeckillKey.seckillVerifyCode, "" + goodsId, rnd);
        return bufferedImage;
    }



    /**
     * 校验秒杀时候的验证码
     * @param verifyCode 验证码
     * @param goodsId 商品ID
     * @return
     */
    @Override
    public boolean checkVerifyCode(int verifyCode,long goodsId) {
        int realVerifyCode = redisService.get(SeckillKey.seckillVerifyCode, "" + goodsId, Integer.class);
        return realVerifyCode == verifyCode;
    }


}
