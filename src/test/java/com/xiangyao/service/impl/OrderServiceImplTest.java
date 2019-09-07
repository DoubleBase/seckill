package com.xiangyao.service.impl;

import com.xiangyao.app.SeckillApplication;
import com.xiangyao.domains.Order;
import com.xiangyao.mapper.OrderMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author xianggua
 * @description
 * @date 2019-9-7 23:05
 * @since 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SeckillApplication.class)
public class OrderServiceImplTest {

    @Autowired
    private OrderMapper orderMapper;

    @Before
    public void setUp() throws Exception {
        System.out.println("执行初始化...");
    }

    @Test
    public void createOrder() {
        Order order = new Order();
        order.setCreateDate(new Date());
        order.setGoodsId(1L);
        order.setGoodsCount(1);
        order.setGoodsName("测试");
        order.setStatus(0);
        order.setUserId(1L);
        orderMapper.insertOrder(order);
    }


}