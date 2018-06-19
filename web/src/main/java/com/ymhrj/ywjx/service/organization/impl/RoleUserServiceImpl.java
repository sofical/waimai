package com.ymhrj.ywjx.service.organization.impl;

import com.ymhrj.ywjx.db.entity.Role;
import com.ymhrj.ywjx.db.entity.RoleUser;
import com.ymhrj.ywjx.db.repository.RoleUserRepository;
import com.ymhrj.ywjx.service.organization.RoleService;
import com.ymhrj.ywjx.service.organization.RoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by zj on 2017/11/21.
 */
@Component
public class RoleUserServiceImpl implements RoleUserService {
    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleUserRepository roleUserRepository;

    @Override
    public RoleUser init(UUID userId) {
        Role role = roleService.init();
        RoleUser roleUser = roleUserRepository.findOneByRoleIdAndUserId(role.getRoleId(), userId);
        if (null == roleUser) {
            roleUser = new RoleUser();
            roleUser.setRoleUserId(UUID.randomUUID());
            roleUser.setRoleId(role.getRoleId());
            roleUser.setUserId(userId);
            roleUserRepository.save(roleUser);
        }
        return roleUser;
    }

    @Override
    public List<String> getRoles(UUID userId) {
        List<RoleUser> roleUsers = roleUserRepository.findAllByUserId(userId);
        List<String> roleAll = new ArrayList<>();
        for (RoleUser roleUser : roleUsers) {
            Role role = roleService.get(roleUser.getRoleId());
            String roleString = role.getRights();
            if (!StringUtils.isEmpty(roleString)) {
                String[] roles = roleString.split(",");
                for (String rights : roles) {
                    roleAll.add(rights.trim());
                }
            }
        }
        return roleAll;
    }
}
