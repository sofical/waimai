package com.ymhrj.ywjx.service.organization;

import com.ymhrj.ywjx.db.entity.RoleUser;

import java.util.List;
import java.util.UUID;

/**
 * Created by zj on 2017/11/21.
 */
public interface RoleUserService {
    RoleUser init(UUID userId);
    List<String> getRoles(UUID userId);
}
