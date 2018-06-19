package com.ymhrj.ywjx.controller.vo;

import com.alibaba.fastjson.JSONObject;
import com.ymhrj.ywjx.db.entity.Shop;
import lombok.Data;

/**
 * @author : 130801(cgs)
 * Date : 2018-02-26
 * Time : 11:54
 */
@Data
public class ShopVo extends Shop{
    private JSONObject extraData;
}
