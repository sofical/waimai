package com.ymhrj.ywjx.service.third.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ymhrj.ywjx.constant.MeituanConstant;
import com.ymhrj.ywjx.controller.third.vo.MeituanBindVo;
import com.ymhrj.ywjx.db.entity.MeituanComment;
import com.ymhrj.ywjx.db.entity.MeituanOrder;
import com.ymhrj.ywjx.db.entity.MeituanShop;
import com.ymhrj.ywjx.db.repository.MeituanCommentRespository;
import com.ymhrj.ywjx.db.repository.MeituanOrderRespository;
import com.ymhrj.ywjx.db.repository.MeituanShopRepository;
import com.ymhrj.ywjx.service.third.MeituanService;
import com.ymhrj.ywjx.utils.MeituanUtils;
import com.ymhrj.ywjx.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by SONY on 2017/12/12.
 */
@Component
public class MeituanServiceImpl implements MeituanService {
    @Autowired
    private MeituanShopRepository meituanShopRepository;
    @Autowired
    private MeituanOrderRespository meituanOrderRespository;
    @Autowired
    private MeituanCommentRespository meituanCommentRespository;
    @Override
    public void bind(MeituanBindVo bindVo) {
        MeituanShop existed = meituanShopRepository.findOneByEPoiIdAndBusinessId(bindVo.getEPoiId(), bindVo.getBusinessId());
        if (existed == null) {
            existed = new MeituanShop();
            existed.setMeituanShopId(UUID.randomUUID());
            existed.setEPoiId(bindVo.getEPoiId());
        }
        existed.setAppAuthToken(bindVo.getAppAuthToken());
        existed.setBusinessId(bindVo.getBusinessId());
        existed.setCreateTime(new Date(Long.valueOf(bindVo.getTimestamp())));
        meituanShopRepository.save(existed);
    }

    @Override
    public void unbind(String businessId, String ePoiId) {
        MeituanShop existed = meituanShopRepository.findOneByEPoiIdAndBusinessId(ePoiId, businessId);
        if (null != existed) {
            meituanShopRepository.delete(existed);
        }
    }

    @Override
    public void addOrder(String ePoiId, String order) {
        System.out.print("get order");
        System.out.print(order);
        JSONObject orderInfo = JSONObject.parseObject(order);
        String orderId = orderInfo.getString("orderId");
        MeituanOrder meituanOrder = meituanOrderRespository.findOneByEPoiIdAndOrderId(ePoiId, orderId);
        if (null == meituanOrder) {
            meituanOrder = new MeituanOrder();
            meituanOrder.setMeituanOrderId(UUID.randomUUID());
            meituanOrder.setEPoiId(ePoiId);
            meituanOrder.setOrderId(orderId);
            meituanOrder.setIsSync(0);
            meituanOrder.setReGet(0);
        }
        meituanOrder.setOrderInfo(order);
        meituanOrderRespository.save(meituanOrder);
    }
    /**
     * developerId:100120
     ePoiId:9384019
     sign:52b379754c40c7865a48b84a24fb99c1ebb49f11
     order:{
     "caution":"哈哈",
     "cityId":132,
     "ctime":12341234,
     "daySeq":"1",
     "deliveryTime":124124123,
     "detail":[{
     "app_food_code": "1", // erp端菜品id, 对应菜品映射中的eDishCode
     "food_name": "狗不理",//菜品名
     "sku_id": "1",//erp端菜品skuId, 对应菜品映射中的eDishSkuCode
     "quantity": 6,//份数
     "price": 100 ,//原价
     "box_num":2,//餐盒总个数
     "box_price":1,//餐盒费，单价
     "unit": "份",//单位
     "food_discount": 0.8, //(菜品折扣，只是美团商家、APP方配送的门店才会设置，默认为1。),
     "spec":"大份",//菜品规格
     "food_property":"中辣,微甜"// (菜品属性，多个属性用英文逗号隔开)
     "cart_id":0//商品所在的口袋，0为1号口袋，1为2号口袋，以此类推
     }],
     "ePoiId":"erp-poi",
     "extras":[{
     "act_detail_id":10,(活动id)
     "reduce_fee": 2.5, (活动优惠金额，是美团承担活动费用和商户承担活动费用的总和)
     "mt_charge":1.5, (优惠金额中美团承担的部分)
     "poi_charge":1.5, (优惠金额中商家承担的部分)
     "remark": "满10元减2.5元",（优惠说明）
     "type":2,（活动类型）
     "avg_send_time":5.5(餐厅平均送餐时间，单位为分钟)
     },{
     "reduce_fee": 5, (优惠金额，是美团承担活动费用和商户承担活动费用的总和)
     "remark": "新用户立减5元",（优惠说明）
     "type":1,（活动类型）
     "avg_send_time":1.0(餐厅平均送餐时间，单位为分钟)
     },
     {"rider_fee":10 (骑手应付款，只对美团配送线上支付线下结算的商家有效) }
     {…},],
     "favorites":false,
     "hasInvoiced":1,
     "invoiceTitle":"XXX公司",
     "taxpayerId":"91110108562144110X",
     "isFavorites":false,
     "isPoiFirstOrder":false,
     "isThirdShipping":0,
     "latitude":234.12341234,
     "longitude":534.12341234,
     "logisticsCode":1002,
     "orderId":12341234,
     "orderIdView":12343412344,
     "originalPrice":25,
     "payType":2,
     "pickType":0,
     "poiAddress":"望京-研发园",
     "poiFirstOrder":false,
     "poiName":"门店名称",
     "poiPhone":"18610543723",
     "poiReceiveDetail":"{"actOrderChargeByMt" (美团承担明细) :[{
     "comment":"美团配送减3.0元" (备注),
     "feeTypeDesc":"活动款" (明细费用类型描述),
     "feeTypeId":10019 (明细费用类型Id),
     "moneyCent":300 (明细金额（分）)}],
     "actOrderChargeByPoi" (商家承担明细):[{
     "comment":"美团配送减3.0元",
     "feeTypeDesc":"活动款",
     "feeTypeId":10019,
     "moneyCent":0}],
     "foodShareFeeChargeByPoi":390 (菜品分成 (分)),
     "logisticsFee":300 (配送费 (分)),
     "onlinePayment":2000 (在线支付款 (分)),
     "wmPoiReceiveCent":1610 (商家应收款（分）)}",
     "recipientAddress":"望京-西小区-8号楼5层",
     "recipientName":"X先生",
     "recipientPhone":"18610543723",
     "shipperPhone":"18610543723",
     "shippingFee":5,
     "status":1,
     "total":20,
     "utime":235131234
     }
     */


    /**
     * 参数	类型	名称	是否必须	备注
     appAuthToken	string	认领门店返回的token【一店一token】	必须	系统级参数
     charset	string	交互数据的编码【建议UTF-8】	必须	系统级参数
     timestamp	long	当前请求的时间戳【单位是秒】	必须	系统级参数
     version	string	接口版本【默认是1】	非必须	系统级参数
     sign	string	请求的数字签名	必须	系统级参数
     ePoiId	string	ERP方门店id 最大长度100	必须	业务级参数
     startTime	long	查询评价起始时间	必须	业务级参数
     endTime	long	查询评价结束时间	必须	业务级参数
     offset	int	查询起始位置	必须	业务级参数
     limit	int	查询条数	必须	业务级参数


     返回参数
     字段	类型	含义	实例	备注
     addComment	string	追评内容	"吃完了，感觉不错"
     addCommentTime	string	追评时间
     commentContent	string	评价内容	OK
     commentId	string	评价Id	615952059
     deliveryCommentScore	string	配送评分	5	五分满分
     foodCommentScore	string	菜品评分	5	五分满分
     orderCommentScore	string	综合评分	5	五分满分
     result	string	操作结果	ok	正常返回
     */
    @Override
    public void syncComments() {
        HashMap<String, String> request = new HashMap<>();
        request.put("charset", MeituanConstant.charset);
        request.put("timestamp", String.valueOf(TimeUtil.str2Ts()));
        request.put("startTime", String.valueOf(TimeUtil.startOfToday()));
        request.put("endTime", String.valueOf(TimeUtil.endOfToday()));
        request.put("offset", MeituanConstant.commentPageOffset);
        request.put("limit", MeituanConstant.commentPageLimit);
        request.put("version", MeituanConstant.apiVersion);

        List<MeituanShop> shops = this.meituanShopRepository.findAll();
        for (MeituanShop shop : shops) {
            request.put("appAuthToken", shop.getAppAuthToken());
            request.put("ePoiId", shop.getEPoiId());

            try {
                JSONObject result = MeituanUtils.get("/waimai/poi/queryReviewList", request, "GET", JSONObject.class);
                if (result.containsKey("data")) {
                    JSONArray comments = (JSONArray) result.get("data");
                    for (Object commentObj : comments) {
                        JSONObject comment = (JSONObject) commentObj;
                        String commentId = comment.getString("commentId");
                        MeituanComment meituanComment = this.meituanCommentRespository.findOneByCommentIdAndEPoiId(commentId, shop.getEPoiId());
                        if (null == meituanComment) {
                            meituanComment = new MeituanComment();
                            meituanComment.setMeituanCommentId(UUID.randomUUID());
                            meituanComment.setCreateTime(new java.util.Date());
                            meituanComment.setCommentId(commentId);
                            meituanComment.setEPoiId(shop.getEPoiId());
                        }
                        meituanComment.setCommentInfo(JSON.toJSONString(commentObj));
                        meituanComment.setUpdateTime(new java.util.Date());
                        this.meituanCommentRespository.save(meituanComment);
                    }
                }
            } catch (Exception e) {
                System.out.print(e);
            }
        }
    }
}
