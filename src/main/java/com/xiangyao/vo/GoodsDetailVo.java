package com.xiangyao.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author xianggua
 * @description
 * @date 2019-7-16 22:09
 * @since 1.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GoodsDetailVo {
    private int sekillStatus;
    private int remainSeconds;
    private GoodsVo goods;

}
