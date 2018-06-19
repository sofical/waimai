package com.ymhrj.ywjx.service.organization.impl;

import com.ymhrj.ywjx.controller.vo.PageData;
import com.ymhrj.ywjx.controller.vo.RoleCreateVo;
import com.ymhrj.ywjx.controller.vo.RoleRightsVo;
import com.ymhrj.ywjx.controller.vo.RoleUserCreateVo;
import com.ymhrj.ywjx.db.entity.Role;
import com.ymhrj.ywjx.db.entity.RoleUser;
import com.ymhrj.ywjx.db.entity.User;
import com.ymhrj.ywjx.db.repository.RoleRepository;
import com.ymhrj.ywjx.db.repository.RoleUserRepository;
import com.ymhrj.ywjx.db.repository.UserRepository;
import com.ymhrj.ywjx.enums.RightEnum;
import com.ymhrj.ywjx.service.organization.RoleService;
import com.ymhrj.ywjx.utils.ContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * Created by zj on 2017/11/21.
 */
@Component
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleUserRepository roleUserRepository;

    private final String INIT_CODE = "SUPPER_MAN";
    private final String INIT_NAME = "超级管理员";

    @Override
    public Role init() {
        Role role = roleRepository.findOneByCode(INIT_CODE);
        if (null == role) {
            String rights = "";
            for (RightEnum e : RightEnum.values()) {
                rights += ("".equals(rights) ? "" : ",") + String.valueOf(e);
            }
            role = new Role();
            role.setRoleId(UUID.randomUUID());
            role.setCode(INIT_CODE);
            role.setName(INIT_NAME);
            role.setRights(rights);
            roleRepository.save(role);
        }
        return role;
    }

    @Override
    public Role get(UUID roleId) {
        return roleRepository.findOne(roleId);
    }

    @Override
    public PageData<Role> getRoleList(Integer from, Integer limit) {
        PageData<Role> result = new PageData<>();
        result.setCount(this.roleRepository.getCount());
        List<Role> data = this.roleRepository.getRoleList(from, limit);
        result.setData(data);
        return result;
    }

    @Override
    public Role create(RoleCreateVo roleCreateVo) {
        Role existedCode = this.roleRepository.findOneByCode(roleCreateVo.getCode());
        if (null != existedCode) {
            throw new RuntimeException("相同编码已存在");
        }
        Role existedName = this.roleRepository.findOneByName(roleCreateVo.getName());
        if (null != existedName) {
            throw new RuntimeException("相同名称已存在");
        }
        Role role = new Role();
        role.setRoleId(UUID.randomUUID());
        role.setCode(roleCreateVo.getCode());
        role.setName(roleCreateVo.getName());
        role.setRights("");
        return this.roleRepository.save(role);
    }

    @Override
    public void delete(UUID roleId) {
        Role role = this.roleRepository.findOne(roleId);
        if (null == role) {
            throw new RuntimeException("角色不存在或已被删除");
        }
        if (this.INIT_CODE .equals(role.getCode())) {
            throw new RuntimeException("超管角色不能删除");
        }
        this.roleRepository.delete(role);
    }

    @Override
    public PageData<User> getRoleUsers(UUID roleId, Integer from, Integer limit) {
        PageData<User> result = new PageData<>();
        result.setCount(this.userRepository.getRoleUserCount(String.valueOf(roleId)));
        List<User> data = this.userRepository.getRoleUserList(String.valueOf(roleId), from, limit);
        result.setData(data);
        return result;
    }

    @Override
    public RoleUser createRoleUser(UUID roleId, RoleUserCreateVo roleUserCreateVo) {
        User user = this.userRepository.findOneByAccount(roleUserCreateVo.getAccount());
        if (null == user) {
            throw new RuntimeException("账号不存在");
        }

        RoleUser existed = this.roleUserRepository.findOneByRoleIdAndUserId(roleId, user.getUserId());
        if (null != existed) {
            throw new RuntimeException("该角色下已存在相同账号，请勿重复添加");
        }

        RoleUser roleUser = new RoleUser();
        roleUser.setRoleUserId(UUID.randomUUID());
        roleUser.setRoleId(roleId);
        roleUser.setUserId(user.getUserId());
        return this.roleUserRepository.save(roleUser);
    }

    @Override
    public void deleteRoleUser(UUID roleId, UUID userId) {
        if (ContextUtils.getUserId().equals(userId)) {
            throw new RuntimeException("不能删除自己");
        }
        RoleUser roleUser = this.roleUserRepository.findOneByRoleIdAndUserId(roleId, userId);
        if (null != roleUser) {
            this.roleUserRepository.delete(roleUser);
        }
    }

    @Override
    public void setRights(UUID roleId, List<RoleRightsVo> rights) {
        String str = "";
        for(RoleRightsVo roleRightsVo : rights) {
            str += ("".equals(str) ? "":",") + roleRightsVo.getCode();
        }
        Role role = this.roleRepository.findOne(roleId);
        if (null == role) {
            throw new RuntimeException("角色不存在");
        }
        role.setRights(str);
        this.roleRepository.save(role);
    }
}
