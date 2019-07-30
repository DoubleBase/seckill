package com.xiangyao.vo;

import com.xiangyao.domains.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private User user;

}
