package com.ymhrj.ywjx.controller.third.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * Created by Administrator on 2017/12/15.
 */
@Data
public class BaiduSuccessBodyVo {
    private String errno = "0";
    private String error = "success";
    private JSONObject data = new JSONObject();
}
