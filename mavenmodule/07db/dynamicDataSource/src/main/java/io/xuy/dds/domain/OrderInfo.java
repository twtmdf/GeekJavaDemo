package io.xuy.dds.domain;

import io.xuy.dds.domain.enums.DeleteFlag;
import io.xuy.dds.domain.enums.OrderState;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "order_info")
public class OrderInfo extends AbstractModel{
    @Id
    @GeneratedValue(generator  = "dynamicIdentityGenerator")
    @GenericGenerator(name = "dynamicIdentityGenerator" ,strategy = "io.xuy.dds.config.DynamicIdentityGenerator")
    private Long id;

    /**
     * 用户标识
     */
    @Column(name = "fk_user_id")
    private Long userId;
    /**
     * 用户账户标识
     */
    @Column(name = "fk_user_account_id")
    private Long userAccountId;

    /**
     * 货币
     */
    private String currency;
    /**
     * 扣减总金额
     */
    @Column(name = "discount_amount")
    private BigDecimal discountAmount;

    /**
     * 商品价值总金额
     */
    @Column(name = "shipping_amount")
    private BigDecimal shippingAmount;

    /**
     * 支付总金额
     */
    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    /**
     * 活动扣减金额
     */
    @Column(name = "activity_off")
    private BigDecimal activityOff;

    /**
     * 优惠券扣减金额
     */
    @Column(name = "coupon_off")
    private BigDecimal couponOff;

    /**
     * 支付渠道
     */
    @Column(name = "fk_pay_channel_id")
    private String payChannelId;

    /**
     * 交易流水号
     */
    @Column(name = "fk_pay_seq")
    private String paySeq;

    /**
     * 订单状态
     */
    @Enumerated(EnumType.ORDINAL)
    private OrderState state;
    /**
     * 支付时间
     */
    @Column(name = "pay_time")
    private Instant payTime;

    /**
     * 订单记录状态
     */
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "deleted")
    private DeleteFlag deleteFlag;
}
