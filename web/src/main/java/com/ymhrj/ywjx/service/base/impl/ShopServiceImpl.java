package com.ymhrj.ywjx.service.base.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ymhrj.ywjx.controller.vo.PageData;
import com.ymhrj.ywjx.controller.vo.ShopCreateVo;
import com.ymhrj.ywjx.controller.vo.ShopVo;
import com.ymhrj.ywjx.db.entity.Shop;
import com.ymhrj.ywjx.db.repository.ShopRepository;
import com.ymhrj.ywjx.service.base.ShopService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author : 130801(cgs)
 * Date : 2018-02-26
 * Time : 13:54
 */
@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopRepository shopRepository;



    /**
     * Create shop vo.
     *
     * @param shopCreateVo the shop create vo
     * @return the shop vo
     */
    @Override
    public ShopVo create(ShopCreateVo shopCreateVo) {
        Shop shop = new Shop();
        BeanUtils.copyProperties(shopCreateVo,shop);
        shop.setShopId(UUID.randomUUID());
        shop.setPlatform(shopCreateVo.getPlatform().getValue());
        shop.setCreateTime(new Date());
        shop.setUpdateTime(new Date());
        if(shopCreateVo.getExtraData() == null){
            shopCreateVo.setExtraData(new JSONObject());
        }
        shop.setExtra(shopCreateVo.getExtraData().toJSONString());
        this.shopRepository.save(shop);

        return this.poToVo(shop);
    }


    @Override
    public ShopVo update(UUID shopId,ShopCreateVo shopCreateVo) {
        Shop shop = this.shopRepository.findOne(shopId);
        if(shop == null){
            throw new RuntimeException("不存在");
        }

        shop.setCode(shopCreateVo.getCode());
        shop.setName(shopCreateVo.getName());
        shop.setUpdateTime(new Date());
        if(shopCreateVo.getExtraData() == null){
            shopCreateVo.setExtraData(new JSONObject());
        }
        shop.setExtra(shopCreateVo.getExtraData().toJSONString());
        this.shopRepository.save(shop);

        return this.poToVo(shop);
    }

    /**
     * Gets list.
     *
     * @param code     the code
     * @param name     the name
     * @param platform the platform
     * @param page     the page
     * @param limit    the limit
     * @return the list
     */
    @Override
    public PageData<ShopVo> getList(String code, String name, Integer platform, Integer page, Integer limit) {
        PageData<Shop> shopPageData = this.shopRepository.getList(code,name,platform,page,limit);
        PageData<ShopVo> voPageData = new PageData<>();
        List<ShopVo> voList = new ArrayList<>();
        for (Shop shop : shopPageData.getData()){
            voList.add(this.poToVo(shop));
        }
        voPageData.setCount(shopPageData.getCount());
        voPageData.setData(voList);
        return voPageData;
    }


    /**
     * Delete.
     *
     * @param shopId the shop id
     */
    @Override
    public void delete(UUID shopId) {
        this.shopRepository.delete(shopId);
    }

    /**
     * Gets shop name.
     *
     * @param shopId the shop id
     * @return the shop name
     */
    @Override
    public String getShopName (UUID shopId){
        Shop shop = this.shopRepository.findOne(shopId);
        if(shop != null){
            return  shop.getName();
        }
        return "";
    }

    private ShopVo poToVo(Shop shop){
        ShopVo vo = new ShopVo();
        BeanUtils.copyProperties(shop,vo);
        vo.setExtraData(JSON.parseObject(shop.getExtra()));
        return vo;
    }
}
