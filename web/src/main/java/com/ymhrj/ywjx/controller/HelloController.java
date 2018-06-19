package com.ymhrj.ywjx.controller;

import com.ymhrj.ywjx.enums.RightEnum;
import com.ymhrj.ywjx.service.organization.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zj on 2017/11/21.
 */
@RestController
@RequestMapping("/api")
public class HelloController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "hello";
    }

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public String init() {
        userService.init();
        return "inited!";
    }
}
