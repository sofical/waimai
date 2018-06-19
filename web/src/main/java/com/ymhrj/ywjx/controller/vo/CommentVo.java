package com.ymhrj.ywjx.controller.vo;

import com.ymhrj.ywjx.db.entity.Comments;
import lombok.Data;

/**
 * @author : CGS
 * Date : 2018-05-05
 * Time : 15:12
 */
@Data
public class CommentVo extends Comments{
    private String shopName;
}
