package com.ymhrj.ywjx.controller;

import com.alibaba.fastjson.JSONArray;
import com.ymhrj.ywjx.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zj on 2017/11/27.
 */
@RestController
@RequestMapping("/api/v1/cfg")
public class CfgController {
    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "/menus", method = RequestMethod.GET)
    public JSONArray menus() {
        return menuService.getMenus();
    }
}
