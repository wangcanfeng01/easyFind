package com.wcf.hellohome.user.service;

import com.wcf.hellohome.exception.PgSqlException;
import com.wcf.hellohome.user.model.UserInfo;

import java.util.List;

/**
 * @author WCF
 * @time 2018/4/19
 * @why write something 用户关系
 **/
public interface WcfRelationService {

    /**
     *@note 根据名字添加好友
     *@author WCF
     *@time 2018/6/10 19:18
     *@since v1.0
     * @param username 用户名
    * @param frindName
     *@return boolean
     **/
    boolean addFriend(String username, String frindName)throws PgSqlException;


    /**
     *@note 获取用户名称对应的好友列表
     *@author WCF
     *@time 2018/6/10 19:19
     *@since v1.0
     * @param username
     *@return java.util.List<com.wcf.hellohome.user.model.UserInfo>
     **/
     List<UserInfo> getFriendList(String username)throws PgSqlException;
}
