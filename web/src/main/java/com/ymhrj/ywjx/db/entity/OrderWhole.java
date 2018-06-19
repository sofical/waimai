package com.ymhrj.ywjx.db.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.UUID;

/**
 * Created by Administrator on 2017/12/14.
 * `order_whole_id` char(36) NOT NULL,
 `plant` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1-美团、2-饿了么、3-百度',
 `shop_id` char(64) NOT NULL,
 `order_id` char(64) NOT NULL,
 `order_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0交易中、1交易成功、2退单',
 `customer_name` varchar(30) NOT NULL,
 `customer_phone` varchar(15) NOT NULL,
 `customer_sex` tinyint(4) NOT NULL COMMENT '0未知1男2女',
 `original_price` float NOT NULL DEFAULT '0',
 `shipping_fee` float NOT NULL,
 `total` float NOT NULL,
 `source_order` text NOT NULL,
 `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
 `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 */
@Data
@Entity
@Table(name = "order_whole")
public class OrderWhole {
    @Id
    @Type(type = "uuid-char")
    @Column(name = "order_whole_id")
    private UUID orderWholeId;
    @Column(name = "plant")
    private Integer plant = 1;
    @Column(name = "shop_id")
    private String shopId;
    @Column(name = "order_id")
    private String orderId;
    @Column(name = "order_status")
    private Integer orderStatus;
    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "customer_phone")
    private String customerPhone;
}
