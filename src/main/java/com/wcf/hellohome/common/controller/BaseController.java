package com.wcf.hellohome.common.controller;

import com.wcf.hellohome.common.constant.WCFConst;
import com.wcf.hellohome.common.utils.ObjectUtils;
import com.wcf.hellohome.exception.PgSqlException;
import com.wcf.hellohome.user.model.UserDetailsInfo;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.servlet.http.HttpServletRequest;

/**
 * @author WCF
 * @time 2018/4/2
 * @why Controller基础功能，方便管理，统一替换
 **/
public class BaseController {
    /**
     * 阅读页面的路径
     */
    private String readTheme = "show/read/";

    /**
     * 错误页面
     */
    private String errorTheme = "error/500";


    /**
     * @param request
     * @return com.wcf.hellohome.user.model.UserDetailsInfo
     * @note 获取请求中的用户信息
     * @author WCF
     * @time 2018/6/10 19:38
     * @since v1.0
     **/
    public UserDetailsInfo getUserPrincipal(HttpServletRequest request) {
        return (UserDetailsInfo) request.getUserPrincipal();
    }


    /**
     * @param request
     * @return java.lang.String
     * @note 获取请求中的用户名称
     * @author WCF
     * @time 2018/6/10 19:58
     * @since v1.0
     **/
    public String getUserName(HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) request.getUserPrincipal();
        if (!ObjectUtils.isEmpty(authenticationToken)) {
            UserDetailsInfo userDetailsInfo = (UserDetailsInfo) authenticationToken.getPrincipal();
            if (null != userDetailsInfo && !ObjectUtils.isEmpty(userDetailsInfo.getNickname())) {
                return userDetailsInfo.getNickname();
            }
        }
        return WCFConst.NOT_LOGIN_USER;
    }

    /**
     * @param viewName
     * @return java.lang.String
     * @note 转向博客页
     * @author WCF
     * @time 2018/6/10 19:58
     * @since v1.0
     **/
    public String readTransfer(String viewName) {
        return readTheme + viewName;
    }

    /**
     * @param e
     * @return java.lang.String
     * @note 转向错误页面
     * @author WCF
     * @time 2018/6/14 22:49
     * @since v1.0
     **/
    public String errorTransfer(HttpServletRequest request, PgSqlException e) {
        request.setAttribute("error",e.getChinese());
        return errorTheme;
    }

    /**
     * @param
     * @return java.lang.String
     * @note 返回找不到资源页面
     * @author WCF
     * @time 2018/6/10 19:59
     * @since v1.0
     **/
    public String notFound() {
        return "error/404";
    }
}
