package com.ymhrj.ywjx.service.third.impl;

import com.alibaba.fastjson.JSONObject;
import com.ymhrj.ywjx.controller.third.vo.BaiduRequestVo;
import com.ymhrj.ywjx.controller.third.vo.BaiduSuccessBodyVo;
import com.ymhrj.ywjx.controller.third.vo.BaiduSuccessVo;
import com.ymhrj.ywjx.db.entity.BaiduOrder;
import com.ymhrj.ywjx.db.repository.BaiduOrderRespository;
import com.ymhrj.ywjx.service.third.BaiduService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by Administrator on 2017/12/15.
 */
@Component
public class BaiduServiceImpl implements BaiduService {
    @Autowired
    private BaiduOrderRespository baiduOrderRespository;
    @Override
    public Object pull(BaiduRequestVo baiduRequest) {
        if ("order.create".equals(baiduRequest.getCmd())) {
            return this.createOrder(baiduRequest);
        }
        return null;
    }

    private Object createOrder(BaiduRequestVo baiduRequest) {
        JSONObject orderInfo = baiduRequest.getBody();
        JSONObject shopInfo = orderInfo.getJSONObject("shop");
        String shopId = shopInfo.getString("id");
        JSONObject order = orderInfo.getJSONObject("order");
        String orderId = order.getString("order_id");
        BaiduOrder baiduOrder = this.baiduOrderRespository.findOneByShopIdAndOrderId(shopId, orderId);
        if (null == baiduOrder) {
            baiduOrder = new BaiduOrder();
            baiduOrder.setBaiduOrderId(UUID.randomUUID());
            baiduOrder.setShopId(shopId);
            baiduOrder.setOrderId(orderId);
        }
        baiduOrder.setOrderInfo(JSONObject.toJSONString(orderInfo));
        baiduOrder.setIsSync(0);
        this.baiduOrderRespository.save(baiduOrder);

        BaiduSuccessBodyVo body = new BaiduSuccessBodyVo();
        JSONObject data = body.getData();
        data.put("source_order_id", orderId);
        body.setData(data);
        return new BaiduSuccessVo("resp.order.create", baiduRequest, body);
    }
}
