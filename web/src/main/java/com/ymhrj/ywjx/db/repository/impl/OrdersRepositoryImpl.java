package com.ymhrj.ywjx.db.repository.impl;

import com.alibaba.fastjson.JSONObject;
import com.ymhrj.ywjx.controller.vo.PageData;
import com.ymhrj.ywjx.db.entity.Orders;
import com.ymhrj.ywjx.utils.JpaUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author : CGS
 * Date : 2018-03-17
 * Time : 15:00
 */
public class OrdersRepositoryImpl {
    @PersistenceContext
    private EntityManager entityManager;
    public PageData<JSONObject> getDayStat(Integer platform, UUID shopId, String beginDate, String endDate, String column, Integer page, Integer limit, Boolean asc) {
        Map<String, Object> params = new HashMap<>();
        StringBuffer whereBuffer = new StringBuffer("SELECT shop_id, platform ,date_format(order_time, '%Y-%m-%d') as order_date ,sum("+column+") as fee from  orders WHERE  1 ");
        if (platform != null) {
            whereBuffer.append(" AND platform = :platform ");
            params.put("platform", platform);
        }
        if (!StringUtils.isEmpty(shopId)) {
            whereBuffer.append(" AND shop_id = :shop_id ");
            params.put("shop_id", shopId.toString());
        }
        if (!StringUtils.isEmpty(beginDate)) {
            whereBuffer.append(" AND date(order_time) >= :begin_date ");
            params.put("begin_date", beginDate);
        }
        if (!StringUtils.isEmpty(endDate)) {
            whereBuffer.append(" AND date(order_time) <= :end_date ");
            params.put("end_date", endDate);
        }
        whereBuffer.append("GROUP BY order_date order by order_date  ");
        if(asc){
            whereBuffer.append("ASC");
        }else {
            whereBuffer.append("DESC");
        }

        Pageable pageable = new PageRequest(page - 1, limit);
        String selectSql = whereBuffer.toString();
        String countSql = "SELECT count(1)  FROM (   " + whereBuffer.toString() + ") as cc";
        return JpaUtils.queryPage(entityManager, selectSql, countSql, params, pageable, true);
    }

    public List<JSONObject> getDayStatNotShop(Integer platform, UUID shopId, String beginDate, String endDate, String column,  Boolean asc) {
        Map<String, Object> params = new HashMap<>();
        StringBuffer whereBuffer = new StringBuffer("SELECT  date_format(order_time, '%Y-%m-%d') as order_date ,sum("+column+") as fee from  orders WHERE  1 ");
        if (platform != null) {
            whereBuffer.append(" AND platform = :platform ");
            params.put("platform", platform);
        }
        if (!StringUtils.isEmpty(shopId)) {
            whereBuffer.append(" AND shop_id = :shop_id ");
            params.put("shop_id", shopId.toString());
        }
        if (!StringUtils.isEmpty(beginDate)) {
            whereBuffer.append(" AND date(order_time) >= :begin_date ");
            params.put("begin_date", beginDate);
        }
        if (!StringUtils.isEmpty(endDate)) {
            whereBuffer.append(" AND date(order_time) <= :end_date ");
            params.put("end_date", endDate);
        }
        whereBuffer.append("GROUP BY order_date order by order_date  ");
        if(asc){
            whereBuffer.append("ASC");
        }else {
            whereBuffer.append("DESC");
        }
        return JpaUtils.queryList(entityManager,whereBuffer.toString(),params,null,true);
    }


    public PageData<Orders> getList(Integer platform, UUID shopId, String beginDate, String endDate, String customerPhone, String customerName, String customerAddress, Integer page, Integer limit){
        Map<String, Object> params = new HashMap<>();
        StringBuffer whereBuffer = new StringBuffer(" WHERE  1 ");
        if (platform != null) {
            whereBuffer.append(" AND platform = :platform ");
            params.put("platform", platform);
        }
        if (!StringUtils.isEmpty(shopId)) {
            whereBuffer.append(" AND shop_id = :shop_id ");
            params.put("shop_id", shopId.toString());
        }
        if (!StringUtils.isEmpty(beginDate)) {
            whereBuffer.append(" AND date(order_time) >= :begin_date ");
            params.put("begin_date", beginDate);
        }
        if (!StringUtils.isEmpty(endDate)) {
            whereBuffer.append(" AND date(order_time) <= :end_date ");
            params.put("end_date", endDate);
        }
        if (!StringUtils.isEmpty(customerPhone)) {
            whereBuffer.append(" AND customer_phone = :customer_phone ");
            params.put("customer_phone", customerPhone);
        }
        if (!StringUtils.isEmpty(customerName)) {
            whereBuffer.append(" AND customer_name = :customer_name ");
            params.put("customer_name", customerName);
        }
        if (!StringUtils.isEmpty(customerAddress)) {
            whereBuffer.append(" AND customer_address = :customer_address ");
            params.put("customer_address", customerAddress);
        }

        Pageable pageable = new PageRequest(page - 1, limit);
        String selectSql = " SELECT * FROM orders " + whereBuffer.toString()+" ORDER BY order_time  DESC ";
        String countSql = "SELECT count(1)  FROM orders    " + whereBuffer.toString() ;
        return JpaUtils.queryPage(entityManager, selectSql, countSql, params, pageable, true,Orders.class);
    }


    public List<JSONObject> statByCount(String groupBy, String countColumn){
        StringBuffer sql = new StringBuffer(" SELECT "+groupBy +",count(DISTINCT "+countColumn+") AS num FROM orders GROUP BY " +groupBy );
        return JpaUtils.queryList(entityManager,sql.toString(),null,null,true);
    }

    public List<JSONObject> statBySum(String groupBy,String sumColumn){
        StringBuffer sql = new StringBuffer(" SELECT "+groupBy +",sum("+sumColumn+") AS num FROM orders GROUP BY " +groupBy );
        return JpaUtils.queryList(entityManager,sql.toString(),null,null,true);
    }


    public List<JSONObject> getShopTotalStat(Integer platform, UUID shopId, String beginDate, String endDate, String column){
        Map<String, Object> params = new HashMap<>();
        StringBuffer whereBuffer = new StringBuffer("SELECT platform,shop_id ,sum("+column+")as fee from  orders WHERE  1 ");
        if (platform != null) {
            whereBuffer.append(" AND platform = :platform ");
            params.put("platform", platform);
        }
        if (!StringUtils.isEmpty(shopId)) {
            whereBuffer.append(" AND shop_id = :shop_id ");
            params.put("shop_id", shopId.toString());
        }
        if (!StringUtils.isEmpty(beginDate)) {
            whereBuffer.append(" AND date(order_time) >= :begin_date ");
            params.put("begin_date", beginDate);
        }
        if (!StringUtils.isEmpty(endDate)) {
            whereBuffer.append(" AND date(order_time) <= :end_date ");
            params.put("end_date", endDate);
        }
        whereBuffer.append(" GROUP BY platform,shop_id ORDER BY platform ASC  ");

        return JpaUtils.queryList(entityManager,whereBuffer.toString(),params,null,true);
    }


    public     List<JSONObject> getHotShops(Integer platform, UUID shopId, String beginDate, String endDate){
        Map<String, Object> params = new HashMap<>();

        StringBuffer whereBuffer = new StringBuffer("SELECT shop_id,count(order_id) as num from orders  where 1 ");
        if (platform != null) {
            whereBuffer.append(" AND platform = :platform ");
            params.put("platform", platform);
        }
        if (!StringUtils.isEmpty(shopId)) {
            whereBuffer.append(" AND shop_id = :shop_id ");
            params.put("shop_id", shopId.toString());
        }
        if (!StringUtils.isEmpty(beginDate)) {
            whereBuffer.append(" AND date(order_time) >= :begin_date ");
            params.put("begin_date", beginDate);
        }
        if (!StringUtils.isEmpty(endDate)) {
            whereBuffer.append(" AND date(order_time) <= :end_date ");
            params.put("end_date", endDate);
        }

        whereBuffer.append(" GROUP BY shop_id ORDER BY num DESC ");
        return JpaUtils.queryList(entityManager,whereBuffer.toString(),params,null,true);
    }

    public     List<JSONObject> getHotHour(Integer platform, UUID shopId, String beginDate, String endDate){
        Map<String, Object> params = new HashMap<>();

        StringBuffer whereBuffer = new StringBuffer("SELECT date_format(order_time,\"%H\") AS hot_hour,count(order_id) as num FROM orders  where 1 ");
        if (platform != null) {
            whereBuffer.append(" AND platform = :platform ");
            params.put("platform", platform);
        }
        if (!StringUtils.isEmpty(shopId)) {
            whereBuffer.append(" AND shop_id = :shop_id ");
            params.put("shop_id", shopId.toString());
        }
        if (!StringUtils.isEmpty(beginDate)) {
            whereBuffer.append(" AND date(order_time) >= :begin_date ");
            params.put("begin_date", beginDate);
        }
        if (!StringUtils.isEmpty(endDate)) {
            whereBuffer.append(" AND date(order_time) <= :end_date ");
            params.put("end_date", endDate);
        }

        whereBuffer.append(" GROUP BY hot_hour  ");
        return JpaUtils.queryList(entityManager,whereBuffer.toString(),params,null,true);
    }


    public   Integer statCustomerNum(Integer platform, UUID shopId, String beginDate, String endDate,Boolean old){
        Map<String, Object> params = new HashMap<>();

        StringBuffer whereBuffer = new StringBuffer("SELECT count(customer_id) as num from (SELECT customer_id,count(DISTINCT customer_id) as num FROM  orders  where 1 ");
        if (platform != null) {
            whereBuffer.append(" AND platform = :platform ");
            params.put("platform", platform);
        }
        if (!StringUtils.isEmpty(shopId)) {
            whereBuffer.append(" AND shop_id = :shop_id ");
            params.put("shop_id", shopId.toString());
        }
        if (!StringUtils.isEmpty(beginDate)) {
            whereBuffer.append(" AND date(order_time) >= :begin_date ");
            params.put("begin_date", beginDate);
        }
        if (!StringUtils.isEmpty(endDate)) {
            whereBuffer.append(" AND date(order_time) <= :end_date ");
            params.put("end_date", endDate);
        }

        whereBuffer.append(" group by customer_id) as tmp where  ");
        if(old) {
            whereBuffer.append(" num >1");
        }else {
            whereBuffer.append(" num <=1");

        }
        List<JSONObject> result = JpaUtils.queryList(entityManager,whereBuffer.toString(),params,null,true);
        return result.get(0).getInteger("num");
    }


    /**
     *
     * @param customerId
     * @return
     */
    public List<JSONObject> getCustomerPlatoform(UUID customerId){
       String sql = " SELECT platform ,count(order_id) AS num FROM orders WHERE customer_id=:customer_id GROUP  BY platform";
        Map<String, Object> params = new HashMap<>();
        params.put("customer_id",customerId.toString());
        return JpaUtils.queryList(entityManager,sql,params,null,true);
    }


    /**
     *
     * @param customerId
     * @return
     */
    public List<JSONObject> getCustomerHour(UUID customerId){
        String sql = " SELECT date_format(order_time,\"%H\")  hour_num ,count(order_id) AS num FROM orders WHERE customer_id=:customer_id GROUP  BY hour_num ORDER BY num DESC LIMIT 0,10 ";
        Map<String, Object> params = new HashMap<>();
        params.put("customer_id",customerId.toString());
        return JpaUtils.queryList(entityManager,sql,params,null,true);
    }

}
