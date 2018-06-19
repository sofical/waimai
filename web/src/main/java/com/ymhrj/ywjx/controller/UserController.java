package com.ymhrj.ywjx.controller;

import com.ymhrj.ywjx.controller.vo.PageData;
import com.ymhrj.ywjx.controller.vo.UserCreateVo;
import com.ymhrj.ywjx.db.entity.User;
import com.ymhrj.ywjx.service.organization.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * UserController.
 *
 * @author zj.
 *         Created on 2018/2/22 0022.
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public PageData<User> getUsers(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                   @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        return this.userService.getUsers((page-1)*limit, limit);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public User create(@RequestBody UserCreateVo userCreateVo) {
        return this.userService.create(userCreateVo);
    }

    @RequestMapping(value = "/{user_id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("user_id") UUID userId) {
        this.userService.delete(userId);
    }

    @RequestMapping(value = "/{user_id}/actions/reset", method = RequestMethod.POST)
    public void reset(@PathVariable("user_id") UUID userId) {
        this.userService.reset(userId);
    }
}
