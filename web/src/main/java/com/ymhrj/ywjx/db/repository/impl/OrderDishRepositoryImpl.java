package com.ymhrj.ywjx.db.repository.impl;

import com.alibaba.fastjson.JSONObject;
import com.ymhrj.ywjx.utils.JpaUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author : CGS
 * Date : 2018-04-01
 * Time : 15:35
 */
public class OrderDishRepositoryImpl {
    @PersistenceContext
    private EntityManager entityManager;

    public List<JSONObject> getHotDishes(Integer platform, UUID shopId, String beginDate, String endDate){
        Map<String, Object> params = new HashMap<>();

        StringBuffer whereBuffer = new StringBuffer("SELECT od.dish_id as dish_id,SUM(num) as num from order_dish od , orders o where od.order_id=o.order_id  ");
        if (platform != null) {
            whereBuffer.append(" AND o.platform = :platform ");
            params.put("platform", platform);
        }
        if (!StringUtils.isEmpty(shopId)) {
            whereBuffer.append(" AND o.shop_id = :shop_id ");
            params.put("shop_id", shopId.toString());
        }
        if (!StringUtils.isEmpty(beginDate)) {
            whereBuffer.append(" AND date(o.order_time) >= :begin_date ");
            params.put("begin_date", beginDate);
        }
        if (!StringUtils.isEmpty(endDate)) {
            whereBuffer.append(" AND date(o.order_time) <= :end_date ");
            params.put("end_date", endDate);
        }

        whereBuffer.append(" GROUP BY dish_id ORDER BY num DESC ");
        return JpaUtils.queryList(entityManager,whereBuffer.toString(),params,null,true);

    }


    public List<JSONObject> statByDishDimension(Integer platform, UUID shopId, String beginDate, String endDate,String column){
        Map<String, Object> params = new HashMap<>();

        StringBuffer whereBuffer = new StringBuffer("SELECT d."+column+" AS dimension, SUM(num) as num FROM order_dish od , orders o,dish d WHERE od.order_id=o.order_id AND od.dish_id=d.dish_id ");
        if (platform != null) {
            whereBuffer.append(" AND o.platform = :platform ");
            params.put("platform", platform);
        }
        if (!StringUtils.isEmpty(shopId)) {
            whereBuffer.append(" AND o.shop_id = :shop_id ");
            params.put("shop_id", shopId.toString());
        }
        if (!StringUtils.isEmpty(beginDate)) {
            whereBuffer.append(" AND date(o.order_time) >= :begin_date ");
            params.put("begin_date", beginDate);
        }
        if (!StringUtils.isEmpty(endDate)) {
            whereBuffer.append(" AND date(o.order_time) <= :end_date ");
            params.put("end_date", endDate);
        }

        whereBuffer.append(" GROUP BY d."+column+" ORDER BY num DESC ");
        return JpaUtils.queryList(entityManager,whereBuffer.toString(),params,null,true);

    }


    /**
     *
     * @param customerId
     * @return
     */
    public List<JSONObject> getCustomerDish(UUID customerId){
        String sql = " SELECT d.name as name ,sum(num) AS number FROM order_dish od,orders o,dish d WHERE od.order_id=o.order_id AND od.dish_id=d.dish_id  AND o.customer_id=:customer_id GROUP  BY od.dish_id ORDER BY number desc limit 0,10";
        Map<String, Object> params = new HashMap<>();
        params.put("customer_id",customerId.toString());
        return JpaUtils.queryList(entityManager,sql,params,null,true);
    }
}
