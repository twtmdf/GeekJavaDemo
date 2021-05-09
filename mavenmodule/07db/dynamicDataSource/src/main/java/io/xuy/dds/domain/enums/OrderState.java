package io.xuy.dds.domain.enums;

/**
 * 状态, 0.新建/1.下单成功/2.已支付/3.过期未支付
 */
public enum OrderState {
    NEWS,ORDER_SUCCESS,PAY_SUCCESS,PAY_TIMEOUT;
}

