package com.ymhrj.ywjx.controller.vo;

import com.alibaba.fastjson.JSONObject;
import com.ymhrj.ywjx.db.entity.Shop;
import com.ymhrj.ywjx.enums.Platform;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import java.util.UUID;

/**
 * @author : 130801(cgs)
 * Date : 2018-02-26
 * Time : 11:54
 */
@Data
public class ShopCreateVo {
    @Length(max = 50)
    private String code;
    @Length(max = 50)
    private String name;
    private Platform platform;
    private JSONObject extraData;
}
