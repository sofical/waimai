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
@Table(name = "meituan_order")
public class MeituanOrder {
    @Id
    @Type(type = "uuid-char")
    @Column(name = "meituan_order_id")
    private UUID meituanOrderId;

    @Column(name = "e_poi_id")
    private String ePoiId;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "order_info")
    private String orderInfo;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "is_sync")
    private Integer isSync = 0;

    @Column(name = "re_get")
    private Integer reGet = 0;
}
