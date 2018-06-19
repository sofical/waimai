package com.ymhrj.ywjx.db.repository;

import com.ymhrj.ywjx.db.entity.User;
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
public interface UserRepository extends JpaRepository<User, UUID> {
    User findOneByAccount(String account);

    @Query(value = "select count(1) from user", nativeQuery = true)
    Integer getUserCount();

    @Query(value = "select * from user order by create_time desc limit :from, :limit", nativeQuery = true)
    List<User> getUserList(@Param("from") Integer from, @Param("limit") Integer limit);

    @Query(value = "select count(1) from user where user_id in (select user_id from role_user where role_id=:role_id)", nativeQuery = true)
    Integer getRoleUserCount(@Param("role_id") String roleUserId);

    @Query(value = "select * from user where user_id in (select user_id from role_user where role_id=:role_id) order by create_time desc limit :from, :limit", nativeQuery = true)
    List<User> getRoleUserList(@Param("role_id") String roleUserId, @Param("from") Integer from, @Param("limit") Integer limit);
}
