package com.ymhrj.ywjx.controller.third.vo;

import com.alibaba.fastjson.JSONObject;
import com.ymhrj.ywjx.utils.MD5Util;
import lombok.Data;

import java.util.*;

/**
 * Created by Administrator on 2017/12/15.
 */
@Data
public class BaiduSuccessVo {
    public BaiduSuccessVo(String cmd, BaiduRequestVo request, BaiduSuccessBodyVo body) {
        this.cmd = cmd;
        this.timestamp = String.valueOf((int)(System.currentTimeMillis()/1000));
        this.version = request.getVersion();
        this.source = request.getSource();
        this.ticket = request.getTicket();
        this.body = body;
        //签名
        JSONObject signBody = new JSONObject();
        signBody.put("cmd", this.cmd);
        signBody.put("timestamp", this.timestamp);
        signBody.put("version", this.version);
        signBody.put("source", this.source);
        signBody.put("ticket", this.ticket);
        signBody.put("body", this.body);
        JSONObject signBodyFinal = new JSONObject();
        List<Map.Entry<String, Object>> infoIds = new ArrayList<>(signBody.entrySet());
        Collections.sort(infoIds, new Comparator<Map.Entry<String, Object>>() {
            @Override
            public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
                return (o1.getKey().compareTo(o2.getKey()));
            }
        });
        for (int i=0; i<infoIds.size(); i++) {
            signBodyFinal.put(infoIds.get(i).getKey(), infoIds.get(i).getValue());
        }
        String sign = MD5Util.encode(JSONObject.toJSONString(signBodyFinal));
        this.sign = sign;
    }
    private String cmd;
    private String timestamp;
    private String version;
    private String ticket;
    private String source;
    private String sign;
    private BaiduSuccessBodyVo body;
}
