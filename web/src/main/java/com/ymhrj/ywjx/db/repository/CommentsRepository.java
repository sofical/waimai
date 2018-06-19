package com.ymhrj.ywjx.db.repository;

import com.alibaba.fastjson.JSONObject;
import com.ymhrj.ywjx.controller.vo.PageData;
import com.ymhrj.ywjx.db.entity.Comments;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017/12/13.
 */
@Repository
public interface CommentsRepository extends PagingAndSortingRepository<Comments, UUID> {

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
    PageData<Comments> getList(Integer platform, UUID shopId, String beginDate, String endDate,  Integer page, Integer limit);

    /**
     * Find by origin comment id comments.
     *
     * @param originCommentId the origin comment id
     * @return the comments
     */
    Comments findByOriginCommentId(String originCommentId);

    /**
     * Gets hot shops.
     *
     * @param platform  the platform
     * @param shopId    the shop id
     * @param beginDate the begin date
     * @param endDate   the end date
     * @param column    the column
     * @return the hot shops
     */
    List<JSONObject> getCommentAlias(Integer platform, UUID shopId, String beginDate, String endDate,String column);

}
