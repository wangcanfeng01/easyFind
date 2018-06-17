package com.wcf.hellohome.user.service.impl;

import com.wcf.hellohome.common.constant.ErrorMessage;
import com.wcf.hellohome.exception.PgSqlException;
import com.wcf.hellohome.exception.WCFAuthenticationException;
import com.wcf.hellohome.user.model.UserDetailsInfo;
import com.wcf.hellohome.user.model.UserInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WCF
 * @time 2018/1/28
 * @why write something
 **/
@Service
@Log4j2
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * 超级用户,所有权限
     */
    private final static String administrator = "SUPER_USER";

    /**
     * 用户库信息服务
     */
    @Autowired
    private WcfUserServiceImpl WUserServiceImpl;

    /**
     * @param name
     * @return org.springframework.security.core.userdetails.UserDetails
     * @note 将数据库中bo转成vo信息
     * @author WCF
     * @time 2018/6/12 23:04
     * @since v1.0
     **/
    @Override
    public UserDetails loadUserByUsername(String name) {
        UserInfo info = null;
        try {
            info = WUserServiceImpl.getByUsername(name);
        } catch (PgSqlException e) {
            log.error(ErrorMessage.SELECT_USER_ERROR, e);
            throw new WCFAuthenticationException("用户不存在，请注册新账户");
        } catch (Exception e) {
            log.error("User not found", e);
            throw new WCFAuthenticationException("用户不存在，请注册新账户");
        }
        if (null == info) {
            throw new WCFAuthenticationException("用户不存在，请注册新账户");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        //增加管理员角色
        authorities.add(new SimpleGrantedAuthority(administrator));

        //用户认证
        UserDetailsInfo userDetailsInfo = new UserDetailsInfo(name, info.getPassword(), authorities);
        userDetailsInfo.setNickname(name);
        userDetailsInfo.setFacePath(info.getFacePath());
        return userDetailsInfo;
    }
}
