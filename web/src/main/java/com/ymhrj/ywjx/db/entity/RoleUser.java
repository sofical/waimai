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
 * Created by Administrator on 2017/11/21.
 */
@Data
@Entity
@Table(name = "role_user")
public class RoleUser {
    @Id
    @Type(type = "uuid-char")
    @Column(name = "role_user_id")
    private UUID roleUserId;

    @Type(type = "uuid-char")
    @Column(name = "role_id")
    private UUID roleId;

    @Type(type = "uuid-char")
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "create_time")
    private Date createTime;
}
