package com.xiangyao.controller;

import com.xiangyao.common.action.BaseAction;
import com.xiangyao.common.result.ActionResult;
import com.xiangyao.redis.RedisService;
import com.xiangyao.redis.key.GoodsKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xianggua
 * @description
 * @date 2019-7-4 22:55
 * @since 1.0
 */
@RestController
@RequestMapping("/demo")
public class DemoAction extends BaseAction {
    @Autowired
    private RedisService redisService;

    @GetMapping(value = "/addRedis")
    public ActionResult addRedis() {
        redisService.set(GoodsKey.goodListKey,"","测试添加redis");
        return ActionResult.build();
    }

    @GetMapping(value = "/getRedis")
    public ActionResult getRedis(){
        String value = redisService.get(GoodsKey.goodListKey,"",String.class);
        ActionResult result = ActionResult.build();
        result.setData(value);
        return result;
    }

    @GetMapping(value = "/deleteRedis")
    public ActionResult deleteRedis(){
        boolean flag = redisService.delete(GoodsKey.goodListKey,"");
        ActionResult result = ActionResult.build();
        result.setData(flag);
        return result;
    }



}
