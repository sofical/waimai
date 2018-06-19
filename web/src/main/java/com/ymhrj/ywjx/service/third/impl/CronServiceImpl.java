package com.ymhrj.ywjx.service.third.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ymhrj.ywjx.db.entity.BaiduOrder;
import com.ymhrj.ywjx.db.entity.Comments;
import com.ymhrj.ywjx.db.entity.Customer;
import com.ymhrj.ywjx.db.entity.Dish;
import com.ymhrj.ywjx.db.entity.EleOrder;
import com.ymhrj.ywjx.db.entity.MeituanComment;
import com.ymhrj.ywjx.db.entity.MeituanOrder;
import com.ymhrj.ywjx.db.entity.OrderDish;
import com.ymhrj.ywjx.db.entity.Orders;
import com.ymhrj.ywjx.db.entity.Shop;
import com.ymhrj.ywjx.db.repository.BaiduOrderRespository;
import com.ymhrj.ywjx.db.repository.CommentsRepository;
import com.ymhrj.ywjx.db.repository.CustomerRepository;
import com.ymhrj.ywjx.db.repository.DishRepository;
import com.ymhrj.ywjx.db.repository.EleOrderRespository;
import com.ymhrj.ywjx.db.repository.MeituanCommentRespository;
import com.ymhrj.ywjx.db.repository.MeituanOrderRespository;
import com.ymhrj.ywjx.db.repository.OrderDishRepository;
import com.ymhrj.ywjx.db.repository.OrdersRepository;
import com.ymhrj.ywjx.db.repository.ShopRepository;
import com.ymhrj.ywjx.enums.Platform;
import com.ymhrj.ywjx.service.third.CronService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author : CGS
 * Date : 2018-04-01
 * Time : 16:10
 */
@Service
@Slf4j
public class CronServiceImpl implements CronService{

    @Autowired
    private MeituanOrderRespository meituanOrderRespository;
    @Autowired
    private BaiduOrderRespository baiduOrderRespository;
    @Autowired
    private EleOrderRespository eleOrderRespository;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private OrderDishRepository orderDishRepository;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private MeituanCommentRespository meituanCommentRespository;
    @Autowired
    private CommentsRepository commentsRepository;

    @Override
    public String formatOrder() {

        this.formatMeituanOrders();
        this.formatBaiduOrders();
        this.formatEleOrders();
        return null;
    }


    private void formatMeituanOrders(){
        List<MeituanOrder> list = this.meituanOrderRespository.findByIsSync(0);

        for (MeituanOrder order : list){

            Orders orders = this.ordersRepository.findByPlatformAndOriginOrderId(Platform.MEITUAN.getValue(),order.getOrderId());
            if(orders == null){
                orders = new Orders();
                orders.setOrderId(UUID.randomUUID());
                orders.setCreateTime(new Date());
                orders.setUpdateTime(new Date());
                orders.setPlatform(Platform.MEITUAN.getValue());
                orders.setOriginOrderId(order.getOrderId());
            }else {
                orders.setUpdateTime(new Date());


            }
            JSONObject orderInfo = JSONObject.parseObject(order.getOrderInfo());

            // 店铺id
            Shop shop = this.shopRepository.findByPlatformAndCode(Platform.MEITUAN.getValue(),orderInfo.getString("poiId"));
            if(shop == null){
                continue;
            }
            // 客户id
            Customer customer = this.customerRepository.findByPhone(orderInfo.getString("recipientPhone"));
            List<String> names;
            List<String> address;
            if(customer == null){
                customer = new Customer();
                customer.setCreateTime(new Date());

                names = new ArrayList<>();
                address = new ArrayList<>();
                customer.setCustomerId(UUID.randomUUID());
                names.add(orderInfo.getString("recipientName"));
                address.add(orderInfo.getString("recipientAddress"));

            }else {
                names = JSON.parseArray(customer.getNames(), String.class);
                address = JSON.parseArray(customer.getAddressList(), String.class);
                if(!names.contains(orderInfo.getString("recipientName"))){
                    names.add(orderInfo.getString("recipientName"));
                }
                if(!address.contains(orderInfo.getString("recipientAddress"))){
                    address.add(orderInfo.getString("recipientAddress"));
                }


            }
            customer.setUpdateTime(new Date());
            customer.setNames(JSON.toJSONString(names));
            customer.setAddressList(JSON.toJSONString(address));
            customer.setPhone(orderInfo.getString("recipientPhone"));
            this.customerRepository.save(customer);


            // 订单
            orders.setShopId(shop.getShopId());
            orders.setCustomerId(customer.getCustomerId());
            orders.setCustomerName(orderInfo.getString("recipientName"));
            orders.setCustomerAddress(orderInfo.getString("recipientAddress"));
            orders.setCustomerPhone(orderInfo.getString("recipientPhone"));
            orders.setOriginFee(orderInfo.getBigDecimal("originalPrice"));
            orders.setRealFee(orderInfo.getBigDecimal("total"));
            orders.setBonusFee(orders.getOriginFee().subtract(orders.getRealFee()));
            orders.setOrderTime(new Date(orderInfo.getLong("cTime")*1000));
            this.ordersRepository.save(orders);


            // 保存菜单菜品
            List<JSONObject> foodList = JSON.parseArray(orderInfo.getString("detail"),JSONObject.class);
            for (JSONObject food : foodList){
                //获取菜品
                Dish dish = this.dishRepository.findByShopIdAndAndCode(shop.getShopId(),food.getString("app_food_code"));
                if(dish == null){
                    dish = new Dish();
                    dish.setShopId(shop.getShopId());
                    dish.setCreateTime(new Date());
                    dish.setUpdateTime(new Date());
                    dish.setDishId(UUID.randomUUID());
                    dish.setCode(food.getString("app_food_code"));
                    dish.setName(food.getString("food_name"));
                    dish.setStyle("");
                    dish.setTag("");
                    dish.setTaste("");
                    this.dishRepository.save(dish);
                }
                OrderDish orderDish = new OrderDish();
                orderDish.setOrderDishId(UUID.randomUUID());
                orderDish.setDishId(dish.getDishId());
                orderDish.setNum(food.getInteger("quantity"));
                orderDish.setOrderId(orders.getOrderId());
                this.orderDishRepository.save(orderDish);
            }


            order.setIsSync(1);
            this.meituanOrderRespository.save(order);
        }
    }

    private void formatBaiduOrders(){
        List<BaiduOrder> list = this.baiduOrderRespository.findByIsSync(0);

        for (BaiduOrder order : list){

            Orders orders = this.ordersRepository.findByPlatformAndOriginOrderId(Platform.BAIDU.getValue(),order.getOrderId());
            if(orders == null){
                orders = new Orders();
                orders.setOrderId(UUID.randomUUID());
                orders.setCreateTime(new Date());
                orders.setUpdateTime(new Date());
                orders.setPlatform(Platform.BAIDU.getValue());
                orders.setOriginOrderId(order.getOrderId());
            }else {
                orders.setUpdateTime(new Date());


            }
            JSONObject orderInfo = JSONObject.parseObject(order.getOrderInfo());

            // 店铺id
            JSONObject baiduShop = JSONObject.parseObject(orderInfo.getString("shop"));
            Shop shop = this.shopRepository.findByPlatformAndCode(Platform.BAIDU.getValue(),baiduShop.getString("id"));
            if(shop == null){
                continue;
            }
            // 客户id
            JSONObject baiduCustomer = JSONObject.parseObject(orderInfo.getString("user"));
            Customer customer = this.customerRepository.findByPhone(baiduCustomer.getString("phone"));
            List<String> names;
            List<String> address;
            if(customer == null){
                customer = new Customer();
                customer.setCreateTime(new Date());
                names = new ArrayList<>();
                address = new ArrayList<>();
                customer.setCustomerId(UUID.randomUUID());
                names.add(baiduCustomer.getString("name"));
                address.add(baiduCustomer.getString("address"));

            }else {
                names = JSON.parseArray(customer.getNames(), String.class);
                address = JSON.parseArray(customer.getAddressList(), String.class);
                if(!names.contains(baiduCustomer.getString("name"))){
                    names.add(baiduCustomer.getString("name"));
                }
                if(!address.contains(baiduCustomer.getString("address"))){
                    address.add(baiduCustomer.getString("address"));
                }


            }
            customer.setUpdateTime(new Date());
            customer.setNames(JSON.toJSONString(names));
            customer.setAddressList(JSON.toJSONString(address));
            customer.setPhone(baiduCustomer.getString("phone"));
            this.customerRepository.save(customer);


            // 订单
            JSONObject baiduOrder = JSONObject.parseObject(orderInfo.getString("order"));
            orders.setShopId(shop.getShopId());
            orders.setCustomerId(customer.getCustomerId());
            orders.setCustomerName(baiduCustomer.getString("name"));
            orders.setCustomerAddress(baiduCustomer.getString("address"));
            orders.setCustomerPhone(baiduCustomer.getString("phone"));
            orders.setOriginFee(BigDecimal.valueOf(baiduOrder.getLong("total_fee")/100));
            orders.setRealFee(BigDecimal.valueOf(baiduOrder.getLong("user_fee")/100));
            orders.setBonusFee(orders.getOriginFee().subtract(orders.getRealFee()));
            orders.setOrderTime(new Date(baiduOrder.getLong("create_time")*1000));
            this.ordersRepository.save(orders);


            // 保存菜单菜品
            List<JSONObject> foodList = JSON.parseArray(orderInfo.getString("products"),JSONObject.class);
            for (JSONObject food : foodList){
                //获取菜品
                Dish dish = this.dishRepository.findByShopIdAndAndCode(shop.getShopId(),food.getString("app_food_code"));
                if(dish == null){
                    dish = new Dish();
                    dish.setShopId(shop.getShopId());
                    dish.setCreateTime(new Date());
                    dish.setUpdateTime(new Date());
                    dish.setDishId(UUID.randomUUID());
                    dish.setCode(food.getString("product_id"));
                    dish.setName(food.getString("product_name"));
                    dish.setStyle("");
                    dish.setTag("");
                    dish.setTaste("");
                    this.dishRepository.save(dish);
                }
                OrderDish orderDish = new OrderDish();
                orderDish.setOrderDishId(UUID.randomUUID());
                orderDish.setDishId(dish.getDishId());
                orderDish.setNum(food.getInteger("product_amount"));
                orderDish.setOrderId(orders.getOrderId());
                this.orderDishRepository.save(orderDish);
            }


            order.setIsSync(1);
            this.baiduOrderRespository.save(order);
        }
    }

    private void formatEleOrders(){
        List<EleOrder> list = this.eleOrderRespository.findByIsSync(0);

        for (EleOrder order : list){

            Orders orders = this.ordersRepository.findByPlatformAndOriginOrderId(Platform.ELE.getValue(),order.getOrderId());
            if(orders == null){
                orders = new Orders();
                orders.setOrderId(UUID.randomUUID());
                orders.setCreateTime(new Date());
                orders.setUpdateTime(new Date());
                orders.setPlatform(Platform.ELE.getValue());
                orders.setOriginOrderId(order.getOrderId());
            }else {
                orders.setUpdateTime(new Date());
            }
            JSONObject orderInfo = JSONObject.parseObject(order.getOrderInfo());

            // 店铺id
            Shop shop = this.shopRepository.findByPlatformAndCode(Platform.ELE.getValue(),orderInfo.getString("shopId"));
            if(shop == null){
                continue;
            }
            // 客户id
            List<String> phoneList = JSON.parseArray(orderInfo.getString("phoneList"),String.class);
            if(phoneList.size() <= 0){
                continue;
            }
            String phone = phoneList.get(0);
            Customer customer = this.customerRepository.findByPhone(phone);
            List<String> names;
            List<String> address;
            if(customer == null){
                customer = new Customer();
                customer.setCreateTime(new Date());

                names = new ArrayList<>();
                address = new ArrayList<>();
                customer.setCustomerId(UUID.randomUUID());
                names.add(orderInfo.getString("consignee"));
                address.add(orderInfo.getString("deliveryPoiAddress"));

            }else {
                names = JSON.parseArray(customer.getNames(), String.class);
                address = JSON.parseArray(customer.getAddressList(), String.class);
                if(!names.contains(orderInfo.getString("consignee"))){
                    names.add(orderInfo.getString("consignee"));
                }
                if(!address.contains(orderInfo.getString("deliveryPoiAddress"))){
                    address.add(orderInfo.getString("deliveryPoiAddress"));
                }


            }
            customer.setUpdateTime(new Date());
            customer.setNames(JSON.toJSONString(names));
            customer.setAddressList(JSON.toJSONString(address));
            customer.setPhone(phone);
            this.customerRepository.save(customer);


            // 订单
            orders.setShopId(shop.getShopId());
            orders.setCustomerId(customer.getCustomerId());
            orders.setCustomerName(orderInfo.getString("consignee"));
            orders.setCustomerAddress(orderInfo.getString("deliveryPoiAddress"));
            orders.setCustomerPhone(phone);
            orders.setOriginFee(orderInfo.getBigDecimal("totalPrice"));

            // todo真实费用没找到字段 ,
            orders.setRealFee(orderInfo.getBigDecimal("totalPrice"));
            orders.setBonusFee(orders.getOriginFee().subtract(orders.getRealFee()));
            orders.setOrderTime(new Date(orderInfo.getLong("createdAt")));
            this.ordersRepository.save(orders);


            // 保存菜单菜品
            List<JSONObject> groupList = JSON.parseArray(orderInfo.getString("groups"),JSONObject.class);
            for (JSONObject group : groupList){
                List<JSONObject> foodList = JSON.parseArray(group.getString("items"),JSONObject.class);
                for (JSONObject food : foodList){
                    //获取菜品
                    Dish dish = this.dishRepository.findByShopIdAndAndCode(shop.getShopId(),food.getString("id"));
                    if(dish == null){
                        dish = new Dish();
                        dish.setShopId(shop.getShopId());
                        dish.setCreateTime(new Date());
                        dish.setUpdateTime(new Date());
                        dish.setDishId(UUID.randomUUID());
                        dish.setCode(food.getString("id"));
                        dish.setName(food.getString("name"));
                        dish.setStyle("");
                        dish.setTag("");
                        dish.setTaste("");
                        this.dishRepository.save(dish);
                    }
                    OrderDish orderDish = new OrderDish();
                    orderDish.setOrderDishId(UUID.randomUUID());
                    orderDish.setDishId(dish.getDishId());
                    orderDish.setNum(food.getInteger("quantity"));
                    orderDish.setOrderId(orders.getOrderId());
                    this.orderDishRepository.save(orderDish);
                }
            }



            order.setIsSync(1);
            this.eleOrderRespository.save(order);
        }
    }


    @Override
    public String formatComment() {
        this.formatMeituanComment();
        return null;
    }

    private void formatMeituanComment(){
        List<MeituanComment> meituanCommentList = this.meituanCommentRespository.findByIsSync(0);
        for (MeituanComment meituanComment : meituanCommentList){
            JSONObject commentJson = JSONObject.parseObject(meituanComment.getCommentInfo());
            Comments oldComments = this.commentsRepository.findByOriginCommentId(meituanComment.getCommentId());
            if(oldComments != null){
                continue;
            }
            Comments newComment = new Comments();
            newComment.setCommentId(UUID.randomUUID());
//            newComment.setCommentTime(commentJson.getDate("addCommentTime"));
            newComment.setCommentTime(meituanComment.getCreateTime());
            newComment.setContent(commentJson.getString("commentContent"));
            newComment.setFoodScore(commentJson.getInteger("foodCommentScore"));
            newComment.setOrderScore(commentJson.getInteger("orderCommentScore"));
            newComment.setDeliveryScore(commentJson.getInteger("deliveryCommentScore"));
            newComment.setOriginCommentId(meituanComment.getCommentId());
            // 基本信息
            newComment.setPlatform(Platform.MEITUAN.getValue());
            String extra ="%\"e_po_id\":\""+meituanComment.getEPoiId()+"\"%";
            Shop shop = this.shopRepository.findByExtraLike(extra);
            System.out.println(extra);
            if(shop == null){
                log.info("美团 e_po_id"+meituanComment.getEPoiId()+" 有问题,找到,");
                continue;
            }
            newComment.setShopId(shop.getShopId());
            newComment.setOrderId(null);
            newComment.setCreateTime(new Date());
            newComment.setUpdateTime(new Date());

            this.commentsRepository.save(newComment);
            meituanComment.setIsSync(1);
            this.meituanCommentRespository.save(meituanComment);
        }

    }
}
