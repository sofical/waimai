package com.ymhrj.ywjx.service.organization.impl;

import com.ymhrj.ywjx.cache.KVCache;
import com.ymhrj.ywjx.constant.TokenConstant;
import com.ymhrj.ywjx.service.organization.TokenService;
import com.ymhrj.ywjx.utils.ContextUtils;
import com.ymhrj.ywjx.utils.StringUtil;
import com.ymhrj.ywjx.vo.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * Created by zj on 2017/11/21.
 */
@Component
public class TokenServiceImpl implements TokenService {
    @Autowired
    private KVCache cache;

    @Override
    public Token create(UUID userId) {
        Token token = new Token();
        token.setUserId(userId);
        token.setAccessToken(UUID.randomUUID());
        token.setExpiredAt(System.currentTimeMillis() + TokenConstant.TTL);
        token.setMacKey(StringUtil.randChars(12));
        cache.set(this.getCacheKey(token.getAccessToken()), token);
        return token;
    }

    @Override
    public Token get(UUID tokenId) {
        return cache.get(this.getCacheKey(tokenId));
    }

    @Override
    public void checkFilter(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if(StringUtils.isEmpty(authorization)){
            authorization = request.getParameter("auth");
        }
        if (StringUtils.isEmpty(authorization)) {
            throw new RuntimeException("没有访问权限");
        }
        authorization = authorization.trim();
        if (!authorization.toLowerCase().startsWith("mac ")) {
            throw new RuntimeException("错误的授权类型");
        }
        String tokenString = authorization.substring(4, authorization.length()).trim();
        String[] token = tokenString.split("=");
        UUID tokenId = null;
        for (int i=0; i < token.length; i=i+2) {
            if ("id".equals(token[i].trim().toLowerCase())) {
                tokenId = UUID.fromString(token[i+1].trim().replaceAll("\"", ""));
            }
        }
        Token data = this.get(tokenId);
        if (null == data) {
            throw new RuntimeException("没有访问权限");
        }
        if (data.getExpiredAt() < System.currentTimeMillis()) {
            throw new RuntimeException("认证已过期");
        }
        data.setExpiredAt(System.currentTimeMillis() + TokenConstant.TTL);
        cache.set(this.getCacheKey(tokenId), data);
        ContextUtils.setUserId(data.getUserId());
    }

    private String getCacheKey(UUID accessToken) {
        return TokenConstant.NAME_FORMAT.replace("{token}", String.valueOf(accessToken));
    }
}
