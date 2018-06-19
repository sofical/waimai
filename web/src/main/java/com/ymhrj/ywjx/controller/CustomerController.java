package com.ymhrj.ywjx.controller;

import com.ymhrj.ywjx.controller.vo.ChartVo;
import com.ymhrj.ywjx.controller.vo.CustomerVo;
import com.ymhrj.ywjx.controller.vo.PageData;
import com.ymhrj.ywjx.service.base.CustomerService;
import com.ymhrj.ywjx.service.base.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author : CGS
 * Date : 2018-03-24
 * Time : 15:45
 */
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public PageData<CustomerVo> getCustomers(
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "begin_date", required = false) String beginDate,
            @RequestParam(value = "end_date", required = false) String endDate,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        return customerService.getList(phone,name,address,beginDate,endDate,page,limit);
    }

    @RequestMapping(value = "/actions/old_new_stat", method = RequestMethod.GET)
    public List<ChartVo> getOldNewSata(
            @RequestParam(value = "platform", required = false) Integer platform,
            @RequestParam(value = "shop_id", required = false) UUID shopId,
            @RequestParam(value = "begin_date", required = false) String beginDate,
            @RequestParam(value = "end_date", required = false) String endDate) {
        return this.orderService.statOldNewCustomer(platform,shopId,beginDate,endDate);
    }

    @RequestMapping(value = "/{customer_id}/actions/analysis", method = RequestMethod.GET)
    public Map<String,List<ChartVo>> getCustomerAnalysis(
            @PathVariable("customer_id") UUID customerId) {
        return this.orderService.getCustomerAnalysis(customerId);
    }

}
