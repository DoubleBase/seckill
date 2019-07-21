package com.xiangyao.service;

import com.xiangyao.domains.Order;

/**
 * @author xianggua
 * @description
 * @date 2019-7-20 17:43
 * @since 1.0
 */
public interface IOrderService {

    Order getSeckillOrderByUserIdAndGoodsId(long userId,long goodsId);

}
