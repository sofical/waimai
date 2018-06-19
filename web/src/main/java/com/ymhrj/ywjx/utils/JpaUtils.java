package com.ymhrj.ywjx.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ymhrj.ywjx.controller.vo.PageData;
import org.hibernate.transform.Transformers;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * jpa
 *
 * @author cgs
 * date 2018-03-25
 */
public class JpaUtils {

    /**
     * 根据Map设置参数
     *
     * @param query query
     * @param paramMap para
     */
    private static void setQuery(Query query, Map<String, Object> paramMap, Pageable pageable) {
        if (!CollectionUtils.isEmpty(paramMap)) {
            for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        if (pageable != null) {
            query.setFirstResult(pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
        }
    }

    /**
     * 执行Count语句
     *
     * @param entityManager manage
     * @param countHql
     * @param paramMap
     * @return
     */
    public static Long count(EntityManager entityManager, String countHql, Map<String, Object> paramMap, boolean isNative) {
        Query query = isNative ? entityManager.createNativeQuery(countHql) : entityManager.createQuery(countHql);
        setQuery(query, paramMap, null);
        return ((BigInteger) query.getSingleResult()).longValue();
    }

    /**
     * 执行Query语句查询List
     *
     * @param entityManager
     * @param queryHql
     * @param paramMap
     * @param pageable
     * @return list jsonobject(fastjson)
     */
    public static List<JSONObject> queryList(EntityManager entityManager, String queryHql, Map<String, Object> paramMap, Pageable pageable, boolean isNative) {
        Query query = isNative ? entityManager.createNativeQuery(queryHql) : entityManager.createQuery(queryHql);
        query.unwrap(org.hibernate.SQLQuery.class)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        setQuery(query, paramMap, pageable);
        List<JSONObject> list = new ArrayList<>();
        List<Map<String,Object>> mapList = query.getResultList();
        for (Map<String,Object> map : mapList){
            list.add(JSONObject.parseObject(JSON.toJSONString(map)));
        }
        return list;
    }

    public static List queryList(EntityManager entityManager, String queryHql, Map<String, Object> paramMap, Pageable pageable, boolean isNative, Class<?> resultClass) {
        Query query = isNative ? entityManager.createNativeQuery(queryHql, resultClass) : entityManager.createQuery(queryHql, resultClass);
        setQuery(query, paramMap, pageable);
        return query.getResultList();
    }

    /**
     * 分页查询
     *
     * @param entityManager
     * @param queryHql
     * @param countHql
     * @param paramMap
     * @param pageable
     * @return
             */
    public static PageData queryPage(EntityManager entityManager, String queryHql, String countHql, Map<String, Object> paramMap, Pageable pageable, boolean isNative) {
        List list = queryList(entityManager, queryHql, paramMap, pageable, isNative);
        long count = count(entityManager, countHql, paramMap, isNative);
        PageData pagerResult = new PageData();
        pagerResult.setCount((int)count);
        pagerResult.setData(list);
        return pagerResult;
    }

    public static PageData queryPage(EntityManager entityManager, String selectSql, String countSql, Map<String, Object> paramMap, Pageable pageable, boolean isNative, Class<?> resultClass) {
        List list = queryList(entityManager, selectSql, paramMap, pageable, isNative, resultClass);
        long count = count(entityManager, countSql, paramMap, isNative);
        PageData pagerResult = new PageData();
        pagerResult.setCount((int)count);
        pagerResult.setData(list);
        return pagerResult;
    }



}
