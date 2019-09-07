package com.xiangyao.vo;

import com.xiangyao.domains.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author xianggua
 * @description
 * @date 2019-9-7 23:48
 * @since 1.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailVo {

    private GoodsVo goods;
    private Order order;

}
