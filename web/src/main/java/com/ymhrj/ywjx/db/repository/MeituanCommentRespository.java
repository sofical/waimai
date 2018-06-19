package com.ymhrj.ywjx.db.repository;

import com.ymhrj.ywjx.db.entity.MeituanComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017/12/13.
 */
public interface MeituanCommentRespository extends JpaRepository<MeituanComment, UUID> {
    /**
     * find one by comment id.
     * @param commentId
     * @return
     */
    MeituanComment findOneByCommentIdAndEPoiId(String commentId, String ePoiId);

    List<MeituanComment> findByIsSync(Integer isSync);
}
