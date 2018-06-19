package com.ymhrj.ywjx.controller;

import com.ymhrj.ywjx.service.organization.UserService;
import com.ymhrj.ywjx.vo.Token;
import com.ymhrj.ywjx.vo.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by zj on 2017/11/21.
 */
@RestController
@RequestMapping("/api/v1/tokens")
public class TokenController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Token token(@RequestBody @Valid UserLogin userLogin) {
        return userService.login(userLogin);
    }
}
