package com.wcf.hellohome.about.service.impl;

import com.wcf.hellohome.about.dao.VersionInfoMapper;
import com.wcf.hellohome.about.model.VersionInfo;
import com.wcf.hellohome.about.service.VersionService;
import com.wcf.hellohome.common.constant.ErrorMessage;
import com.wcf.hellohome.exception.PgSqlException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WCF
 * @time 2018/6/29
 * @why 功能：
 **/
@Log4j2
@Service
public class VersionServiceImpl implements VersionService {

    @Autowired
    VersionInfoMapper versionInfoMapper;

    /**
     * @param info
     * @return void
     * @note 插入版本信息
     * @author WCF
     * @time 2018/6/29 0:08
     * @since v1.0
     **/
    @Override
    public void insertVersion(VersionInfo info)throws PgSqlException {
        try {
            versionInfoMapper.insertVersion(info);
        } catch (Exception e) {
            log.error(ErrorMessage.INSERT_VERSION_ERROR,e);
            throw new PgSqlException(ErrorMessage.INSERT_VERSION_ERROR);
        }
    }

    /**
     * @param
     * @return java.util.List<com.wcf.hellohome.about.model.VersionInfo>
     * @note 查询版本信息
     * @author WCF
     * @time 2018/6/28 23:50
     * @since v1.0
     **/
    @Override
    public List<VersionInfo> getAllVersions() throws PgSqlException{
        try {
            return versionInfoMapper.getAllVersions();
        } catch (Exception e) {
            log.error(ErrorMessage.SELECT_VERSION_ERROR,e);
            throw new PgSqlException(ErrorMessage.SELECT_VERSION_ERROR);
        }
    }

    /**
     * @param info
     * @return void
     * @note 通过id更新版本信息
     * @author WCF
     * @time 2018/6/28 23:57
     * @since v1.0
     **/
    @Override
    public void updateVersionById(VersionInfo info)throws PgSqlException {
        try {
            versionInfoMapper.updateVersionById(info);
        } catch (Exception e) {
            log.error(ErrorMessage.UPDATE_VERSION_ERROR,e);
            throw new PgSqlException(ErrorMessage.UPDATE_VERSION_ERROR);
        }
    }
}
