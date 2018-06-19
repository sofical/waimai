package com.ymhrj.ywjx.db.repository;

import com.ymhrj.ywjx.db.entity.BaiduOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017/12/13.
 */
public interface BaiduOrderRespository extends JpaRepository<BaiduOrder, UUID> {
    BaiduOrder findOneByShopIdAndOrderId(String shopId, String orderId);
    @Query(value = "select  * from ele_order where is_sync=0 and create_time < DATE_SUB(NOW(),INTERVAL -1 DAY)", nativeQuery = true)
    List<BaiduOrder> findAllUnSync();
    List<BaiduOrder> findByIsSync(Integer isSync);
}
