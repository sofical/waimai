package com.ymhrj.ywjx.db.repository;

import com.ymhrj.ywjx.db.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Created by zj on 2017/11/21.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findOneByCode(String code);
    @Query(value = "select count(1) from role", nativeQuery = true)
    Integer getCount();
    @Query(value = "select * from role order by create_time limit :from, :limit", nativeQuery = true)
    List<Role> getRoleList(@Param("from") Integer from, @Param(value = "limit") Integer limit);
    Role findOneByName(String name);
}
