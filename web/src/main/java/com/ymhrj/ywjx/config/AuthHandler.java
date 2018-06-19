package com.ymhrj.ywjx.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ymhrj.ywjx.service.organization.TokenService;
import com.ymhrj.ywjx.utils.FileUtil;
import com.ymhrj.ywjx.utils.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2017/11/21.
 */
public class AuthHandler extends HandlerInterceptorAdapter {
    final private String apiSecurity = "/api_security.json";
    protected JSONArray securityPath;
    protected JSONArray unSecurityPath;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        loadFilterConfig();
        //需要授权
        if (this.isMatch(this.securityPath, uri) && !this.isMatch(this.unSecurityPath, uri)) {
            WebApplicationContext context = (WebApplicationContext) request.getSession().getServletContext().getAttribute("org.springframework.web.servlet.FrameworkServlet.CONTEXT.spring");
            if (null != context) {
                TokenService tokenService = context.getBean(TokenService.class);
                tokenService.checkFilter(request);
            } else {
                throw new RuntimeException("加载鉴权方法失败");
            }
        }
        return true;
    }

    protected void loadFilterConfig() {
        JSONObject settings = FileUtil.readConfig(apiSecurity);
        this.securityPath = (JSONArray) settings.get("security");
        this.unSecurityPath = (JSONArray) settings.get("unsecurity");
    }

    protected Boolean isMatch(JSONArray patterns, String uri) {
        PathMatcher matcher = new AntPathMatcher();
        for (Object pattern : patterns){
            if(matcher.match((String) pattern,uri)){
                return true;
            }
        }
        return false;
    }
}
