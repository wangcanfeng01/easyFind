package com.wcf.hellohome.user.service.impl;

import com.wcf.hellohome.common.constant.ErrorMessage;
import com.wcf.hellohome.exception.PgSqlException;
import com.wcf.hellohome.exception.UserException;
import com.wcf.hellohome.user.dao.UserInfoMapper;
import com.wcf.hellohome.user.model.UserInfo;
import com.wcf.hellohome.user.service.WcfUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author WCF
 * @time 2018/1/28
 * @why 用户库信息服务实现类
 **/
@Service
@Log4j2
public class WcfUserServiceImpl implements WcfUserService {

    @Autowired
    private UserInfoMapper mapper;

    /**
     * @param userInfo
     * @return boolean
     * @note 添加新用户
     * @author WCF
     * @time 2018/6/12 23:12
     * @since v1.0
     **/
    @Override
    public boolean addNewUser(UserInfo userInfo) throws PgSqlException,UserException {
        //判断这个用户是否存在
        UserInfo user = null;
        try {
            user = mapper.getUserByName(userInfo.getUsername());
        } catch (Exception e) {
            log.error(ErrorMessage.SELECT_USER_ERROR, e);
            throw new PgSqlException(ErrorMessage.SELECT_USER_ERROR);
        }
        if (null != user) {
            log.error("该用户已经存在");
           throw new UserException(ErrorMessage.USER_ALREADY_EXIST);
        }
        int result = 0;
        try {
            result = mapper.insertUserInfo(userInfo);
        } catch (Exception e) {
            log.error(ErrorMessage.INSERT_USER_ERROR, e);
            throw new PgSqlException(ErrorMessage.INSERT_USER_ERROR);
        }
        if (result > 0) {
            return true;
        }
        return false;

    }


    /**
     * @param name
     * @return com.wcf.hellohome.user.model.UserInfo
     * @note 通过用户名获取用户信息
     * @author WCF
     * @time 2018/6/12 23:17
     * @since v1.0
     **/
    @Override
    public UserInfo getByUsername(String name) throws PgSqlException {
        try {
            return mapper.getUserByName(name);
        } catch (Exception e) {
            log.error(ErrorMessage.SELECT_USER_ERROR, e);
            throw new PgSqlException(ErrorMessage.SELECT_USER_ERROR);
        }
    }


    /**
     * @param id
     * @return com.wcf.hellohome.user.model.UserInfo
     * @note 通过用户id获取用户信息
     * @author WCF
     * @time 2018/6/12 23:18
     * @since v1.0
     **/
    @Override
    public UserInfo getUserByid(Integer id) throws PgSqlException {
        if (null != id) {
            UserInfo user = null;
            try {
                user = mapper.getUserById(id);
            } catch (Exception e) {
                log.error(ErrorMessage.SELECT_USER_ERROR, e);
                throw new PgSqlException(ErrorMessage.SELECT_USER_ERROR);
            }
            return user;
        }
        return null;
    }
}
