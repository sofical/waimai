package com.ymhrj.ywjx.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ymhrj.ywjx.service.MenuService;
import com.ymhrj.ywjx.service.organization.RoleUserService;
import com.ymhrj.ywjx.utils.ContextUtils;
import com.ymhrj.ywjx.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2017/11/28.
 */
@Component
public class MenuServiceImpl implements MenuService {
    @Autowired
    private RoleUserService roleUserService;
    private final String MENU_FILE = "/menus.json";
    private List<String> roles;

    /**
     * 获取当前用户权限内的菜单
     * @return
     */
    @Override
    public JSONArray getMenus() {
        JSONArray allMenus = FileUtil.readConfig(this.MENU_FILE);
        this.roles = roleUserService.getRoles(ContextUtils.getUserId());
        return menusFilter(allMenus);
    }

    /**
     * 过滤菜单
     * @param menus
     * @return
     */
    private JSONArray menusFilter(JSONArray menus) {
        JSONArray newMenus = new JSONArray();
        for (Object menuObject : menus) {
            JSONObject menu = (JSONObject) menuObject;
            if (menu.containsKey("_child")) {
                JSONArray child = menu.getJSONArray("_child");
                JSONArray newChild = this.menusFilter(child);
                menu.put("_child", newChild);
            }
            if (roles.contains(menu.getString("code"))) {
                newMenus.add(menu);
            }
        }
        return newMenus;
    }

}
