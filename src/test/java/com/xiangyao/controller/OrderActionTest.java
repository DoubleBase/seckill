package com.xiangyao.controller;

import com.xiangyao.app.SeckillApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;

/**
 * @author xianggua
 * @description
 * @date 2019-9-7 23:57
 * @since 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SeckillApplication.class)
public class OrderActionTest {

    private MockMvc mockMvc;

    @Autowired
    private OrderAction orderAction;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(orderAction).build();
    }

    @Test
    public void getOrderDetailById() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/order/detail/3")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        ResultActions resultActions = mockMvc.perform(requestBuilder);

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();


    }
}