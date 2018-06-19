package com.ymhrj.ywjx.controller.vo;

import com.ymhrj.ywjx.db.entity.OrderDish;
import lombok.Data;

/**
 * @author : CGS
 * Date : 2018-03-25
 * Time : 15:25
 */
@Data
public class OrderDishVo extends OrderDish {
    private String dishName;
}
