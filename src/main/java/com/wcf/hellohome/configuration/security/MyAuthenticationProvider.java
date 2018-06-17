package com.wcf.hellohome.configuration.security;

import com.wcf.hellohome.common.utils.MD5Utils;
import com.wcf.hellohome.exception.WCFAuthenticationException;
import com.wcf.hellohome.read.service.WcfOperationLogService;
import com.wcf.hellohome.user.model.UserDetailsInfo;
import com.wcf.hellohome.user.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;


/**
 * @author WCF
 * @time 2018/2/7
 * @why 鉴权
 **/
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

    /**
     * 用户服务实现
     */
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    /**
     * @param authentication
     * @return org.springframework.security.core.Authentication
     * @note 验证用户信息
     * @author WCF
     * @time 2018/6/12 0:06
     * @since v1.0
     **/
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        UserDetailsInfo userInfo = (UserDetailsInfo) userDetailsServiceImpl.loadUserByUsername(username);
        Collection<GrantedAuthority> authorities = userInfo.getAuthorities();
        if (proveUser(username, password, authorities)) {
            //第一个参数为principal，用于后面提供用户信息
            Authentication auth = new UsernamePasswordAuthenticationToken(userInfo, password, authorities);
            return auth;
        } else {
            return null;
        }
    }

    /**
     * @param name
     * @param password
     * @param authorities
     * @return boolean
     * @note 验证用户名和密码
     * @author WCF
     * @time 2018/6/12 0:10
     * @since v1.0
     **/
    private boolean proveUser(String name, String password, Collection<? extends GrantedAuthority> authorities) {

        if (name == null || password == null) {
            throw new WCFAuthenticationException("密码错误");
        }
        UserDetails userInfo = userDetailsServiceImpl.loadUserByUsername(name);
        if (userInfo == null) {
            throw new WCFAuthenticationException("用户不存在");
        }
        //后期可以在这里加上角色验证
        password = MD5Utils.encode(password);
        if (userInfo.getPassword().equals(password)) {
            return true;
        }
        throw new WCFAuthenticationException("密码错误");
    }

    /**
     * @param aClass
     * @return boolean
     * @note 实现基类的方法
     * @author WCF
     * @time 2018/6/12 0:07
     * @since v1.0
     **/
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
