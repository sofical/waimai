package com.ymhrj.ywjx.controller;

import com.ymhrj.ywjx.controller.vo.ChartVo;
import com.ymhrj.ywjx.service.base.OrderDishService;
import com.ymhrj.ywjx.service.base.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * @author : CGS
 * Date : 2018-04-01
 * Time : 15:08
 */
@RestController
@RequestMapping("/api/v1/hot")
public class HotController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDishService orderDishService;

    @RequestMapping(value = "/dishes", method = RequestMethod.GET)
    public List<ChartVo> getHotDishes(
            @RequestParam(value = "platform", required = false) Integer platform,
            @RequestParam(value = "shop_id", required = false) UUID shopId,
            @RequestParam(value = "begin_date", required = false) String beginDate,
            @RequestParam(value = "end_date", required = false) String endDate) {
        return this.orderDishService.getHotDishes(platform,shopId,beginDate,endDate);
    }

    @RequestMapping(value = "/shops", method = RequestMethod.GET)
    public List<ChartVo>  getHotShops(
            @RequestParam(value = "platform", required = false) Integer platform,
            @RequestParam(value = "shop_id", required = false) UUID shopId,
            @RequestParam(value = "begin_date", required = false) String beginDate,
            @RequestParam(value = "end_date", required = false) String endDate) {
        return this.orderService.getHotShops(platform,shopId,beginDate,endDate);
    }

    @RequestMapping(value = "/time", method = RequestMethod.GET)
    public List<ChartVo>  getHotTime(
            @RequestParam(value = "platform", required = false) Integer platform,
            @RequestParam(value = "shop_id", required = false) UUID shopId,
            @RequestParam(value = "begin_date", required = false) String beginDate,
            @RequestParam(value = "end_date", required = false) String endDate) {
        return this.orderService.getHotHour(platform,shopId,beginDate,endDate);
    }
}
