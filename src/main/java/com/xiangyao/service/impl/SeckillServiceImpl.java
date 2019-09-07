package com.xiangyao.service.impl;

import com.xiangyao.common.util.VerifyCodeUtil;
import com.xiangyao.domains.Order;
import com.xiangyao.domains.User;
import com.xiangyao.redis.RedisService;
import com.xiangyao.redis.key.SeckillKey;
import com.xiangyao.service.IGoodsService;
import com.xiangyao.service.IOrderService;
import com.xiangyao.service.ISeckillService;
import com.xiangyao.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Resource
    private IGoodsService goodsService;
    @Resource
    private IOrderService orderService;

    /**
     * 生成秒杀的验证码
     *
     * @param user
     * @param goodsId
     * @return
     */
    @Override
    public BufferedImage createVerifyCode(User user, int goodsId) {
        //生成随机验证码
        String verifyCode = VerifyCodeUtil.generateVerifyCode();
        //创建验证码图片
        BufferedImage bufferedImage = VerifyCodeUtil.createVerifyCode(verifyCode);
        //验证码放入redis缓存
        int rnd = VerifyCodeUtil.calc(verifyCode);
        redisService.set(SeckillKey.seckillVerifyCode, user.getNickname() + "," + goodsId, rnd);
        return bufferedImage;
    }

    /**
     * 校验秒杀时候的验证码
     *
     * @param user       登录用户
     * @param verifyCode 验证码
     * @param goodsId    商品ID
     * @return
     */
    @Override
    public boolean checkVerifyCode(User user, int verifyCode, long goodsId) {
        int realVerifyCode = redisService.get(SeckillKey.seckillVerifyCode, user.getNickname() + "," + goodsId, Integer.class);
        return realVerifyCode == verifyCode;
    }

    @Override
    @Transactional
    public Order seckill(User user, GoodsVo goodsVo) {
        //减库存
        boolean result = goodsService.reduceStock(goodsVo);
        if (result) {
            log.info("用户ID={},商品ID={},减库存成功,下订单",user.getId(),goodsVo.getId());
            //减库存成功，则下订单
            return orderService.createOrder(user,goodsVo);
        }else{
            //标记无库存
            log.info("商品ID={},已经售完",goodsVo.getId());
            redisService.set(SeckillKey.isGoodsOver, "" + goodsVo.getId(), true);
            return null;
        }
    }

}
