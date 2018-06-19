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
@Table(name = "meituan_comment")
public class MeituanComment {
    @Id
    @Type(type = "uuid-char")
    @Column(name = "meituan_comment_id")
    private UUID meituanCommentId;

    @Column(name = "e_poi_id")
    private String ePoiId;

    @Column(name = "comment_id")
    private String commentId;

    @Column(name = "comment_info")
    private String commentInfo;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "is_sync")
    private Integer isSync = 0;
}
