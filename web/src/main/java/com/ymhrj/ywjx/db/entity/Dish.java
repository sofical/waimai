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
@Table(name = "dish")
public class Dish {
    @Id
    @Type(type = "uuid-char")
    @Column(name = "dish_id")
    private UUID dishId;
    @Column(name = "code")
    private String code;
    @Column(name = "name")
    private String name;
    @Column(name = "shop_id")
    @Type(type = "uuid-char")
    private UUID shopId;
    @Column(name = "style")
    private String style;
    @Column(name = "taste")
    private String taste;
    @Column(name = "tag")
    private String tag;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
}
