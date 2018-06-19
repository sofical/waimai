package com.ymhrj.ywjx.controller.vo;

import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author : 130801(cgs)
 * Date : 2018-02-26
 * Time : 11:54
 */
@Data
public class OrderExcelVo {

    @Excel(name = "平台" ,replace = {"饿了么_1", "百度_2","美团_3"},orderNum = "0")
    private Integer platform;
    @Excel(name = "店铺名称" ,orderNum = "1")
    private String shopName;
    @Excel(name = "配送电话" ,orderNum = "2")
    private String customerPhone;
    @Excel(name = "客户名称",orderNum = "3")
    private String customerName;
    @Excel(name = "配送地址",orderNum = "4")
    private String customerAddress;
    @Excel(name = "菜品",  orderNum = "5")
    private String dishNames;
    @Excel(name = "原价",orderNum = "6")
    private BigDecimal originFee;
    @Excel(name = "优惠",orderNum = "7")
    private BigDecimal bonusFee;
    @Excel(name = "真实价格",orderNum = "8")
    private BigDecimal realFee;
    @Excel(name = "原平台订单id",orderNum = "9")
    private String originOrderId;
    @Excel(name = "订单时间", exportFormat = "yyyy-MM-dd HH:mm:SS", orderNum = "10")
    private Date orderTime;

}
