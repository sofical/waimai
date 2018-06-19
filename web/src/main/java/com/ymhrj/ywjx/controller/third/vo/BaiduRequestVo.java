package com.ymhrj.ywjx.controller.third.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * Created by Administrator on 2017/12/15.
 * {
 "body": {
     "shop": {
         "id": "2520",
         "name": "百度外卖（测试店）"
     },
     "order": {
         "order_id": "14347012309352",
         "send_immediately": 1,
         "send_time": "1",
         "send_fee": 500,
         "package_fee": 200,
         "discount_fee": 0,
         "total_fee": 3700,
         "shop_fee": 3200,
         "user_fee": 3700,
         "pay_type": 1,
         "pay_status": 1,
         "need_invoice": 2,
         "invoice_title": "",
         "remark": "请提供餐具",
         "delivery_party": 1,
         "create_time": "1434701230"
     },
     "user": {
         "name": "测试订单请勿操作",
         "phone": "18912345678",
         "gender": 1,
         "address": "北京海淀区奎科科技大厦 测试",
         "coord": {
         "longitude": 116.143145,
         "latitude": 39.741426
     }
     },
     "products": [
     {
         "product_id": "12277320",
         "product_name": "酱肉包（/份）",
         "product_price": 1200,
         "product_amount": 1,
         "product_fee": 1200,
         "package_price": 100,
         "package_amount": 1,
         "package_fee": 100,
         "total_fee": 1300,
         "upc": ""
     }
     ],
     "discount": []
 },
 "cmd": "order.create",
 "sign": "E362B8AACE4F7A77047A885C0C0D230D",
 "source": "65400",
 "ticket": "909C3B92-8CDD-AF2C-C887-5B660233E2B2",
 "timestamp": 1434701234,
 "version": "2.0"
 }
 */
@Data
public class BaiduRequestVo {
    private String cmd;
    private String sign;
    private String source;
    private String ticket;
    private Long timestamp;
    private String version;
    private JSONObject body;
}
