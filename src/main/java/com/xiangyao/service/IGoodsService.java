package com.xiangyao.service;

import com.xiangyao.domains.Goods;
import com.xiangyao.vo.GoodsVo;

import java.util.List;

/**
 * @author xianggua
 * @description
 * @date 2019-6-18 0:55
 * @since 1.0
 */
public interface IGoodsService {

    int deleteByGoodsId(int goodsId);

    int insert(Goods record);

    List<GoodsVo> listGoods();

    GoodsVo getGoodsVoByGoodsId(int goodsId);

}
