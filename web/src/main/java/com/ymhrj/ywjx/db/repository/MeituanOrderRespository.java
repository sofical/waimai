package com.ymhrj.ywjx.db.repository;

import com.ymhrj.ywjx.db.entity.MeituanOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017/12/13.
 */
public interface MeituanOrderRespository extends JpaRepository<MeituanOrder, UUID> {
    MeituanOrder findOneByEPoiIdAndOrderId(String ePoiId, String orderId);
    @Query(value = "select  * from meituan_order where re_get=0 and create_time < DATE_SUB(NOW(),INTERVAL -1 DAY)", nativeQuery = true)
    List<MeituanOrder> findAllUnSync();

    List<MeituanOrder> findByIsSync(Integer isSync);
}
