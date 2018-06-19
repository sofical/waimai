package com.ymhrj.ywjx.service.base;

import com.ymhrj.ywjx.controller.vo.DishForCreate;
import com.ymhrj.ywjx.controller.vo.DishVo;
import com.ymhrj.ywjx.controller.vo.PageData;
import com.ymhrj.ywjx.db.entity.Dish;

import java.util.UUID;

/**
 * @author : CGS
 * Date : 2018-03-01
 * Time : 19:12
 */
public interface DishService {

    DishVo get(UUID dishId);
    void delete(UUID dishId);
    DishVo create(DishForCreate dishForCreate);
    DishVo update(UUID dishId,DishForCreate dishForCreate);
    PageData<DishVo> getList(UUID shopId,String code,String name, Integer page, Integer limit);

}
