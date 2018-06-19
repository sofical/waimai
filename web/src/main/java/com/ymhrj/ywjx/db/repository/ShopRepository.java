package com.ymhrj.ywjx.db.repository;

import com.ymhrj.ywjx.controller.vo.PageData;
import com.ymhrj.ywjx.db.entity.Shop;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * The interface Shop repository.
 *
 * @author CGS
 */
@Repository
public interface ShopRepository extends PagingAndSortingRepository<Shop, UUID> {

    /**
     * Gets list.
     *
     * @param code     the code
     * @param name     the name
     * @param platform the platform
     * @param page     the page
     * @param limit    the limit
     * @return the list
     */
    PageData<Shop> getList(String code, String name, Integer platform, Integer page, Integer limit);


    Shop findByPlatformAndCode(Integer platform ,String code);


    Shop findByExtraLike(String extra);
}
