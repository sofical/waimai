package com.ymhrj.ywjx.service.organization;

import com.ymhrj.ywjx.controller.vo.PageData;
import com.ymhrj.ywjx.controller.vo.RoleCreateVo;
import com.ymhrj.ywjx.controller.vo.RoleRightsVo;
import com.ymhrj.ywjx.controller.vo.RoleUserCreateVo;
import com.ymhrj.ywjx.db.entity.Role;
import com.ymhrj.ywjx.db.entity.RoleUser;
import com.ymhrj.ywjx.db.entity.User;

import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017/11/21.
 */
public interface RoleService {
    Role init();
    Role get(UUID roleId);
    PageData<Role> getRoleList(Integer from, Integer limit);
    Role create(RoleCreateVo roleCreateVo);
    void delete(UUID roleId);
    PageData<User> getRoleUsers(UUID roleId, Integer from, Integer limit);
    RoleUser createRoleUser(UUID roleId, RoleUserCreateVo roleUserCreateVo);
    void deleteRoleUser(UUID roleId, UUID userId);
    void setRights(UUID roleId, List<RoleRightsVo> rights);
}
