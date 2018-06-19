package com.ymhrj.ywjx.service.organization;

import com.ymhrj.ywjx.controller.vo.PageData;
import com.ymhrj.ywjx.controller.vo.ResetPassVo;
import com.ymhrj.ywjx.controller.vo.UserCreateVo;
import com.ymhrj.ywjx.db.entity.User;
import com.ymhrj.ywjx.vo.Token;
import com.ymhrj.ywjx.vo.UserLogin;

import java.util.UUID;

/**
 * Created by Administrator on 2017/11/21.
 */
public interface UserService {
    /**
     * user init.
     * @return
     */
    User init();

    /**
     * get token.
     * @param userLogin
     * @return
     */
    Token login(UserLogin userLogin);

    /**
     * reset password.
     * @param resetPassVo
     */
    void resetPass(ResetPassVo resetPassVo);

    PageData<User> getUsers(Integer from, Integer limit);

    User create(UserCreateVo userCreateVo);

    void delete(UUID userId);

    void reset(UUID userId);
}
