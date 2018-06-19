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
@Table(name = "order_dish")
public class OrderDish {
    @Id
    @Type(type = "uuid-char")
    @Column(name = "order_dish_id")
    private UUID orderDishId;
    @Column(name = "order_id")
    @Type(type = "uuid-char")
    private UUID orderId;
    @Column(name = "dish_id")
    @Type(type = "uuid-char")
    private UUID dishId;
    @Column(name = "num")
    private Integer num;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
}
