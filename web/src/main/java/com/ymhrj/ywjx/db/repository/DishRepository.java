package com.ymhrj.ywjx.db.repository;

import com.ymhrj.ywjx.controller.vo.PageData;
import com.ymhrj.ywjx.db.entity.Dish;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Created by Administrator on 2017/12/13.
 */
@Repository
public interface DishRepository extends PagingAndSortingRepository<Dish, UUID> {
    PageData<Dish> getList(UUID shopId,String code, String name, Integer page, Integer limit);

    Dish findByShopIdAndAndCode(UUID shopId,String code);
}
