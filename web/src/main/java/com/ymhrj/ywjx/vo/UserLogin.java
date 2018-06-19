package com.ymhrj.ywjx.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by zj on 2017/11/21.
 */
@Data
public class UserLogin {
    @NotBlank(message = "用户名不能为空")
    @Length(min = 5, max = 64, message = "用户名长度在{min}-{max}之前")
    String account;

    @NotBlank(message = "密码")
    @Length(min = 6, max = 18, message = "密码长度在{min}-{max}之前")
    String password;
}
