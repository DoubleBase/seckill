package com.xiangyao.rabbitmq;

import com.xiangyao.domains.User;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xianggua
 * @description
 * @date 2019-7-29 23:04
 * @since 1.0
 */
@Setter
@Getter
public class SeckillMessage {

    private User user;
    private long goodsId;

}
