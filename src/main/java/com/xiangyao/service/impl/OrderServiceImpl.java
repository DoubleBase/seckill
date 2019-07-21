package com.xiangyao.service.impl;

import com.xiangyao.domains.Order;
import com.xiangyao.mapper.OrderMapper;
import com.xiangyao.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author xianggua
 * @description
 * @date 2019-7-20 17:43
 * @since 1.0
 */
@Service("orderService")
@Slf4j
public class OrderServiceImpl implements IOrderService {

    @Resource
    private OrderMapper orderMapper;

    @Override
    public Order getSeckillOrderByUserIdAndGoodsId(long userId, long goodsId) {
        return null;
    }
}
