package com.ymhrj.ywjx.service.base.impl;

import com.alibaba.fastjson.JSONObject;
import com.ymhrj.ywjx.controller.vo.BrokenLineVo;
import com.ymhrj.ywjx.controller.vo.ChartVo;
import com.ymhrj.ywjx.controller.vo.DayOrderVo;
import com.ymhrj.ywjx.controller.vo.OrderVo;
import com.ymhrj.ywjx.controller.vo.PageData;
import com.ymhrj.ywjx.db.entity.Orders;
import com.ymhrj.ywjx.db.entity.Shop;
import com.ymhrj.ywjx.db.repository.OrderDishRepository;
import com.ymhrj.ywjx.db.repository.OrdersRepository;
import com.ymhrj.ywjx.db.repository.ShopRepository;
import com.ymhrj.ywjx.enums.Platform;
import com.ymhrj.ywjx.service.base.OrderDishService;
import com.ymhrj.ywjx.service.base.OrderService;
import com.ymhrj.ywjx.service.base.ShopService;
import com.ymhrj.ywjx.utils.InUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author : CGS
 * Date : 2018-03-17
 * Time : 15:13
 */
@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private OrderDishService orderDishService;
    @Autowired
    private OrderDishRepository orderDishRepository;
    @Autowired
    private ShopService shopService;

    private static final String TYPE_PLATFORM = "platform";
    private static final String TYPE_SHOP = "shop_id";
    private static final String[] COUNT_ARR = {"order_id","customer_id"};
    private static final String[] SUM_ARR = {"origin_fee","bonus_fee","real_fee"};

    @Override
    public PageData<DayOrderVo> getDayStat(Integer platform, UUID shopId, String beginDate, String endDate, String column, Integer page, Integer limit, Boolean asc) {
        PageData<JSONObject> pageData = this.ordersRepository.getDayStat(platform,shopId,beginDate,endDate,column,page,limit,asc);
        PageData<DayOrderVo> resultPage = new PageData<>();
        List<DayOrderVo> voList = new ArrayList<>();
        for (JSONObject row : pageData.getData()){
            DayOrderVo dayOrder = new DayOrderVo(UUID.fromString(row.getString("shop_id")),row.getInteger("platform") ,row.getString("order_date"),row.getBigDecimal("fee"));
            dayOrder.setShopName(this.shopService.getShopName(dayOrder.getShopId()));
            voList.add(dayOrder);
        }
        resultPage.setData(voList);
        resultPage.setCount(pageData.getCount());
        return resultPage;
    }


    /**
     * Gets every day stat.
     *
     * @param platform  the platform
     * @param shopId    the shop id
     * @param beginDate the begin date
     * @param endDate   the end date
     * @param column    the column
     * @return the every day stat
     */
    @Override
    public BrokenLineVo getEveryDayStat(Integer platform, UUID shopId, String beginDate, String endDate, String column) {
        List<JSONObject>  list = this.ordersRepository.getDayStatNotShop(platform,shopId,beginDate,endDate,column,true);

        BrokenLineVo vo = new BrokenLineVo();

        List<String> names = new ArrayList<>();
        List<BigDecimal> values = new ArrayList<>();
       for (JSONObject row : list){
           names.add(row.getString("order_date"));
           values.add(row.getBigDecimal("fee"));
       }
        vo.setNames(names);
        vo.setValues(values);
        return vo;
    }

    /**
     * Gets list.
     *
     * @param platform        the platform
     * @param shopId          the shop id
     * @param beginDate       the begin date
     * @param endDate         the end date
     * @param customerPhone   the customer phone
     * @param customerName    the customer name
     * @param customerAddress the customer address
     * @param page            the page
     * @param limit           the limit
     * @return the list
     */
    @Override
    public PageData<OrderVo> getList(Integer platform, UUID shopId, String beginDate, String endDate, String customerPhone, String customerName, String customerAddress, Integer page, Integer limit) {
        PageData<Orders> ordersPageData = this.ordersRepository.getList(platform,shopId,beginDate,endDate,customerPhone,customerName,customerAddress,page,limit);
        List<OrderVo> voList = new ArrayList<>();
        PageData<OrderVo> voResult  = new PageData<>();
        for (Orders orders : ordersPageData.getData()){
            voList.add(this.poToVo(orders,true));
        }
        voResult.setCount(ordersPageData.getCount());
        voResult.setData(voList);
        return voResult;
    }

    /**
     * 退单
     * @param platform
     * @param shopId
     * @param beginDate
     * @param endDate
     * @return
     */
    @Override
    public List<ChartVo> getBackOrderReason(Integer platform, UUID shopId, String beginDate, String endDate) {
        List<ChartVo> chartVoList = new ArrayList<>();
        ChartVo chartVo1 = new ChartVo();
        chartVo1.setName("来不及");
        chartVo1.setValue("100");
        chartVoList.add(chartVo1);
        ChartVo chartVo2 = new ChartVo();
        chartVo2.setName("不想吃");
        chartVo2.setValue("100");
        chartVoList.add(chartVo2);
        return chartVoList;
    }

    /**
     * Gets shop total stat.
     *
     * @param platform  the platform
     * @param shopId    the shop id
     * @param beginDate the begin date
     * @param endDate   the end date
     * @param column    the column
     * @return the shop total stat
     */
    @Override
    public List<ChartVo> getShopTotalStat(Integer platform, UUID shopId, String beginDate, String endDate, String column) {
        List<JSONObject> resList = this.ordersRepository.getShopTotalStat(platform,shopId,beginDate,endDate,column);
        List<ChartVo> chartVoList = new ArrayList<>();
        for (JSONObject row: resList){
            ChartVo chartVo = new ChartVo();
            Shop shop = this.shopRepository.findOne(UUID.fromString(row.getString("shop_id")));
            if(shop != null){
                chartVo.setName("["+Platform.valueOf(row.getInteger("platform")).getDesc()+"]"+shop.getName());
            }else {
                continue;
            }
            chartVo.setValue(row.getString("fee"));
            chartVoList.add(chartVo);
        }
        return chartVoList;
    }

    /**
     * Compare list.
     *
     * @param type      the type
     * @param dimension the dimension
     * @return the list
     */
    @Override
    public List<ChartVo> compare(String type, String dimension) {

        List<ChartVo> list =  new ArrayList<>();
        if(TYPE_PLATFORM.equals(type)){
            List<JSONObject> resList =this.getByDimension(type,dimension);
            for (JSONObject row: resList){
                ChartVo chartVo = new ChartVo();
                chartVo.setName(Platform.valueOf(row.getInteger("platform")).getDesc());
                chartVo.setValue(row.getString("num"));
                list.add(chartVo);
            }
        }else if(TYPE_SHOP.equals(type)){
            List<JSONObject> resList = this.getByDimension(type,dimension);
            for (JSONObject row: resList){
                ChartVo chartVo = new ChartVo();
                Shop shop = this.shopRepository.findOne(UUID.fromString(row.getString("shop_id")));
                if(shop != null){
                    chartVo.setName(shop.getName());
                }else {
                    continue;
                }
                chartVo.setValue(row.getString("num"));
                list.add(chartVo);
            }
        }else {
            throw new RuntimeException("type 错误");
        }
        return list;
    }


    /**
     * Gets hot shops.
     *
     * @param platform  the platform
     * @param shopId    the shop id
     * @param beginDate the begin date
     * @param endDate   the end date
     * @return the hot shops
     */
    @Override
    public List<ChartVo> getHotShops(Integer platform, UUID shopId, String beginDate, String endDate) {
        List<JSONObject> hotShops = this.ordersRepository.getHotShops(platform,shopId,beginDate,endDate);
        List<ChartVo> list = new ArrayList<>();
        for (JSONObject row : hotShops){
            ChartVo chartVo = new ChartVo();
            chartVo.setName(this.shopService.getShopName(UUID.fromString(row.getString("shop_id"))));
            chartVo.setValue(row.getString("num"));
            list.add(chartVo);
        }
        return list;
    }

    @Override
    public List<ChartVo> getHotHour(Integer platform, UUID shopId, String beginDate, String endDate) {
        List<JSONObject> hotHour = this.ordersRepository.getHotHour(platform,shopId,beginDate,endDate);
        List<ChartVo> list = new ArrayList<>();
        Map<Integer,String> hourMap = new HashMap<>();
        for (JSONObject row : hotHour){
            hourMap.put(row.getInteger("hot_hour"),row.getString("num"));
        }
        for (Integer hour = 0;hour < 24 ; hour ++){
            ChartVo chartVo = new ChartVo();
            chartVo.setName(hour.toString());
            if(hourMap.containsKey(hour) ){
                chartVo.setValue(hourMap.get(hour));
            }else {
                chartVo.setValue(String.valueOf(0));
            }

            list.add(chartVo);
        }
        return list;
    }

    /**
     * Stat old new customer list.
     *
     * @param platform  the platform
     * @param shopId    the shop id
     * @param beginDate the begin date
     * @param endDate   the end date
     * @return the list
     */
    @Override
    public List<ChartVo> statOldNewCustomer(Integer platform, UUID shopId, String beginDate, String endDate) {
        ChartVo newCustomer = new ChartVo();
        ChartVo oldCustomer = new ChartVo();
        newCustomer.setName("新客户");
        newCustomer.setValue(this.ordersRepository.statCustomerNum(platform,shopId,beginDate,endDate,false).toString());
        oldCustomer.setName("老客户");
        oldCustomer.setValue(this.ordersRepository.statCustomerNum(platform,shopId,beginDate,endDate,true).toString());
        List<ChartVo> list = new ArrayList<>();
        list.add(newCustomer);
        list.add(oldCustomer);
        return list;
    }

    @Override
    public Map<String,List<ChartVo>> getCustomerAnalysis(UUID customerId) {

        Map<String, List<ChartVo>> map = new HashMap<>();
        List<JSONObject> platformList = this.ordersRepository.getCustomerPlatoform(customerId);
        List<ChartVo> platforms = new ArrayList<>();
        for (JSONObject pf : platformList){
            ChartVo chartVo = new ChartVo();
            chartVo.setName(Platform.valueOf(pf.getInteger("platform")).getDesc());
            chartVo.setValue(pf.getInteger("num").toString());
            platforms.add(chartVo);
        }
        map.put("platform",platforms);


        List<ChartVo> dishes = new ArrayList<>();
        List<JSONObject> dishList = this.orderDishRepository.getCustomerDish(customerId);
        for (JSONObject dishJson : dishList){
            ChartVo chartVo = new ChartVo();
            chartVo.setName(dishJson.getString("name"));
            chartVo.setValue(dishJson.getString("number"));
            dishes.add(chartVo);
        }
        map.put("dish",dishes);


        List<ChartVo> hours = new ArrayList<>();
        List<JSONObject> hourList = this.ordersRepository.getCustomerHour(customerId);
        for (JSONObject hourJson : hourList){
            ChartVo chartVo = new ChartVo();
            chartVo.setName(hourJson.getString("hour_num")+"点");
            chartVo.setValue(hourJson.getString("num"));
            hours.add(chartVo);
        }
        map.put("hour",hours);
        return map;
    }

    private List<JSONObject> getByDimension(String type, String dimension){
        List<JSONObject> resList ;
        if(InUtils.isInArr(COUNT_ARR,dimension)){
            resList = this.ordersRepository.statByCount(type,dimension);
        }else if(InUtils.isInArr(SUM_ARR,dimension)){
            resList = this.ordersRepository.statBySum(type,dimension);
        }else {
            throw new RuntimeException("dimension 出错");
        }
        return resList;
    }

    private OrderVo poToVo(Orders orders, Boolean withDetail){
        OrderVo orderVo = new OrderVo();
        BeanUtils.copyProperties(orders,orderVo);
        if (withDetail){
            orderVo.setShopName(this.shopService.getShopName(orderVo.getShopId()));

            orderVo.setDishes(this.orderDishService.findByOrderId(orders.getOrderId()));
        }
        return orderVo;
    }


}
