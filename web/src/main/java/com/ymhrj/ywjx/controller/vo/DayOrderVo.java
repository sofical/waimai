package com.ymhrj.ywjx.controller.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author : CGS
 * Date : 2018-03-17
 * Time : 14:51
 */
@Data
public class DayOrderVo {
    private UUID shopId;
    private Integer platform;
    private String orderDate;
    private BigDecimal fee;
    private String shopName;

    public DayOrderVo(UUID shopId, Integer platform, String orderDate, BigDecimal fee) {
        this.shopId = shopId;
        this.platform = platform;
        this.orderDate = orderDate;
        this.fee = fee;

    }
}
