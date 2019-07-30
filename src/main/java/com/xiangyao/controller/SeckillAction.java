package com.xiangyao.controller;

import com.xiangyao.common.action.BaseAction;
import com.xiangyao.common.enums.ResultStatus;
import com.xiangyao.common.result.ActionResult;
import com.xiangyao.domains.Order;
import com.xiangyao.domains.User;
import com.xiangyao.rabbitmq.RabbitMqSender;
import com.xiangyao.rabbitmq.SeckillMessage;
import com.xiangyao.redis.RedisService;
import com.xiangyao.redis.key.GoodsKey;
import com.xiangyao.service.IGoodsService;
import com.xiangyao.service.IOrderService;
import com.xiangyao.service.ISeckillService;
import com.xiangyao.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

/**
 * @author xianggua
 * @description
 * @date 2019-7-20 15:15
 * @since 1.0
 */
@Controller
@RequestMapping("/seckill")
public class SeckillAction extends BaseAction implements InitializingBean {

    private Logger logger = LoggerFactory.getLogger(SeckillAction.class);

    @Resource
    private ISeckillService seckillService;
    @Resource
    private IOrderService orderService;
    @Resource
    private RedisService redisService;
    @Resource
    private IGoodsService goodsService;
    @Resource
    private RabbitMqSender rabbitMqSender;

    private HashMap<Long, Boolean> localOverMap = new HashMap<>();

    /**
     * 生成验证码
     *
     * @param goodsId 商品ID
     * @return
     * @author xianggua
     * @date 2019-7-20 17:17
     * @since 1.0
     */
    @RequestMapping(value = "/verifyCode", method = RequestMethod.GET)
    @ResponseBody
    public ActionResult<String> verifyCode(User user, int goodsId) {
        ActionResult<String> result = ActionResult.build();
        if (user == null) {
            result.withError(ResultStatus.SESSION_ERROR);
            return result;
        }
        try {
            BufferedImage image = seckillService.createVerifyCode(user, goodsId);
            OutputStream out = getServletResponse().getOutputStream();
            ImageIO.write(image, "JPEG", out);
            out.flush();
            out.close();
            return result;
        } catch (IOException e) {
            logger.error("生成验证码错误------goodsId:{}", goodsId, e);
            result.withError(ResultStatus.VERIFY_CODE_ERROR.getCode(), ResultStatus.VERIFY_CODE_ERROR.getMsg());
            return result;
        }
    }

    /**
     * 秒杀商品
     *
     * @param goodsId 商品ID
     * @author xianggua
     * @date 2019-7-20 17:18
     * @since 1.0
     */
    @RequestMapping(value = "/do_seckill", method = RequestMethod.POST)
    @ResponseBody
    public ActionResult<Integer> doSeckill(User user, int verifyCode, long goodsId) {
        ActionResult<Integer> result = ActionResult.build();
        //0.校验验证码是否正确
        boolean check = seckillService.checkVerifyCode(user, verifyCode, goodsId);
        //验证码校验失败,则返回错误信息
        if (!check) {
            result.withError(ResultStatus.VERIFY_CODE_CHECK_FAILED);
            return result;
        }
        //检查订单是否存在,防止重复下单
        Order order = orderService.getSeckillOrderByUserIdAndGoodsId(Long.parseLong(user.getNickname()), goodsId);
        if (order != null) {
            //订单已存在,重复秒杀
            result.withError(ResultStatus.SECKILL_REPEATE);
            return result;
        }
        //内存标记,减少redis访问
        boolean over = localOverMap.get(goodsId);
        if (over) {
            result.withError(ResultStatus.SECKILL_OVER);
            return result;
        }
        //查看库存是否足够
        Long stock = redisService.decr(GoodsKey.goodStockKey, "" + goodsId);
        if (stock < 0) {
            localOverMap.put(goodsId, true);
            result.withError(ResultStatus.SECKILL_OVER);
            return result;
        }
        //发送MQ消息,生成订单
        SeckillMessage seckillMessage = new SeckillMessage();
        seckillMessage.setUser(user);
        seckillMessage.setGoodsId(goodsId);
        rabbitMqSender.sendSeckillMessage(seckillMessage);
        return result;
    }

    /**
     * 系统初始化,将秒杀商品库存存入redis
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsList = goodsService.listGoods();
        if (CollectionUtils.isEmpty(goodsList)) {
            return;
        }
        for (GoodsVo goods : goodsList) {
            redisService.set(GoodsKey.goodStockKey, "" + goods.getId(), goods.getStockCount());
            localOverMap.put(goods.getId(), false);
        }
    }
}


