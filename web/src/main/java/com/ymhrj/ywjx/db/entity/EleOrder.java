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
 * Created by Administrator on 2017/12/13.
 */
@Data
@Entity
@Table(name = "ele_order")
public class EleOrder {
    @Id
    @Type(type = "uuid-char")
    @Column(name = "ele_order_id")
    private UUID eleOrderId;

    @Column(name = "shop_id")
    private Integer shopId;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "order_info")
    private String orderInfo;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "is_sync")
    private Integer isSync;
}
