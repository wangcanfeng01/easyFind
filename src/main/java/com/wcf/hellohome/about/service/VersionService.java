package com.wcf.hellohome.about.service;

import com.wcf.hellohome.about.model.VersionInfo;
import com.wcf.hellohome.exception.PgSqlException;

import java.util.List;

/**
 * @author WCF
 * @time 2018/6/28
 * @why 功能：版本信息服务
 **/
public interface VersionService {

    /**
     * @param info
     * @return void
     * @note 插入版本信息
     * @author WCF
     * @time 2018/6/29 0:08
     * @since v1.0
     **/
    void insertVersion(VersionInfo info) throws PgSqlException;

    /**
     * @param
     * @return java.util.List<com.wcf.hellohome.about.model.VersionInfo>
     * @note 查询版本信息
     * @author WCF
     * @time 2018/6/28 23:50
     * @since v1.0
     **/
    List<VersionInfo> getAllVersions() throws PgSqlException;

    /**
     * @param info
     * @return void
     * @note 通过id更新版本信息
     * @author WCF
     * @time 2018/6/28 23:57
     * @since v1.0
     **/
    void updateVersionById(VersionInfo info) throws PgSqlException;
}
