package com.ymhrj.ywjx.db.repository;

import com.ymhrj.ywjx.db.entity.EleShop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017/12/18.
 */
public interface EleShopRespository extends JpaRepository<EleShop, UUID> {
    EleShop findOneByShopCode(String shopCode);
}
