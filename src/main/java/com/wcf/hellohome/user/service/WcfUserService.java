package com.wcf.hellohome.user.service;

import com.wcf.hellohome.exception.PgSqlException;
import com.wcf.hellohome.exception.UserException;
import com.wcf.hellohome.user.model.UserInfo;

/**
 * @author WCF
 * @time 2018/4/19
 * @why write something
 **/
public interface WcfUserService {

    /**
     * @param userInfo
     * @return boolean
     * @note 添加新的用户
     * @author WCF
     * @time 2018/6/12 22:14
     * @since v1.0
     **/
    boolean addNewUser(UserInfo userInfo) throws PgSqlException,UserException;


    /**
     * @param name
     * @return com.wcf.hellohome.user.model.UserInfo
     * @note 通过用户名获取用户信息
     * @author WCF
     * @time 2018/6/12 22:15
     * @since v1.0
     **/
    UserInfo getByUsername(String name) throws PgSqlException;


    /**
     * @param id
     * @return com.wcf.hellohome.user.model.UserInfo
     * @note 通过评论中的用户id寻找用户
     * @author WCF
     * @time 2018/6/12 22:19
     * @since v1.0
     **/
    UserInfo getUserByid(Integer id) throws PgSqlException;

}
