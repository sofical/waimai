package com.ymhrj.ywjx.controller.third.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;

/**
 * Created by zj on 2017/12/14.
 * {
 "requestId": "100000021764401594",
 "type": 10,
 "appId": 22954133,
 "message": "{\"id\":\"1200897812792015983\",\"orderId\":\"1200897812792015983\",\"address\":\"上海市普陀区金沙江路丹巴路119号-NAPOS\",\"createdAt\":\"2017-03-06T12:28:50\",\"activeAt\":\"2017-03-06T12:28:50\",\"deliverFee\":0.0,\"deliverTime\":null,\"description\":\"爱吃辣多点辣\",\"groups\":[{\"name\":\"1号篮子\",\"type\":\"normal\",\"items\":[{\"id\":260,\"skuId\":-1,\"name\":\"红烧肉[重辣]\",\"categoryId\":1,\"price\":4.0,\"quantity\":1,\"total\":4.0,\"additions\":[]}]},{\"name\":\"2号篮子\",\"type\":\"normal\",\"items\":[{\"id\":262,\"skuId\":-1,\"name\":\"狮子头\",\"categoryId\":1,\"price\":5.0,\"quantity\":1,\"total\":5.0,\"additions\":[]}]},{\"name\":\"3号篮子\",\"type\":\"normal\",\"items\":[{\"id\":261,\"skuId\":-1,\"name\":\"奶茶[去冰+半塘]\",\"categoryId\":1,\"price\":3.0,\"quantity\":2,\"total\":6.0,\"additions\":[]}]}],\"invoice\":\"上海市拉拉队有限公司\",\"book\":false,\"onlinePaid\":true,\"railwayAddress\":null,\"phoneList\":[\"13456789012\"],\"shopId\":720032,\"shopName\":\"测试餐厅001\",\"daySn\":7,\"status\":\"unprocessed\",\"refundStatus\":\"noRefund\",\"userId\":13524069,\"totalPrice\":20.0,\"originalPrice\":0.0,\"consignee\":\"饿了么 先生\",\"deliveryGeo\":\"121.3836479187,31.2299251556\",\"deliveryPoiAddress\":\"上海市普陀区金沙江路丹巴路119号-NAPOS\",\"invoiced\":true,\"income\":0.0,\"serviceRate\":0.0,\"serviceFee\":0.0,\"hongbao\":0.0,\"packageFee\":0.0,\"activityTotal\":0.0,\"shopPart\":0.0,\"elemePart\":0.0,\"downgraded\":true,\"vipDeliveryFeeDiscount\":0.0}",
 "shopId": 720032,
 "timestamp": 1488774535366,
 "signature": "2461328351094CA5853415FD25E36E95",
 "userId": 98587250597500702
 }
 */
@Data
public class EleRequestVo {
    @JsonProperty(value = "requestId")
    private String requestId;
    private Integer type;
    @JsonProperty(value = "appId")
    private Integer appId;
    private String message;
    @JsonProperty(value = "shopId")
    private Integer shopId;
    private Long timestamp;
    private String signature;
    @JsonProperty(value = "userId")
    private Long userId;
}
