package com.ymhrj.ywjx.service.base;

import com.ymhrj.ywjx.controller.vo.BrokenLineVo;
import com.ymhrj.ywjx.controller.vo.ChartVo;
import com.ymhrj.ywjx.controller.vo.DayOrderVo;
import com.ymhrj.ywjx.controller.vo.OrderVo;
import com.ymhrj.ywjx.controller.vo.PageData;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * The interface Order service.
 *
 * @author : CGS Date : 2018-03-17 Time : 15:10
 */
public interface OrderService {
    /**
     * Gets day stat.
     *
     * @param platform  the platform
     * @param shopId    the shop id
     * @param beginDate the begin date
     * @param endDate   the end date
     * @param column    the column
     * @param page      the page
     * @param limit     the limit
     * @param asc       the asc
     * @return the day stat
     */
    PageData<DayOrderVo>  getDayStat(Integer platform, UUID shopId, String beginDate, String endDate, String column, Integer page, Integer limit, Boolean asc);

    /**
     * Gets every day stat.
     *
     * @param platform  the platform
     * @param shopId    the shop id
     * @param beginDate the begin date
     * @param endDate   the end date
     * @param column    the column
     * @return the every day stat
     */
    BrokenLineVo getEveryDayStat(Integer platform, UUID shopId, String beginDate, String endDate, String column);


    /**
     * Gets list.
     *
     * @param platform        the platform
     * @param shopId          the shop id
     * @param beginDate       the begin date
     * @param endDate         the end date
     * @param customerPhone   the customer phone
     * @param customerName    the customer name
     * @param customerAddress the customer address
     * @param page            the page
     * @param limit           the limit
     * @return the list
     */
    PageData<OrderVo>  getList(Integer platform, UUID shopId, String beginDate, String endDate, String customerPhone,String customerName,String customerAddress, Integer page, Integer limit);

    List<ChartVo> getBackOrderReason(Integer platform, UUID shopId, String beginDate, String endDate);
    /**
     * Gets shop total stat.
     *
     * @param platform  the platform
     * @param shopId    the shop id
     * @param beginDate the begin date
     * @param endDate   the end date
     * @param column    the column
     * @return the shop total stat
     */
    List<ChartVo> getShopTotalStat(Integer platform, UUID shopId, String beginDate, String endDate, String column);

    /**
     * Compare list.
     *
     * @param type      the type
     * @param dimension the dimension
     * @return the list
     */
    List<ChartVo> compare(String type,String dimension);


    /**
     * Gets hot shops.
     *
     * @param platform  the platform
     * @param shopId    the shop id
     * @param beginDate the begin date
     * @param endDate   the end date
     * @return the hot shops
     */
    List<ChartVo> getHotShops(Integer platform, UUID shopId, String beginDate, String endDate);

    /**
     * Gets hot hour.
     *
     * @param platform  the platform
     * @param shopId    the shop id
     * @param beginDate the begin date
     * @param endDate   the end date
     * @return the hot hour
     */
    List<ChartVo> getHotHour(Integer platform, UUID shopId, String beginDate, String endDate);


    /**
     * Stat old new customer list.
     *
     * @param platform  the platform
     * @param shopId    the shop id
     * @param beginDate the begin date
     * @param endDate   the end date
     * @return the list
     */
    List<ChartVo> statOldNewCustomer(Integer platform, UUID shopId, String beginDate, String endDate);


    Map<String,List<ChartVo>> getCustomerAnalysis(UUID customerId);
}
