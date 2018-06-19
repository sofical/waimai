package com.ymhrj.ywjx.controller;

import com.ymhrj.ywjx.controller.vo.ResetPassVo;
import com.ymhrj.ywjx.service.organization.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SafeContorller.
 *
 * @author zj.
 *         Created on 2018/2/21 0021.
 */
@RestController
@RequestMapping("/api/v1/safe")
public class SafeContorller {
    @Autowired
    private UserService userService;
    @RequestMapping("/reset")
    public void reset(@RequestBody ResetPassVo resetPassVo) {
        this.userService.resetPass(resetPassVo);
    }
}
