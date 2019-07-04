package com.xiangyao.controller;

import com.xiangyao.common.action.BaseAction;
import com.xiangyao.redis.key.GoodsKey;
import com.xiangyao.service.IGoodsService;
import com.xiangyao.vo.GoodsVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xianggua
 * @description 秒杀商品Action
 * @date 2019-6-17 1:17
 * @since 1.0
 */
@Controller
@RequestMapping("/goods")
public class GoodsAction extends BaseAction {
    @Resource
    private IGoodsService goodsService;

    /**
     * 获取商品列表
     *
     * @author xianggua
     * @date 2019-6-18 22:32
     * @since 1.0
     */
    @RequestMapping(value = "/listGoods", produces = "text/html")
    @ResponseBody
    public String listGoods(Model model) {
        List<GoodsVo> goodsList = goodsService.listGoods();
        model.addAttribute("goodsList", goodsList);
        return render(model, "goods_list");
    }

    /**
     * 获取商品列表,使用redis缓存
     *
     * @author xianggua
     * @date 2019-6-18 22:32
     * @since 1.0
     */
    @RequestMapping(value = "/listGoodsV2", produces = "text/html")
    @ResponseBody
    public String listGoodsV2(Model model) {
        List<GoodsVo> goodsList = goodsService.listGoods();
        model.addAttribute("goodsList", goodsList);
        return renderV2(model, "goods_list", GoodsKey.goodListKey,"");
    }




}
