package com.wcf.hellohome.user.service.impl;

import com.wcf.hellohome.common.constant.ErrorMessage;
import com.wcf.hellohome.exception.PgSqlException;
import com.wcf.hellohome.user.model.UserInfo;
import com.wcf.hellohome.user.service.WcfRelationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author WCF
 * @time 2018/1/29
 * @why 用户关系信息服务
 **/
@Log4j2
@Service
public class WcfRelationServiceImpl implements WcfRelationService {
    /**
     * 朋友列表
     */
    private final ConcurrentHashMap<String, List<String>> friendList;

    /**
     * 用户库信息服务
     */
    @Autowired
    private WcfUserServiceImpl WUserServiceImpl;

    /**
     *@note 构造器构造
     *@author WCF
     *@time 2018/6/12 23:09
     *@since v1.0
     * @param
     *@return
     **/
    public WcfRelationServiceImpl() {
        friendList = new ConcurrentHashMap<>();
    }

    /**
     * @author WCF
     * @time 2018/1/29 23:14
     * @Description 用于添加好友
     **/
    public boolean addFriend(String username, String frindName) throws PgSqlException{
        UserInfo info = null;
        try {
            info = WUserServiceImpl.getByUsername(frindName);
        } catch (PgSqlException e) {
            log.error(ErrorMessage.SELECT_USER_ERROR, e);
            throw new PgSqlException(ErrorMessage.SELECT_USER_ERROR);
        }
        if (null == info) {
            log.info("该用户不存在" + frindName);
            return false;
        }
        if (username.equals(frindName)) {
            log.info("不能添加自己为好友" + frindName);
            return false;
        }
        //建立双向关系线
        this.establishRelation(username, frindName);
        this.establishRelation(frindName, username);
        return true;
    }

    /**
     * @author WCF
     * @time 2018/1/29 23:36
     * @Description 建立关系，加入好友表
     **/
    private void establishRelation(String username, String friendName) {
        List<String> friends = friendList.get(username);
        //如果该用户没有好友，那么新建一个好友列表
        if (null == friends) {
            friends = new ArrayList<>();
        }
        friends.add(friendName);
        friendList.put(username, friends);
    }

    /**
     * @author WCF
     * @time 2018/1/29 23:43
     * @Description 获取好友列表信息
     **/
    public List<UserInfo> getFriendList(String username)throws PgSqlException {
        List<UserInfo> userInfos = new ArrayList<>();
        List<String> friends = friendList.get(username);
        if (friends != null) {
            for (String friend : friends) {
                UserInfo userInfo = null;
                try {
                    userInfo = WUserServiceImpl.getByUsername(friend);
                } catch (PgSqlException e) {
                    log.error(ErrorMessage.SELECT_USER_ERROR, e);
                    throw new PgSqlException(ErrorMessage.SELECT_USER_ERROR);
                }
                userInfos.add(userInfo);
            }
        }
        return userInfos;
    }
}
