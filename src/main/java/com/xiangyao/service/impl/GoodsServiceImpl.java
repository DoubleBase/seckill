package com.xiangyao.service.impl;

import com.xiangyao.common.enums.ResultStatus;
import com.xiangyao.common.exception.GlobalException;
import com.xiangyao.domains.Goods;
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
    public int deleteByGoodsId(int goodsId) {
        return 0;
    }

    @Override
    public int insert(Goods record) {
        return goodsMapper.insert(record);
    }

    @Override
    public List<GoodsVo> listGoods() {
        return goodsMapper.listGoods();
    }


}
