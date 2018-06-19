package com.ymhrj.ywjx.db.repository.impl;

import com.ymhrj.ywjx.controller.vo.PageData;
import com.ymhrj.ywjx.db.entity.Dish;
import com.ymhrj.ywjx.utils.JpaUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author : CGS
 * Date : 2018-02-27
 * Time : 18:35
 */
public class DishRepositoryImpl {
    @PersistenceContext
    private EntityManager entityManager;
    public PageData<Dish> getList(UUID shopId, String code, String name, Integer page, Integer limit){
        Map<String, Object> params = new HashMap<>();
        StringBuffer whereBuffer = new StringBuffer(" WHERE  shop_id=:shop_id ");
        params.put("shop_id",shopId.toString());
        if(!StringUtils.isEmpty(code)){
            whereBuffer.append(" AND code LIKE :code");
            params.put("code", "%"+code+"%");
        }
        if(!StringUtils.isEmpty(name)){
            whereBuffer.append(" AND name LIKE :name");
            params.put("name", "%"+name+"%");
        }

        Pageable pageable=new PageRequest(page-1,limit);
        String selectSql = " SELECT *  FROM dish "+whereBuffer.toString()+" order by create_time desc";
        String countSql = "SELECT count(1)  FROM dish   " + whereBuffer.toString();
        return JpaUtils.queryPage(entityManager, selectSql, countSql, params, pageable, true, Dish.class);
    }
}
