package com.ymhrj.ywjx.service.base.impl;

import com.alibaba.fastjson.JSONObject;
import com.ymhrj.ywjx.controller.vo.ChartVo;
import com.ymhrj.ywjx.controller.vo.OrderDishVo;
import com.ymhrj.ywjx.db.entity.Dish;
import com.ymhrj.ywjx.db.entity.OrderDish;
import com.ymhrj.ywjx.db.repository.DishRepository;
import com.ymhrj.ywjx.db.repository.OrderDishRepository;
import com.ymhrj.ywjx.service.base.OrderDishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author : CGS
 * Date : 2018-03-25
 * Time : 16:03
 */
@Service
public class OrderDishServiceImpl implements OrderDishService{
    @Autowired
    private OrderDishRepository orderDishRepository;
    @Autowired
    private DishRepository dishRepository;
    @Override
    public List<OrderDishVo> findByOrderId(UUID orderId) {
        List<OrderDish> orderDishList = this.orderDishRepository.findByOrderId(orderId);
        List<OrderDishVo> voList = new ArrayList<>();
        for (OrderDish dish : orderDishList){
            voList.add(this.poToVo(dish));
        }
        return voList;
    }

    @Override
    public List<ChartVo> getHotDishes(Integer platform, UUID shopId, String beginDate, String endDate) {
        List<JSONObject> hotDishes = this.orderDishRepository.getHotDishes(platform,shopId,beginDate,endDate);
        List<ChartVo> list = new ArrayList<>();
        for (JSONObject row : hotDishes){
            ChartVo chartVo = new ChartVo();
            chartVo.setName(this.getDishName(UUID.fromString(row.getString("dish_id"))));
            chartVo.setValue(row.getString("num"));
            list.add(chartVo);
        }
        return list;
    }


    @Override
    public List<ChartVo> statByDimension(Integer platform, UUID shopId, String beginDate, String endDate, String column) {
        List<JSONObject> jsonObjectList = this.orderDishRepository.statByDishDimension(platform,shopId,beginDate,endDate,column);
        List<ChartVo> chartVoList = new ArrayList<>();
        for (JSONObject json : jsonObjectList){
            ChartVo chartVo = new ChartVo();
            if(json.getString("dimension").equals("")){
                chartVo.setName("未定义");
            }else {
                chartVo.setName(json.getString("dimension"));
            }
            chartVo.setValue(json.getString("num"));
            chartVoList.add(chartVo);
        }
        return chartVoList;
    }

    private OrderDishVo poToVo(OrderDish orderDish){
        OrderDishVo vo = new OrderDishVo();
        BeanUtils.copyProperties(orderDish,vo);
        vo.setDishName(this.getDishName(orderDish.getDishId()));

        return vo;
    }
    private String getDishName(UUID dishId){
        Dish dish = this.dishRepository.findOne(dishId);
        if(dish != null){
            return dish.getName();
        }else {
            return "";
        }
    }
}
