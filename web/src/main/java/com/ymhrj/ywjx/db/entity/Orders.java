package com.ymhrj.ywjx.db.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * Created by cgs on 2017/12/18.
 */
@Data
@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @Type(type = "uuid-char")
    @Column(name = "order_id")
    private UUID orderId;
    @Column(name = "shop_id")
    @Type(type = "uuid-char")
    private UUID shopId;
    @Column(name = "customer_id")
    @Type(type = "uuid-char")
    private UUID customerId;
    @Column(name = "platform")
    private Integer platform;
    @Column(name = "origin_fee")
    private BigDecimal originFee;
    @Column(name = "bonus_fee")
    private BigDecimal bonusFee;
    @Column(name = "real_fee")
    private BigDecimal realFee;
    @Column(name = "origin_order_id")
    private String originOrderId;
    @Column(name = "order_time")
    private Date orderTime;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "customer_address")
    private String customerAddress;
    @Column(name = "customer_phone")
    private String customerPhone;
}
