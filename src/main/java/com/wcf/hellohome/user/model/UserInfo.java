package com.wcf.hellohome.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * @author WCF
 * @time 2018/1/28
 * @why 用户信息bo
 **/
@Data
@NoArgsConstructor
@ToString
public class UserInfo {
    /**
     * 用户id
     */
    private Integer id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 用于存放头像路径
     */
    private String facePath;
    /**
     * 登录的时间
     */
    private String registerTime;

    /**
     * 用户角色
     */
    private String role;

    /**
     * 签名档
     */
    private String introduce;
    /**
     *@note 初始化用户信息
     *@author WCF
     *@time 2018/6/12 22:50
     *@since v1.0
     * @param username
    * @param password
     *@return
     **/
    public UserInfo(String username, String password) {
        this.username = username;
        this.password = password;
        this.facePath = "/image/face/face" + new Random().nextInt(10) + ".jpg";
        this.registerTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }
}

