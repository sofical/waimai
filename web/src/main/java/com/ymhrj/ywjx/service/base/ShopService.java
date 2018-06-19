package com.ymhrj.ywjx.service.base;

import com.ymhrj.ywjx.controller.vo.PageData;
import com.ymhrj.ywjx.controller.vo.ShopCreateVo;
import com.ymhrj.ywjx.controller.vo.ShopVo;

import java.util.UUID;

/**
 * The interface Shop service.
 *
 * @author : 130801(cgs) Date : 2018-02-26 Time : 13:54
 */
public interface ShopService {
    /**
     * Create shop vo.
     *
     * @param shopCreateVo the shop create vo
     * @return the shop vo
     */
    ShopVo create(ShopCreateVo shopCreateVo);

    /**
     * Update shop vo.
     *
     * @param shopId       the shop id
     * @param shopCreateVo the shop create vo
     * @return the shop vo
     */
    ShopVo update(UUID shopId,ShopCreateVo shopCreateVo);

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
    PageData<ShopVo> getList(String code,String name,Integer platform,Integer page,Integer limit);


    /**
     * Delete.
     *
     * @param shopId the shop id
     */
    void delete(UUID shopId);

    /**
     * Gets shop name.
     *
     * @param shopId the shop id
     * @return the shop name
     */
    String getShopName (UUID shopId);
}
