package com.ymhrj.ywjx.db.repository.impl;

import com.alibaba.fastjson.JSONObject;
import com.ymhrj.ywjx.controller.vo.PageData;
import com.ymhrj.ywjx.db.entity.Comments;
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
public class CommentsRepositoryImpl {
    @PersistenceContext
    private EntityManager entityManager;


    public PageData<Orders> getList(Integer platform, UUID shopId, String beginDate, String endDate,  Integer page, Integer limit){
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

        Pageable pageable = new PageRequest(page - 1, limit);
        String selectSql = " SELECT * FROM comments " + whereBuffer.toString()+" ORDER BY comment_time  DESC ";
        String countSql = "SELECT count(1)  FROM comments    " + whereBuffer.toString() ;
        return JpaUtils.queryPage(entityManager, selectSql, countSql, params, pageable, true,Comments.class);
    }

    public List<JSONObject> getCommentAlias(Integer platform, UUID shopId, String beginDate, String endDate, String column){
        Map<String, Object> params = new HashMap<>();
        StringBuffer whereBuffer = new StringBuffer("SELECT "+column+" score,count(comment_id) as comment_num from  comments WHERE  1 ");
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
        whereBuffer.append(" GROUP BY "+column+"   ");

        return JpaUtils.queryList(entityManager,whereBuffer.toString(),params,null,true);
    }

}
