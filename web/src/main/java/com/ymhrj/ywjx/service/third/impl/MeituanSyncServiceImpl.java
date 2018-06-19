package com.ymhrj.ywjx.service.third.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ymhrj.ywjx.constant.MeituanConstant;
import com.ymhrj.ywjx.db.entity.MeituanOrder;
import com.ymhrj.ywjx.db.entity.MeituanShop;
import com.ymhrj.ywjx.db.repository.MeituanOrderRespository;
import com.ymhrj.ywjx.db.repository.MeituanShopRepository;
import com.ymhrj.ywjx.service.third.MeituanSyncService;
import com.ymhrj.ywjx.utils.MeituanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/12/13.
 */
@Component
public class MeituanSyncServiceImpl implements MeituanSyncService {
    @Autowired
    private MeituanShopRepository meituanShopRepository;
    @Autowired
    private MeituanOrderRespository meituanOrderRespository;
    @Override
    public void getOrders() {
        /**
         * 地址：http://api.open.cater.meituan.com/waimai/order/queryById

         3. 请求示例

         http://api.open.cater.meituan.com/waimai/order/queryById?appAuthToken=74d301d082f366b730043dc0a55b403ac0f7ac9e63c93cedd3ab9759a06a1bbf3fc953de835c1b512fd3bee7514cdbf1&charset=utf-8&timestamp=124&sign=39bcfd48c3dd6fbcc19eead125917971e9bf2d61&orderId=2569125129

         4. 请求参数

         参数	类型	名称	是否必须	说明
         appAuthToken	string	认领门店返回的token【一店一token】	必须	系统级参数
         charset	string	交互数据的编码【建议UTF-8】	必须	系统级参数
         timestamp	long	当前请求的时间戳【单位是秒】	必须	系统级参数
         version	string	接口版本【默认是1】	非必须	系统级参数
         sign	string	请求的数字签名	必须	系统级参数
         orderId	long	订单号	必须	业务级参数
         */
        HashMap<String, String> appAuthTokens = new HashMap<>();

        List<MeituanOrder> meituanOrders = meituanOrderRespository.findAllUnSync();
        String uri = "/waimai/order/queryById";
        HashMap<String, String> request = MeituanUtils.defaultParams();
        for (MeituanOrder meituanOrder : meituanOrders) {
            if (!appAuthTokens.containsKey(meituanOrder.getEPoiId())) {
                MeituanShop meituanShop = meituanShopRepository.findOneByEPoiIdAndBusinessId(meituanOrder.getEPoiId(), MeituanConstant.businessIdWaiMai);
                appAuthTokens.put(meituanOrder.getEPoiId(), meituanShop.getAppAuthToken());
            }
            request.put("appAuthToken", appAuthTokens.get(meituanOrder.getEPoiId()));
            request.put("orderId", meituanOrder.getOrderId());
            try {
                JSONObject rsp = MeituanUtils.get(uri, request, "GET", JSONObject.class);
                JSONObject orderInfo = rsp.getJSONObject("data");
                meituanOrder.setOrderInfo(JSON.toJSONString(orderInfo));
                meituanOrder.setIsSync(0);
                meituanOrder.setReGet(1);
                meituanOrder.setUpdateTime(new Date());
                this.meituanOrderRespository.save(meituanOrder);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
