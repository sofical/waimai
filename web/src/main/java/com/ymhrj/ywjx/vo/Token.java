package com.ymhrj.ywjx.vo;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

/**
 * Created by zj on 2017/11/21.
 */
@Data
public class Token {
    UUID userId;
    UUID accessToken;
    Long expiredAt;
    String macKey;
}
