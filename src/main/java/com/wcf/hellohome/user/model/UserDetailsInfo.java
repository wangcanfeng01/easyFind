package com.wcf.hellohome.user.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author WCF
 * @time 2018/1/28
 * @why 业务相关用户信息
 **/
public class UserDetailsInfo extends User {
    private static final long serialVersionUID = 4063622468497962273L;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 头像图片路径
     */
    private String facePath;

    /**
     *@note 构造器业务相关用户信息
     *@author WCF
     *@time 2018/6/12 22:43
     *@since v1.0
     * @param username
    * @param password
    * @param authorities
     *@return
     **/
    public UserDetailsInfo(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    /**
     *@note 获取昵称
     *@author WCF
     *@time 2018/6/12 22:45
     *@since v1.0
     * @param
     *@return java.lang.String
     **/
    public String getNickname() {
        return nickname;
    }

    /**
     *@note 设置昵称
     *@author WCF
     *@time 2018/6/12 22:45
     *@since v1.0
     * @param nickname
     *@return void
     **/
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFacePath() {
        return facePath;
    }

    public void setFacePath(String facePath) {
        this.facePath = facePath;
    }
}
