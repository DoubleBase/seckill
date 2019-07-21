package com.xiangyao.vo;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xianggua
 * @description
 * @date 2019-7-20 18:38
 * @since 1.0
 */
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MessageVo {

    private int id;
    private Long userId;
    private String goodsId;
    private String orderId;
    private long messageId;
    private String content;
    private Date createTime;
    private Integer status;
    private Date overTime;
    private int messageType;
    private int sendType;
    private String goodsName;
    private BigDecimal price;
    private String messageHead;

}
