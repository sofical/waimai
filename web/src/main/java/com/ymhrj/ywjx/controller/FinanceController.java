package com.ymhrj.ywjx.controller;

import com.ymhrj.ywjx.controller.vo.BrokenLineVo;
import com.ymhrj.ywjx.controller.vo.ChartVo;
import com.ymhrj.ywjx.controller.vo.DayOrderVo;
import com.ymhrj.ywjx.controller.vo.PageData;
import com.ymhrj.ywjx.service.base.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * @author : cgs
 * Date : 2018-02-26
 * Time : 11:51
 */
@RestController
@RequestMapping("/api/v1/finance")
public class FinanceController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/day_stat", method = RequestMethod.GET)
    public PageData<DayOrderVo> getDayStat(
            @RequestParam(value = "platform", required = false) Integer platform,
            @RequestParam(value = "shop_id", required = false) UUID shopId,
            @RequestParam(value = "begin_date", required = false) String beginDate,
            @RequestParam(value = "end_date", required = false) String endDate,
            @RequestParam(value = "column", required = false,defaultValue = "origin_fee") String column,
            @RequestParam(value = "asc", required = false,defaultValue = "true") Boolean asc,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
       return this.orderService.getDayStat(platform,shopId,beginDate,endDate,column,page,limit,asc);
    }

    @RequestMapping(value = "/every_day_stat", method = RequestMethod.GET)
    public BrokenLineVo getShops(
            @RequestParam(value = "platform", required = false) Integer platform,
            @RequestParam(value = "shop_id", required = false) UUID shopId,
            @RequestParam(value = "begin_date", required = false) String beginDate,
            @RequestParam(value = "end_date", required = false) String endDate,
            @RequestParam(value = "column", required = false,defaultValue = "origin_fee") String column) {


        return orderService.getEveryDayStat( platform,shopId,beginDate,endDate,column);
    }


    /**
     * 总额统计
     * @param platform
     * @param shopId
     * @param beginDate
     * @param endDate
     * @param column
     * @return
     */
    @RequestMapping(value = "/total_stat", method = RequestMethod.GET)
    public List<ChartVo> getShopTotalStat(
            @RequestParam(value = "platform", required = false) Integer platform,
            @RequestParam(value = "shop_id", required = false) UUID shopId,
            @RequestParam(value = "begin_date", required = false) String beginDate,
            @RequestParam(value = "end_date", required = false) String endDate,
            @RequestParam(value = "column", required = false,defaultValue = "origin_fee") String column){
        return this.orderService.getShopTotalStat(platform,shopId,beginDate,endDate,column);
    }

}
