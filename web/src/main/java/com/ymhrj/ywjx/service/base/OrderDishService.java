package com.ymhrj.ywjx.service.base;

import com.ymhrj.ywjx.controller.vo.ChartVo;
import com.ymhrj.ywjx.controller.vo.OrderDishVo;

import java.util.List;
import java.util.UUID;

/**
 * @author : CGS
 * Date : 2018-03-25
 * Time : 16:00
 */
public interface OrderDishService {
    List<OrderDishVo> findByOrderId(UUID orderId);

    List<ChartVo> getHotDishes(Integer platform, UUID shopId, String beginDate, String endDate);


    List<ChartVo> statByDimension(Integer platform, UUID shopId, String beginDate, String endDate, String column);
}
