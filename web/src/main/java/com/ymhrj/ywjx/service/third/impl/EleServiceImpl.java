package com.ymhrj.ywjx.service.third.impl;

import com.alibaba.fastjson.JSONObject;
import com.ymhrj.ywjx.constant.EleConstant;
import com.ymhrj.ywjx.controller.third.vo.EleRequestVo;
import com.ymhrj.ywjx.db.entity.EleOrder;
import com.ymhrj.ywjx.db.entity.EleShop;
import com.ymhrj.ywjx.db.repository.EleOrderRespository;
import com.ymhrj.ywjx.db.repository.EleShopRespository;
import com.ymhrj.ywjx.service.third.EleService;
import com.ymhrj.ywjx.utils.TimeUtil;
import eleme.openapi.sdk.api.entity.ugc.OpenapiOrderRate;
import eleme.openapi.sdk.api.exception.ServiceException;
import eleme.openapi.sdk.api.service.UgcService;
import eleme.openapi.sdk.config.Config;
import eleme.openapi.sdk.oauth.OAuthClient;
import eleme.openapi.sdk.oauth.response.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by zj on 2017/12/14.
 */
@Component
public class EleServiceImpl implements EleService {
    @Autowired
    private EleOrderRespository eleOrderRespository;
    @Autowired
    private EleShopRespository eleShopRespository;
    @Override
    public void pull(EleRequestVo eleRequest) {
        if (!EleConstant.appId.equals(eleRequest.getAppId())) {
            throw new RuntimeException("非本系统授权商户");
        }
        //下单
        if (EleConstant.orderAdd.equals(eleRequest.getType())) {
            JSONObject orderInfo = JSONObject.parseObject(eleRequest.getMessage());
            this.newOrder(orderInfo);
        }
    }

    @Override
    public String bindUrl(String state) {
        Config config = new Config(EleConstant.isSandbox, EleConstant.appKey, EleConstant.appSecret);
        OAuthClient client = new OAuthClient(config);
        return client.getAuthUrl(EleConstant.bindCallBackUrl, EleConstant.scope, state);
    }

    @Override
    public void bind(String code, String state) {
        Config config = new Config(EleConstant.isSandbox, EleConstant.appKey, EleConstant.appSecret);
        OAuthClient client = new OAuthClient(config);
        Token token = client.getTokenByCode(code, EleConstant.bindCallBackUrl);
        if (null == token.getAccessToken()) {
            throw new RuntimeException("验证码已过期");
        }
        EleShop eleShop = eleShopRespository.findOneByShopCode(state);
        if(eleShop == null) {
            eleShop = new EleShop();
            eleShop.setEleShopId(UUID.randomUUID());
            eleShop.setShopCode(state);
        }
        eleShop.setAccessToken(token.getAccessToken());
        eleShop.setExpiresIn(token.getExpires());
        eleShop.setRefreshToken(token.getRefreshToken());
        eleShop.setTokenType(token.getTokenType());
        eleShopRespository.save(eleShop);
    }

    /**
     * 评价结构：https://open.shop.ele.me/openapi/apilist/eleme-ugc/eleme-ugc-getOrderRatesByShopId
     */
    @Override
    public void syncComment() {
        Config config = new Config(EleConstant.isSandbox, EleConstant.appKey, EleConstant.appSecret);
        OAuthClient client = new OAuthClient(config);
        Token token = client.getTokenInClientCredentials();
        List<EleShop> eleShops = this.eleShopRespository.findAll();
        for (EleShop shop :
             eleShops) {
            Date startTime = new Date(TimeUtil.startOfToday());
            Date endTime = new Date(System.currentTimeMillis());
            UgcService ugcService = new UgcService(config, token);
            try {
                List<OpenapiOrderRate> response = ugcService.getOrderRatesByShopId(shop.getShopCode(), startTime, endTime, 0, 20);
                if (!response.isEmpty()) {
                    for (OpenapiOrderRate rate:
                         response) {
                        String commentId = rate.getId();

                    }
                }
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
    }

    private void newOrder(JSONObject orderInfo) {
        String orderId = orderInfo.getString("orderId");
        Integer shopId = orderInfo.getInteger("shopId");
        EleOrder eleOrder = this.eleOrderRespository.findOneByShopIdAndOrderId(shopId, orderId);
        if (null == eleOrder) {
            eleOrder = new EleOrder();
            eleOrder.setEleOrderId(UUID.randomUUID());
            eleOrder.setOrderId(orderId);
            eleOrder.setShopId(shopId);
        }
        eleOrder.setOrderInfo(JSONObject.toJSONString(orderInfo));
        eleOrder.setIsSync(0);
        this.eleOrderRespository.save(eleOrder);
    }
}
