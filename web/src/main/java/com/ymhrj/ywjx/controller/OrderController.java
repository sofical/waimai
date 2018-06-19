package com.ymhrj.ywjx.controller;

import com.ymhrj.ywjx.controller.vo.ChartVo;
import com.ymhrj.ywjx.controller.vo.OrderDishVo;
import com.ymhrj.ywjx.controller.vo.OrderExcelVo;
import com.ymhrj.ywjx.controller.vo.OrderVo;
import com.ymhrj.ywjx.controller.vo.PageData;
import com.ymhrj.ywjx.service.base.OrderService;
import com.ymhrj.ywjx.utils.ExcelUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author : CGS
 * Date : 2018-03-25
 * Time : 15:16
 */
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public PageData<OrderVo> getList(
            @RequestParam(value = "platform", required = false) Integer platform,
            @RequestParam(value = "shop_id", required = false) UUID shopId,
            @RequestParam(value = "begin_date", required = false) String beginDate,
            @RequestParam(value = "end_date", required = false) String endDate,
            @RequestParam(value = "customer_phone", required = false) String customerPhone,
            @RequestParam(value = "customer_ame", required = false) String customerName,
            @RequestParam(value = "customer_address", required = false) String customerAddress,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit
    ){
        return this.orderService.getList(platform,shopId,beginDate,endDate,customerPhone,customerName,customerAddress,page,limit);
    }



    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void getList(
            @RequestParam(value = "platform", required = false) Integer platform,
            @RequestParam(value = "shop_id", required = false) UUID shopId,
            @RequestParam(value = "begin_date", required = false) String beginDate,
            @RequestParam(value = "end_date", required = false) String endDate,
            @RequestParam(value = "customer_phone", required = false) String customerPhone,
            @RequestParam(value = "customer_ame", required = false) String customerName,
            @RequestParam(value = "customer_address", required = false) String customerAddress,
            HttpServletResponse response
    ){
        PageData<OrderVo> page =  this.orderService.getList(platform,shopId,beginDate,endDate,customerPhone,customerName,customerAddress,1,10000);
        List<OrderVo> list = page.getData();
        List<OrderExcelVo> excelVoList = new ArrayList<>();
        for (OrderVo orderVo : list){
            OrderExcelVo excelVo = new OrderExcelVo();
            BeanUtils.copyProperties(orderVo,excelVo);
            String dishNames = "";
            if(orderVo.getDishes() != null){
                for (OrderDishVo dishVo : orderVo.getDishes()){
                    dishNames += dishVo.getDishName()+" ";
                }
            }
            excelVo.setDishNames(dishNames);
            excelVoList.add(excelVo);
        }
        ExcelUtils.exportExcel(excelVoList,"订单列表","订单",OrderExcelVo.class,"订单.xls",response);
    }

    @RequestMapping(value = "/backs", method = RequestMethod.GET)
    List<ChartVo> getBackOrderReason(  @RequestParam(value = "platform", required = false) Integer platform,
                                       @RequestParam(value = "shop_id", required = false) UUID shopId,
                                       @RequestParam(value = "begin_date", required = false) String beginDate,
                                       @RequestParam(value = "end_date", required = false) String endDate){
        return this.orderService.getBackOrderReason(platform,shopId,beginDate,endDate);
    }
}
