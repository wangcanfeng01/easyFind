package com.wcf.hellohome.configuration.commons;

import com.wcf.hellohome.common.utils.CommonUtils;
import com.wcf.hellohome.user.model.UserDetailsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author WCF
 * @time 2018/4/3
 * @why 自定义拦截器，添加一些页面调用的方法
 **/
@Component
public class BaseInterceptor implements HandlerInterceptor {
    /**
     * 通用方法
     */
    @Autowired
    CommonUtils utils;


    /**
     *@note 调用接口前拦截
     *@author WCF
     *@time 2018/6/11 23:40
     *@since v1.0
     * @param httpServletRequest
    * @param httpServletResponse
    * @param o
     *@return boolean
     **/
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        return true;
    }


    /**
     *@note 调用接口后拦截（controller后）
     *@author WCF
     *@time 2018/6/11 23:45
     *@since v1.0
     * @param request
    * @param response
    * @param o
    * @param modelAndView
     *@return void
     **/
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object o, ModelAndView modelAndView) throws Exception {
        request.setAttribute("utils",utils);
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) request.getUserPrincipal();
        if (!ObjectUtils.isEmpty(authenticationToken)) {
            UserDetailsInfo userDetailsInfo = (UserDetailsInfo) authenticationToken.getPrincipal();
            request.setAttribute("loginUser",userDetailsInfo.getNickname());
        }
    }

    /**
     *@note 整个接口调用完成之后，渲染servlet之后
     *@author WCF
     *@time 2018/6/11 23:46
     *@since v1.0
     * @param httpServletRequest
    * @param httpServletResponse
    * @param o
    * @param e
     *@return void
     **/
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
