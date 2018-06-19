package com.ymhrj.ywjx.db.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

/**
 * Created by cgs on 2017/12/18.
 */
@Data
@Entity
@Table(name = "comments")
public class Comments {
    @Id
    @Type(type = "uuid-char")
    @Column(name = "comment_id")
    private UUID commentId;
    @Column(name = "shop_id")
    @Type(type = "uuid-char")
    private UUID shopId;
    @Column(name = "order_id")
    @Type(type = "uuid-char")
    private UUID orderId;

    @Column(name = "platform")
    private Integer platform;

    @Column(name = "content")
    private String content;
    @Column(name = "order_score")
    private Integer orderScore;
    @Column(name = "food_score")
    private Integer foodScore;
    @Column(name = "delivery_score")
    private Integer deliveryScore;
    @Column(name = "origin_comment_id")
    private String originCommentId;
    @Column(name = "comment_time")
    private Date commentTime;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;

}
