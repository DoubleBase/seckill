package com.xiangyao.rabbitmq;

import com.xiangyao.common.util.StringUtil;
import com.xiangyao.domains.Order;
import com.xiangyao.domains.SeckillOrder;
import com.xiangyao.domains.User;
import com.xiangyao.service.IGoodsService;
import com.xiangyao.service.IOrderService;
import com.xiangyao.service.ISeckillService;
import com.xiangyao.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author xianggua
 * @description
 * @date 2019-7-29 23:03
 * @since 1.0
 */
@Service
@Slf4j
public class RabbitMqReceiver {
    @Resource
    private IGoodsService goodsService;
    @Resource
    private IOrderService orderService;
    @Resource
    private ISeckillService seckillService;

    @RabbitListener(queues = RabbitMQConfig.SECKILL_QUEUE)
    public void receive(String message) {
        log.info("receive msg : {}", message);
        SeckillMessage seckillMessage = StringUtil.stringToBean(message, SeckillMessage.class);
        if (seckillMessage == null) {
            log.error("seckill message is null");
            return;
        }
        User user = seckillMessage.getUser();
        long goodsId = seckillMessage.getGoodsId();

        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goodsVo.getGoodsStock();

        if (stock <= 0) {
            //库存不足
            log.info("商品ID={},库存不足",goodsId);
            return;
        }

        //查询订单是否已创建
        SeckillOrder order = orderService.getSeckillOrderByUserIdAndGoodsId(user.getId(), goodsId);
        if (order != null) {
            log.info("用户ID={}购买的商品ID={}的订单已存在",user.getId(),goodsId);
            return;
        }

        //执行秒杀逻辑，减库存，下订单，写入秒杀订单
        seckillService.seckill(user, goodsVo);
    }

}
