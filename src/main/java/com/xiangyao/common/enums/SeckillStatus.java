package com.xiangyao.common.enums;

/**
 * @author xianggua
 * @description 秒杀状态
 * @date 2019-7-16 22:18
 * @since 1.0
 */
public enum SeckillStatus {

    /**
     * 未开始
     */
    NOT_START(0),

    /**
     * 正在进行中
     */
    SECKILLING(1),

    /**
     * 秒杀结束
     */
    SECKILL_END(2)
    ;

    int status;

    SeckillStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }}
