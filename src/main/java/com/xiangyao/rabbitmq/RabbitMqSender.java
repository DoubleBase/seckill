package com.xiangyao.rabbitmq;

import com.xiangyao.common.util.StringUtil;
import com.xiangyao.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xianggua
 * @description
 * @date 2019-7-29 23:03
 * @since 1.0
 */
@Slf4j
@Service
public class RabbitMqSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送秒杀消息
     * @param seckillMessage
     */
    public void sendSeckillMessage(SeckillMessage seckillMessage) {
        String msg = StringUtil.beanToString(seckillMessage);
        log.info("send msg : {}", msg);
        rabbitTemplate.convertAndSend(RabbitMQConfig.SECKILL_QUEUE, msg);
    }




}
