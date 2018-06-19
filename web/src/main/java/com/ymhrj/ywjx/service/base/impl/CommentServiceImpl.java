package com.ymhrj.ywjx.service.base.impl;

import com.alibaba.fastjson.JSONObject;
import com.ymhrj.ywjx.controller.vo.ChartVo;
import com.ymhrj.ywjx.controller.vo.CommentVo;
import com.ymhrj.ywjx.controller.vo.PageData;
import com.ymhrj.ywjx.db.entity.Comments;
import com.ymhrj.ywjx.db.repository.CommentsRepository;
import com.ymhrj.ywjx.service.base.CommentService;
import com.ymhrj.ywjx.service.base.ShopService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author : CGS
 * Date : 2018-05-05
 * Time : 15:07
 */
@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentsRepository commentsRepository;
    @Autowired
    private ShopService shopService;

    /**
     * Gets list.
     *
     * @param platform  the platform
     * @param shopId    the shop id
     * @param beginDate the begin date
     * @param endDate   the end date
     * @param page      the page
     * @param limit     the limit
     * @return the list
     */
    @Override
    public PageData<CommentVo> getList(Integer platform, UUID shopId, String beginDate, String endDate, Integer page, Integer limit) {
        PageData<Comments> ordersPageData = this.commentsRepository.getList(platform,shopId,beginDate,endDate,page,limit);
        List<CommentVo> voList = new ArrayList<>();
        PageData<CommentVo> voResult  = new PageData<>();
        for (Comments comments : ordersPageData.getData()){
            voList.add(this.poToVo(comments,true));
        }
        voResult.setCount(ordersPageData.getCount());
        voResult.setData(voList);
        return voResult;
    }


    /**
     * Gets comment alias.
     *
     * @param platform  the platform
     * @param shopId    the shop id
     * @param beginDate the begin date
     * @param endDate   the end date
     * @param column    the column
     * @return the comment alias
     */
    @Override
    public List<ChartVo> getCommentAlias(Integer platform, UUID shopId, String beginDate, String endDate, String column) {
        List<JSONObject> list = this.commentsRepository.getCommentAlias(platform,shopId,beginDate,endDate,column);
        List<ChartVo> chartVoList = new ArrayList<>();
        for (JSONObject json : list){
            ChartVo chartVo = new ChartVo();
            chartVo.setName(json.getString("score")+"星");
            chartVo.setValue(json.getString("comment_num"));
            chartVoList.add(chartVo);
        }
        return chartVoList;
    }

    /**
     * po=>vo
     * @param comments
     * @param withDetail
     * @return
     */
    private CommentVo poToVo(Comments comments,Boolean withDetail){
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comments,commentVo);
        if(withDetail){
            commentVo.setShopName(this.shopService.getShopName(comments.getShopId()));
        }
        return commentVo;
    }
}
