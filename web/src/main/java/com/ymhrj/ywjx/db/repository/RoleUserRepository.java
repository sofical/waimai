package com.ymhrj.ywjx.db.repository;

import com.ymhrj.ywjx.db.entity.RoleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Created by zj on 2017/11/21.
 */
@Repository
public interface RoleUserRepository extends JpaRepository<RoleUser, UUID> {
    RoleUser findOneByRoleIdAndUserId(UUID roleId, UUID UserId);
    List<RoleUser> findAllByUserId(UUID userId);
}
