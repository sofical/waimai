package com.ymhrj.ywjx.service.third;

import com.ymhrj.ywjx.controller.third.vo.MeituanBindVo;

/**
 * Created by SONY on 2017/12/12.
 */
public interface MeituanService {
    /**
     * bind shop.
     * @param bindVo
     */
    void bind(MeituanBindVo bindVo);

    /**
     * unbind shop.
     * @param businessId
     * @param ePoiId
     */
    void unbind(String businessId, String ePoiId);

    /**
     * new order.
     * @param ePoiId
     * @param order
     */
    void addOrder(String ePoiId, String order);

    /**
     * sync comments.
     */
    void syncComments();
}
