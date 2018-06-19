package com.ymhrj.ywjx.service.base.impl;

import com.ymhrj.ywjx.controller.vo.DishForCreate;
import com.ymhrj.ywjx.controller.vo.DishVo;
import com.ymhrj.ywjx.controller.vo.PageData;
import com.ymhrj.ywjx.db.entity.Dish;
import com.ymhrj.ywjx.db.repository.DishRepository;
import com.ymhrj.ywjx.service.base.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author : CGS
 * Date : 2018-03-01
 * Time : 19:18
 */
@Service
public class DishServiceimpl implements DishService{

    @Autowired
    private DishRepository dishRepository;

    @Override
    public DishVo get(UUID dishId) {
        Dish dish = this.dishRepository.findOne(dishId);
        return this.poToVo(dish);
    }

    @Override
    public void delete(UUID dishId) {
        this.dishRepository.delete(dishId);
    }

    @Override
    public DishVo create(DishForCreate dishForCreate) {
        Dish dish = new Dish();
        dish.setCode(dishForCreate.getCode());
        dish.setName(dishForCreate.getName());
        dish.setStyle(dishForCreate.getStyle());
        dish.setShopId(dishForCreate.getShopId());
        dish.setTaste(dishForCreate.getTaste());
        dish.setTag(dishForCreate.getTag());
        dish.setDishId(UUID.randomUUID());
        dish.setCreateTime(new Date());
        dish.setUpdateTime(new Date());
        this.dishRepository.save(dish);
        return this.poToVo(dish);
    }

    @Override
    public DishVo update(UUID dishId, DishForCreate dishForCreate) {
        Dish dish = this.dishRepository.findOne(dishId);
        if(dish == null){
            throw new RuntimeException("不存在");
        }
        dish.setCode(dishForCreate.getCode());
        dish.setName(dishForCreate.getName());
        dish.setStyle(dishForCreate.getStyle());
        dish.setTaste(dishForCreate.getTaste());
        dish.setTag(dishForCreate.getTag());
        this.dishRepository.save(dish);
        return this.poToVo(dish);
    }

    @Override
    public PageData<DishVo> getList(UUID shopId,String code, String name, Integer page, Integer limit) {
        PageData<Dish> dishPageData = this.dishRepository.getList(shopId,code,name,page,limit);
        PageData<DishVo> result = new PageData<>();
        List<DishVo> voList =  new ArrayList<>();
        for (Dish dish : dishPageData.getData()){
            voList.add(this.poToVo(dish));
        }
        result.setData(voList);
        result.setCount(dishPageData.getCount());
        return result;
    }


    private DishVo poToVo(Dish dish){
        DishVo dishVo = new DishVo();
        BeanUtils.copyProperties(dish,dishVo);
        return dishVo;
    }
}
