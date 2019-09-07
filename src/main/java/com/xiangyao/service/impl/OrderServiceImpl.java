package com.xiangyao.service.impl;

import com.xiangyao.domains.Order;
import com.xiangyao.domains.SeckillOrder;
import com.xiangyao.domains.User;
import com.xiangyao.mapper.OrderMapper;
import com.xiangyao.redis.RedisService;
import com.xiangyao.redis.key.OrderKey;
import com.xiangyao.service.IOrderService;
import com.xiangyao.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

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
    @Resource
    private RedisService redisService;

    @Override
    public SeckillOrder getSeckillOrderByUserIdAndGoodsId(long userId, long goodsId) {
        return redisService.get(OrderKey.getSeckillOrderByUidGid,userId+"_"+goodsId,SeckillOrder.class);
    }

    @Override
    @Transactional
    public Order createOrder(User user, GoodsVo goodsVo) {
        Order order = new Order();
        order.setCreateDate(new Date());
        order.setGoodsId(goodsVo.getId());
        order.setGoodsCount(1);
        order.setGoodsName(goodsVo.getGoodsName());
        order.setStatus(0);
        order.setUserId(Long.valueOf(user.getNickname()));
        long orderId = orderMapper.insertOrder(order);

        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setGoodsId(order.getGoodsId());
        seckillOrder.setOrderId(orderId);
        seckillOrder.setUserId(Long.valueOf(user.getNickname()));
        orderMapper.insertSeckillOrder(seckillOrder);
        String key = user.getNickname() + "_" + goodsVo.getId();
        redisService.set(OrderKey.getSeckillOrderByUidGid, key, seckillOrder);
        return order;
    }

    @Override
    public Order getOrderById(long orderId) {
        return orderMapper.getOrderById(orderId);
    }
}
