package com.ymhrj.ywjx.controller.third;

import com.ymhrj.ywjx.constant.MeituanConstant;
import com.ymhrj.ywjx.controller.third.vo.MeituanBindVo;
import com.ymhrj.ywjx.controller.third.vo.MeituanSuccessVo;
import com.ymhrj.ywjx.service.third.MeituanService;
import com.ymhrj.ywjx.service.third.MeituanSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/12/12.
 */
@RestController
@RequestMapping("/third/meituan")
public class MeituanContorller {
    @Autowired
    private MeituanService meituanService;
    @Autowired
    private MeituanSyncService meituanSyncService;
    @RequestMapping("/heart")
    public Object heart() {
        return new MeituanSuccessVo();
    }

    @RequestMapping("/bind")
    public Object bind(@RequestParam("appAuthToken") String appAuthToken,
                       @RequestParam("businessId") String businessId,
                       @RequestParam("ePoiId") String ePoiId,
                       @RequestParam("timestamp") String timestamp) {
        MeituanBindVo bindVo = new MeituanBindVo();
        bindVo.setBusinessId(businessId);
        bindVo.setAppAuthToken(appAuthToken);
        bindVo.setEPoiId(ePoiId);
        bindVo.setTimestamp(timestamp);
        meituanService.bind(bindVo);
        return new MeituanSuccessVo();
    }
    @RequestMapping("/unbind")
    public Object unbind(@RequestParam("developerId") String developerId,
                         @RequestParam("appAuthToken") String appAuthToken,
                         @RequestParam("businessId") String businessId,
                         @RequestParam("ePoiId") String ePoiId,
                         @RequestParam("epoiId") String epoiId,
                         @RequestParam("timestamp") String timestamp) {
        if(!ePoiId.equals(epoiId)) {
            throw new RuntimeException("epoiId与ePoiId不一致");
        }
        if (!MeituanConstant.developId.equals(developerId)) {
            throw new RuntimeException("非本系统接入门店");
        }
        meituanService.unbind(businessId, ePoiId);
        return new MeituanSuccessVo();
    }
    @RequestMapping("/order")
    public Object order(@RequestParam("developerId") String developerId,
                        @RequestParam("ePoiId") String ePoiId,
                        @RequestParam("order") String order) {
        if (!MeituanConstant.developId.equals(developerId)) {
            throw new RuntimeException("非本系统接入门店");
        }
        meituanService.addOrder(ePoiId, order);
        return new MeituanSuccessVo("OK");
    }

    @RequestMapping("/test")
    public Object test() {
        meituanSyncService.getOrders();
        return null;
    }

    @RequestMapping("/comments")
    public Object comments() {
        this.meituanService.syncComments();
        return null;
    }
}
