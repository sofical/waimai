package com.ymhrj.ywjx.db.repository;

import com.alibaba.fastjson.JSONObject;
import com.ymhrj.ywjx.controller.vo.PageData;
import com.ymhrj.ywjx.db.entity.Orders;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017/12/13.
 */
@Repository
public interface OrdersRepository extends PagingAndSortingRepository<Orders, UUID> {

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
    PageData<JSONObject> getDayStat(Integer platform, UUID shopId, String beginDate, String endDate, String column, Integer page, Integer limit, Boolean asc);

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
    PageData<Orders> getList(Integer platform, UUID shopId, String beginDate, String endDate, String customerPhone, String customerName, String customerAddress, Integer page, Integer limit);

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
    List<JSONObject> getShopTotalStat(Integer platform, UUID shopId, String beginDate, String endDate, String column);

    /**
     * Stat by count list.
     *
     * @param groupBy     the group by
     * @param countColumn the count column
     * @return the list
     */
    List<JSONObject> statByCount(String groupBy,String countColumn);

    /**
     * Stat by sum list.
     *
     * @param groupBy   the group by
     * @param sumColumn the sum column
     * @return the list
     */
    List<JSONObject> statBySum(String groupBy,String sumColumn);

    /**
     * Find by platform and origin order id orders.
     *
     * @param platform      the platform
     * @param originOrderId the origin order id
     * @return the orders
     */
    Orders findByPlatformAndOriginOrderId(Integer platform,String originOrderId);

    /**
     * Gets day stat not shop.
     *
     * @param platform  the platform
     * @param shopId    the shop id
     * @param beginDate the begin date
     * @param endDate   the end date
     * @param column    the column
     * @param asc       the asc
     * @return the day stat not shop
     */
    List<JSONObject> getDayStatNotShop(Integer platform, UUID shopId, String beginDate, String endDate, String column,  Boolean asc);

    /**
     * 热门店铺
     *
     * @param platform  the platform
     * @param shopId    the shop id
     * @param beginDate the begin date
     * @param endDate   the end date
     * @return hot shops
     */
    List<JSONObject> getHotShops(Integer platform, UUID shopId, String beginDate, String endDate);

    /**
     * Gets hot hour.
     *
     * @param platform  the platform
     * @param shopId    the shop id
     * @param beginDate the begin date
     * @param endDate   the end date
     * @return the hot hour
     */
    List<JSONObject> getHotHour(Integer platform, UUID shopId, String beginDate, String endDate);

    /**
     * Stat customer num integer.
     *
     * @param platform  the platform
     * @param shopId    the shop id
     * @param beginDate the begin date
     * @param endDate   the end date
     * @param old       the old
     * @return the integer
     */
    Integer statCustomerNum(Integer platform, UUID shopId, String beginDate, String endDate,Boolean old);


    List<JSONObject> getCustomerPlatoform(UUID customerId);


    List<JSONObject> getCustomerHour(UUID customerId);
}
