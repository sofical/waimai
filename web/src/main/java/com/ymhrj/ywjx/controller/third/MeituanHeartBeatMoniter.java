package com.ymhrj.ywjx.controller.third;

import com.alibaba.fastjson.JSONObject;
import com.ymhrj.ywjx.constant.MeituanConstant;
import com.ymhrj.ywjx.service.third.CronService;
import com.ymhrj.ywjx.service.third.MeituanService;
import com.ymhrj.ywjx.service.third.MeituanSyncService;
import com.ymhrj.ywjx.utils.MeituanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/12/13.
 */
@Component
public class MeituanHeartBeatMoniter {
    @Autowired
    private MeituanService meituanService;
    @Autowired
    private MeituanSyncService meituanSyncService;
    @Autowired
    private CronService cronService;

    @Scheduled(fixedRate = 3000000)
    public void heartBeat() {
        /**
         * {"developerId": 100000,"ePoiId": "2","posId": "3","time": 1479022424223}
         */
        String url = "http://heartbeat.meituan.com";
        JSONObject data = new JSONObject();
        data.put("developerId", MeituanConstant.developId);
        data.put("ePoiId", "2");
        data.put("posId", "2");
        data.put("time", String.valueOf((int) (System.currentTimeMillis()/1000)));
        HashMap<String, String> request = new HashMap<>();
        request.put("data", JSONObject.toJSONString(data));
        try {
            JSONObject rsp = MeituanUtils.get("/pos/heartbeat", request, "POST", url, JSONObject.class);
            System.out.println(rsp);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Scheduled(fixedRate = 3000000)
    public void syncComments() {
        this.meituanService.syncComments();
    }

    @Scheduled(fixedRate = 3000000)
    public void syncOrders() {
        this.meituanSyncService.getOrders();
        this.cronService.formatOrder();
    }
}
