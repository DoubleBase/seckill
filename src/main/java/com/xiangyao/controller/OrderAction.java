package com.xiangyao.controller;

import com.xiangyao.common.action.BaseAction;
import com.xiangyao.common.enums.ResultStatus;
import com.xiangyao.common.result.ActionResult;
import com.xiangyao.domains.Order;
import com.xiangyao.domains.User;
import com.xiangyao.service.IGoodsService;
import com.xiangyao.service.IOrderService;
import com.xiangyao.vo.GoodsVo;
import com.xiangyao.vo.OrderDetailVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author xianggua
 * @description
 * @date 2019-9-7 23:46
 * @since 1.0
 */
@Controller
@RequestMapping("/order")
public class OrderAction extends BaseAction {

    @Resource
    private IOrderService orderService;
    @Resource
    private IGoodsService goodsService;

    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    @ResponseBody
    public ActionResult<OrderDetailVo> getOrderDetailById(@RequestParam("orderId") int orderId, User user) {
        ActionResult<OrderDetailVo> result = ActionResult.build();
        if (user == null) {
            result.withError(ResultStatus.SESSION_ERROR);
            return result;
        }
        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            result.withError(ResultStatus.ORDER_NOT_EXIST);
            return result;
        }
        long goodsId = order.getGoodsId();
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);

        OrderDetailVo vo = new OrderDetailVo();
        vo.setOrder(order);
        vo.setGoods(goods);
        result.setData(vo);
        return result;
    }
}
