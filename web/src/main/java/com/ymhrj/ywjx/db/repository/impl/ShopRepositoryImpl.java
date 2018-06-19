package com.ymhrj.ywjx.db.repository.impl;

import com.ymhrj.ywjx.controller.vo.PageData;
import com.ymhrj.ywjx.db.entity.Shop;
import com.ymhrj.ywjx.utils.JpaUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : CGS
 * Date : 2018-02-27
 * Time : 18:35
 */
public class ShopRepositoryImpl   {
    @PersistenceContext
    private EntityManager entityManager;
    public PageData<Shop> getList(String code, String name, Integer platform, Integer page, Integer limit){
        Map<String, Object> params = new HashMap<>();
        StringBuffer whereBuffer = new StringBuffer(" WHERE  1 ");
        if(!StringUtils.isEmpty(code)){
            whereBuffer.append(" AND code LIKE :code");
            params.put("code", "%"+code+"%");
        }
        if(!StringUtils.isEmpty(name)){
            whereBuffer.append(" AND name LIKE :name");
            params.put("name", "%"+name+"%");
        }
        if(platform != null){
            whereBuffer.append(" AND platform = :platform");
            params.put("platform", platform);
        }
        Pageable pageable=new PageRequest(page-1,limit);
        String selectSql = " SELECT *  FROM shop "+whereBuffer.toString()+" order by create_time desc";
        String countSql = "SELECT count(1)  FROM shop   " + whereBuffer.toString();
        return JpaUtils.queryPage(entityManager, selectSql, countSql, params, pageable, true, Shop.class);
    }
}
