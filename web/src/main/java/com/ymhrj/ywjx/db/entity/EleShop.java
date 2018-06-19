package com.ymhrj.ywjx.db.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 * Created by zj on 2017/12/18.
 */
@Data
@Entity
@Table(name = "ele_shop")
public class EleShop {
    @Id
    @Type(type = "uuid-char")
    @Column(name = "ele_shop_id")
    private UUID eleShopId;
    @Column(name = "shop_code")
    private String shopCode;
    @Column(name = "access_token")
    private String accessToken;
    @Column(name = "token_type")
    private String tokenType;
    @Column(name = "expires_in")
    private Long expiresIn;
    @Column(name = "refresh_token")
    private String refreshToken;
}
