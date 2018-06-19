package com.ymhrj.ywjx.controller;

import com.ymhrj.ywjx.controller.vo.DishForCreate;
import com.ymhrj.ywjx.controller.vo.DishVo;
import com.ymhrj.ywjx.controller.vo.PageData;
import com.ymhrj.ywjx.controller.vo.ShopCreateVo;
import com.ymhrj.ywjx.controller.vo.ShopVo;
import com.ymhrj.ywjx.db.entity.Dish;
import com.ymhrj.ywjx.service.base.DishService;
import com.ymhrj.ywjx.service.base.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/v1/dishes")
public class DishController {

    @Autowired
    private DishService dishService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public PageData<DishVo> getDishes(
            @RequestParam(value = "shop_id", required = false) UUID shopId,
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
       return dishService.getList(shopId,code,name,page,limit);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public DishVo create(@RequestBody @Validated DishForCreate dishForCreate) {
        return dishService.create(dishForCreate);
    }

    @RequestMapping(value = "/{dish_id}", method = RequestMethod.PUT)
    public DishVo create(@PathVariable("dish_id") UUID dishId,@RequestBody @Validated DishForCreate dishForCreate) {
        return dishService.update(dishId,dishForCreate);
    }

    @RequestMapping(value = "/{dish_id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("dish_id") UUID dishId) {
        dishService.delete(dishId);
    }


}
