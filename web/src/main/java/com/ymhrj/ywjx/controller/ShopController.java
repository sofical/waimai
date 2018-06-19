package com.ymhrj.ywjx.controller;

import com.ymhrj.ywjx.controller.vo.PageData;
import com.ymhrj.ywjx.controller.vo.ShopCreateVo;
import com.ymhrj.ywjx.controller.vo.ShopVo;
import com.ymhrj.ywjx.db.entity.User;
import com.ymhrj.ywjx.service.base.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author : cgs
 * Date : 2018-02-26
 * Time : 11:51
 */
@RestController
@RequestMapping("/api/v1/shops")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public PageData<ShopVo> getShops(
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "platform", required = false) Integer platform,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
       return shopService.getList(code,name,platform,page,limit);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ShopVo create(@RequestBody @Validated  ShopCreateVo shopCreateVo) {
        return shopService.create(shopCreateVo);
    }

    @RequestMapping(value = "/{shop_id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("shop_id") UUID shopId) {
        shopService.delete(shopId);
    }

    @RequestMapping(value = "/{shop_id}", method = RequestMethod.PUT)
    public void update(@PathVariable("shop_id") UUID shopId,@RequestBody @Validated  ShopCreateVo shopCreateVo) {
        shopService.update(shopId,shopCreateVo);
    }


}
