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
@Table(name = "customer")
public class Customer {
    @Id
    @Type(type = "uuid-char")
    @Column(name = "customer_id")
    private UUID customerId;
    @Column(name = "phone")
    private String phone;
    @Column(name = "names")
    private String names;
    @Column(name = "address_list")
    private String addressList;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
}
