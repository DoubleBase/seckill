package com.xiangyao.controller;

import com.xiangyao.common.action.BaseAction;
import com.xiangyao.common.enums.SeckillStatus;
import com.xiangyao.common.result.ActionResult;
import com.xiangyao.domains.User;
import com.xiangyao.redis.key.GoodsKey;
import com.xiangyao.service.IGoodsService;
import com.xiangyao.vo.GoodsDetailVo;
import com.xiangyao.vo.GoodsVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
     * localhost:8080/seckill/goods/listGoods
     * 1000*10
     * QPS:357.5
     *
     * @author xianggua
     * @date 2019-6-18 22:32
     * @since 1.0
     */
    @RequestMapping(value = "/listGoods", produces = "text/html")
    @ResponseBody
    public String listGoods(User user, Model model) {
        List<GoodsVo> goodsList = goodsService.listGoods();
        model.addAttribute("goodsList", goodsList);
        model.addAttribute("user", user);
        return render(model, "goods_list");
    }

    /**
     * 获取商品列表,使用redis缓存
     * 1000*10
     * QPS:578.8
     *
     * @author xianggua
     * @date 2019-6-18 22:32
     * @since 1.0
     */
    @RequestMapping(value = "/listGoodsV2", produces = "text/html")
    @ResponseBody
    public String listGoodsV2(Model model, User user) {
        List<GoodsVo> goodsList = goodsService.listGoods();
        model.addAttribute("goodsList", goodsList);
        model.addAttribute("user", user);
        return renderV2(model, "goods_list", GoodsKey.goodListKey, "");
    }

    /**
     * 查询商品详情
     *
     * @param goodsId
     * @return
     * @author xianggua
     * @date 2019-7-16 22:07
     * @since 1.0
     */
    @RequestMapping(value = "/detail/{goodsId}")
    @ResponseBody
    public ActionResult<GoodsDetailVo> detail(@PathVariable("goodsId") int goodsId, User user) {
        ActionResult<GoodsDetailVo> result = ActionResult.build();
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        long startTime = goodsVo.getStartDate().getTime();
        long endTime = goodsVo.getEndDate().getTime();
        long now = System.currentTimeMillis();
        SeckillStatus seckillStatus;
        int remainSeconds;
        if (now < startTime) {
            //未开始,设置倒计时
            seckillStatus = SeckillStatus.NOT_START;
            remainSeconds = (int) ((startTime - now) / 1000);
        } else if (now > endTime) {
            //秒杀结束
            seckillStatus = SeckillStatus.SECKILL_END;
            remainSeconds = -1;
        } else {
            //秒杀进行中
            seckillStatus = SeckillStatus.SECKILLING;
            remainSeconds = 0;
        }
        GoodsDetailVo goodsDetailVo = new GoodsDetailVo();
        goodsDetailVo.setGoods(goodsVo);
        goodsDetailVo.setUser(user);
        goodsDetailVo.setSekillStatus(seckillStatus.getStatus());
        goodsDetailVo.setRemainSeconds(remainSeconds);
        result.setData(goodsDetailVo);
        return result;
    }


}
