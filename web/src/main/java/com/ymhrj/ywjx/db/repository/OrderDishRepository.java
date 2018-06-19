package com.ymhrj.ywjx.db.repository;

import com.alibaba.fastjson.JSONObject;
import com.ymhrj.ywjx.db.entity.OrderDish;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017/12/13.
 */
@Repository
public interface OrderDishRepository extends PagingAndSortingRepository<OrderDish, UUID> {
    /**
     * Find by order id list.
     *
     * @param orderId the order id
     * @return the list
     */
    List<OrderDish> findByOrderId(UUID orderId);


    /**
     * 热门菜品
     *
     * @param platform  the platform
     * @param shopId    the shop id
     * @param beginDate the begin date
     * @param endDate   the end date
     * @return hot dishes
     */
    List<JSONObject> getHotDishes(Integer platform, UUID shopId, String beginDate, String endDate);

    /**
     * Stat by dish dimension list.
     *
     * @param platform  the platform
     * @param shopId    the shop id
     * @param beginDate the begin date
     * @param endDate   the end date
     * @param column    the column
     * @return the list
     */
    List<JSONObject> statByDishDimension(Integer platform, UUID shopId, String beginDate, String endDate,String column);

    List<JSONObject> getCustomerDish(UUID customerId);
}
