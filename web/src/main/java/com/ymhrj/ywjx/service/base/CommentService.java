package com.ymhrj.ywjx.service.base;

import com.ymhrj.ywjx.controller.vo.ChartVo;
import com.ymhrj.ywjx.controller.vo.CommentVo;
import com.ymhrj.ywjx.controller.vo.PageData;

import java.util.List;
import java.util.UUID;

/**
 * The interface Comment service.
 *
 * @author : CGS Date : 2018-05-05 Time : 15:07
 */
public interface CommentService {

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
    PageData<CommentVo> getList(Integer platform, UUID shopId, String beginDate, String endDate, Integer page, Integer limit);

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
    List<ChartVo> getCommentAlias(Integer platform, UUID shopId, String beginDate, String endDate, String column);

}
