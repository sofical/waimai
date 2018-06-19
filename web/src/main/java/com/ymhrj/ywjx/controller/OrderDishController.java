package com.ymhrj.ywjx.controller;

import com.ymhrj.ywjx.controller.vo.ChartVo;
import com.ymhrj.ywjx.service.base.OrderDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * @author : CGS
 * Date : 2018-05-05
 * Time : 17:54
 */
@RestController
@RequestMapping("/api/v1/order_dishes")
public class OrderDishController {

    @Autowired
    private OrderDishService orderDishService;

    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    public List<ChartVo> getHotShops(
            @RequestParam(value = "platform", required = false) Integer platform,
            @RequestParam(value = "shop_id", required = false) UUID shopId,
            @RequestParam(value = "begin_date", required = false) String beginDate,
            @RequestParam(value = "end_date", required = false) String endDate,
            @RequestParam(value = "column", required = false,defaultValue = "style") String column) {
        return this.orderDishService.statByDimension(platform,shopId,beginDate,endDate,column);
    }
}
