package com.ymhrj.ywjx.service.organization.impl;

import com.ymhrj.ywjx.controller.vo.PageData;
import com.ymhrj.ywjx.controller.vo.ResetPassVo;
import com.ymhrj.ywjx.controller.vo.UserCreateVo;
import com.ymhrj.ywjx.db.entity.User;
import com.ymhrj.ywjx.db.repository.UserRepository;
import com.ymhrj.ywjx.service.organization.RoleUserService;
import com.ymhrj.ywjx.service.organization.TokenService;
import com.ymhrj.ywjx.service.organization.UserService;
import com.ymhrj.ywjx.utils.ContextUtils;
import com.ymhrj.ywjx.utils.MD5Util;
import com.ymhrj.ywjx.vo.Token;
import com.ymhrj.ywjx.vo.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by zj on 2017/11/21.
 */
@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleUserService roleUserService;
    @Autowired
    private TokenService tokenService;

    private final String INIT_ACCOUNT = "sofical";
    private final String INIT_PASSWORD = "123456!@";
    private final String INIT_NAME = "超管";

    @Override
    public User init() {
        User user = userRepository.findOneByAccount(INIT_ACCOUNT);
        if (null == user) {
            UUID userId = UUID.randomUUID();
            roleUserService.init(userId);
            user = new User();
            user.setUserId(userId);
            user.setAccount(INIT_ACCOUNT);
            user.setPassword(MD5Util.encode(INIT_PASSWORD));
            user.setName(INIT_NAME);
            userRepository.save(user);
        }
        return user;
    }

    @Override
    public Token login(UserLogin userLogin) {
        User user = userRepository.findOneByAccount(userLogin.getAccount());
        if (null == user) {
            throw new RuntimeException("用户不存在");
        }
        if (!MD5Util.encode(userLogin.getPassword()).equals(user.getPassword())) {
            throw new RuntimeException("密码错误");
        }
        Token token = tokenService.create(user.getUserId());
        return token;
    }

    @Override
    public void resetPass(ResetPassVo resetPassVo) {
        UUID userId = ContextUtils.getUserId();
        User currentUser = this.userRepository.findOne(userId);
        if (!MD5Util.encode(resetPassVo.getOldPass()).equals(currentUser.getPassword())) {
            throw new RuntimeException("旧密码不正确");
        }
        currentUser.setPassword(MD5Util.encode(resetPassVo.getNewPass()));
        this.userRepository.save(currentUser);
    }

    @Override
    public PageData<User> getUsers(Integer from, Integer limit) {
        PageData<User> result = new PageData<>();
        result.setCount(this.userRepository.getUserCount());
        List<User> data = this.userRepository.getUserList(from, limit);
        result.setData(data);
        return result;
    }

    @Override
    public User create(UserCreateVo userCreateVo) {
        User existed = this.userRepository.findOneByAccount(userCreateVo.getAccount());
        if (null != existed) {
            throw new RuntimeException("相同的账号已存在");
        }
        User user = new User();
        user.setUserId(UUID.randomUUID());
        user.setAccount(userCreateVo.getAccount());
        user.setName(userCreateVo.getName());
        user.setPassword(MD5Util.encode(userCreateVo.getPassword()));
        return this.userRepository.save(user);
    }

    @Override
    public void delete(UUID userId) {
        if(userId.equals(ContextUtils.getUserId())) {
            throw new RuntimeException("不能删除自己的账号");
        }
        User user = this.userRepository.findOne(userId);
        if (null == user) {
            throw new RuntimeException("用户不存在或已被删除");
        }
        this.userRepository.delete(user);
    }

    @Override
    public void reset(UUID userId) {
        if(userId.equals(ContextUtils.getUserId())) {
            throw new RuntimeException("不能重置自己的账号");
        }
        User user = this.userRepository.findOne(userId);
        if (null == user) {
            throw new RuntimeException("用户不存在或已被删除");
        }
        user.setPassword(MD5Util.encode("123456"));
        this.userRepository.save(user);
    }
}
