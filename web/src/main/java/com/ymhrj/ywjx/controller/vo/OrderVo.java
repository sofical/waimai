package com.ymhrj.ywjx.controller.vo;

import com.ymhrj.ywjx.db.entity.Orders;
import lombok.Data;

import java.util.List;

/**
 * @author : 130801(cgs)
 * Date : 2018-02-26
 * Time : 11:54
 */
@Data
public class OrderVo extends Orders{
    private String shopName;
    private List<OrderDishVo> dishes;
}
