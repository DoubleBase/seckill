package com.xiangyao.service;

import com.xiangyao.domains.Order;
import com.xiangyao.domains.User;
import com.xiangyao.vo.GoodsVo;

/**
 * @author xianggua
 * @description
 * @date 2019-7-20 17:43
 * @since 1.0
 */
public interface IOrderService {
    /**
     * 获取用户已秒杀的订单
     * @param userId 用户ID
     * @param goodsId 商品ID
     * @return
     */
    Order getSeckillOrderByUserIdAndGoodsId(long userId,long goodsId);

    /**
     * 创建订单
     * @param user 用户
     * @param goodsVo 商品
     */
    Order createOrder(User user, GoodsVo goodsVo);
}
