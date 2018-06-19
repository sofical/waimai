package com.ymhrj.ywjx.service.organization;

import com.ymhrj.ywjx.vo.Token;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * Created by zj on 2017/11/21.
 */
public interface TokenService {
    Token create(UUID userId);
    Token get(UUID tokenId);
    void checkFilter(HttpServletRequest request);
}
