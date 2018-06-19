package com.ymhrj.ywjx.db.repository.impl;

import com.ymhrj.ywjx.controller.vo.PageData;
import com.ymhrj.ywjx.db.entity.Customer;
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
public class CustomerRepositoryImpl {
    @PersistenceContext
    private EntityManager entityManager;
    public  PageData<Customer> getList(String phone, String name, String address,String  beginDate,String endDate, Integer page, Integer limit){
        Map<String, Object> params = new HashMap<>();
        StringBuffer whereBuffer = new StringBuffer(" WHERE  1 ");
        if(!StringUtils.isEmpty(phone)){
            whereBuffer.append(" AND phone LIKE :phone");
            params.put("phone", "%"+phone+"%");
        }
        if(!StringUtils.isEmpty(name)){
            whereBuffer.append(" AND names LIKE :names");
            params.put("names", "%"+name+"%");
        }
        if(!StringUtils.isEmpty(address)){
            whereBuffer.append(" AND address_list LIKE :address");
            params.put("address", "%"+address+"%");
        }
        if (!StringUtils.isEmpty(beginDate)) {
            whereBuffer.append(" AND date(create_time) >= :begin_date ");
            params.put("begin_date", beginDate);
        }
        if (!StringUtils.isEmpty(endDate)) {
            whereBuffer.append(" AND date(create_time) <= :end_date ");
            params.put("end_date", endDate);
        }
        Pageable pageable=new PageRequest(page-1,limit);
        String selectSql = " SELECT *  FROM customer "+whereBuffer.toString()+" order by create_time desc";
        String countSql = "SELECT count(1)  FROM customer   " + whereBuffer.toString();
        return JpaUtils.queryPage(entityManager, selectSql, countSql, params, pageable, true, Customer.class);
    }
}
