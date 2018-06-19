package com.ymhrj.ywjx.db.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;
import java.util.UUID;

/**
 * Created by SONY on 2017/12/12.
 */
@Data
@Entity
@Table(name = "meituan_shop")
public class MeituanShop {
    @Id
    @Type(type = "uuid-char")
    @Column(name = "meituan_shop_id")
    private UUID meituanShopId;

    @Column(name = "app_auth_token")
    private String appAuthToken;

    @Column(name = "business_id")
    private String businessId;

    @Column(name = "e_poi_id")
    private String ePoiId;

    @Column(name = "create_time")
    private Date createTime;
}
