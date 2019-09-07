package com.xiangyao.service.impl;

import com.xiangyao.domains.Goods;
import com.xiangyao.domains.SeckillGoods;
import com.xiangyao.mapper.GoodsMapper;
import com.xiangyao.service.IGoodsService;
import com.xiangyao.vo.GoodsVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xianggua
 * @description
 * @date 2019-6-18 22:18
 * @since 1.0
 */
@Service
public class GoodsServiceImpl implements IGoodsService {

    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public int insert(Goods record) {
        return goodsMapper.insert(record);
    }

    @Override
    public List<GoodsVo> listGoods() {
        return goodsMapper.listGoods();
    }

    @Override
    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return goodsMapper.getGoodsVoByGoodsId(goodsId);
    }

    @Override
    public boolean reduceStock(GoodsVo goodsVo) {
        SeckillGoods goods = new SeckillGoods();
        goods.setGoodsId(goodsVo.getId());
        int ret = goodsMapper.reduceStock(goods);
        return ret > 0;
    }

}
