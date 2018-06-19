package com.ymhrj.ywjx.controller;

import com.ymhrj.ywjx.controller.vo.PageData;
import com.ymhrj.ywjx.controller.vo.RoleCreateVo;
import com.ymhrj.ywjx.controller.vo.RoleRightsVo;
import com.ymhrj.ywjx.controller.vo.RoleUserCreateVo;
import com.ymhrj.ywjx.db.entity.Role;
import com.ymhrj.ywjx.db.entity.RoleUser;
import com.ymhrj.ywjx.db.entity.User;
import com.ymhrj.ywjx.service.organization.RoleService;
import com.ymhrj.ywjx.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * RoleContorller.
 *
 * @author zj.
 *         Created on 2018/2/21 0021.
 */
@RestController
@RequestMapping("/api/v1/roles")
public class RoleContorller {
    @Autowired
    private RoleService roleService;
    @RequestMapping(value = "", method = RequestMethod.GET)
    public PageData<Role> getRoleList(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                      @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        return this.roleService.getRoleList((page-1) * limit, limit);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Role create(@RequestBody RoleCreateVo roleCreateVo) {
        return this.roleService.create(roleCreateVo);
    }

    @RequestMapping(value = "/{role_id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("role_id") UUID roleId) {
        this.roleService.delete(roleId);
    }

    @RequestMapping(value = "/{role_id}/users", method = RequestMethod.GET)
    public PageData<User> getRoleUser(@PathVariable("role_id") UUID roleId,
                                      @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                      @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        return this.roleService.getRoleUsers(roleId, (page-1) * limit, limit);
    }

    @RequestMapping(value = "/{role_id}/users", method = RequestMethod.POST)
    public RoleUser createRoleUser(@PathVariable("role_id") UUID roleId,
                                   @RequestBody RoleUserCreateVo roleUserCreateVo) {
        return this.roleService.createRoleUser(roleId, roleUserCreateVo);
    }

    @RequestMapping(value = "/{role_id}/users/{user_id}", method = RequestMethod.DELETE)
    public void deleteRoleUser(@PathVariable("role_id") UUID roleId, @PathVariable("user_id") UUID userId) {
        this.roleService.deleteRoleUser(roleId, userId);
    }

    @RequestMapping(value = "/rights", method = RequestMethod.GET)
    public Object getRights() {
        return FileUtil.readConfig("/rights.json");
    }

    @RequestMapping(value = "/{role_id}/actions/rights", method = RequestMethod.PUT)
    public void setRights(@PathVariable("role_id") UUID roleId, @RequestBody List<RoleRightsVo> rights) {
        this.roleService.setRights(roleId, rights);
    }
}
