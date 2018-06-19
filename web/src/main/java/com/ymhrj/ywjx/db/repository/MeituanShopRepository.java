package com.ymhrj.ywjx.db.repository;

import com.ymhrj.ywjx.db.entity.MeituanShop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created by SONY on 2017/12/12.
 */
public interface MeituanShopRepository extends JpaRepository<MeituanShop, UUID> {
    MeituanShop findOneByEPoiIdAndBusinessId(String ePoiId, String businessId);
}
