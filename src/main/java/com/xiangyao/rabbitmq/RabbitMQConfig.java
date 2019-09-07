package com.xiangyao.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xianggua
 * @description
 * @date 2019-7-29 23:00
 * @since 1.0
 */
@Configuration
public class RabbitMQConfig {

    public static final String SECKILL_QUEUE = "seckill.queue";

    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;
    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualhost;

    @Bean
    public ConnectionFactory connection(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualhost);
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }

    @Bean
    public Queue seckillQueue(){
        return new Queue(SECKILL_QUEUE);
    }

}
