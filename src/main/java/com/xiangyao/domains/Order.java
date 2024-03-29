package com.xiangyao.domains;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author xianggua
 * @description
 * @date 2019-7-20 17:44
 * @since 1.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Long id;
    private Long userId;
    private Long orderId;
    private Long goodsId;
    private Date createDate;
    private Date payDate;
    private Double goodsPrice;
    private Integer goodsCount;
    private String goodsName;
    private Integer status;

}
