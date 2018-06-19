package com.ymhrj.ywjx.controller;

import com.ymhrj.ywjx.controller.vo.ChartVo;
import com.ymhrj.ywjx.controller.vo.CommentVo;
import com.ymhrj.ywjx.controller.vo.PageData;
import com.ymhrj.ywjx.service.base.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * @author : CGS
 * Date : 2018-05-05
 * Time : 15:08
 */
@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public PageData<CommentVo> getList(
            @RequestParam(value = "platform", required = false) Integer platform,
            @RequestParam(value = "shop_id", required = false) UUID shopId,
            @RequestParam(value = "begin_date", required = false) String beginDate,
            @RequestParam(value = "end_date", required = false) String endDate,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit
    ){
        return this.commentService.getList(platform,shopId,beginDate,endDate,page,limit);
    }

    @RequestMapping(value = "/analysis", method = RequestMethod.GET)
    public List<ChartVo> getHotShops(
            @RequestParam(value = "platform", required = false) Integer platform,
            @RequestParam(value = "shop_id", required = false) UUID shopId,
            @RequestParam(value = "begin_date", required = false) String beginDate,
            @RequestParam(value = "end_date", required = false) String endDate,
            @RequestParam(value = "column", required = false,defaultValue = "order_score") String column) {
        return this.commentService.getCommentAlias(platform,shopId,beginDate,endDate,column);
    }
}
