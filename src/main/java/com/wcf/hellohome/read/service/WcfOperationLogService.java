package com.wcf.hellohome.read.service;

import com.wcf.hellohome.exception.PgSqlException;
import com.wcf.hellohome.read.model.WcfOperationLogInfo;

import java.util.List;

/**
 * @author WCF
 * @time 2018/5/1
 * @why 操作日志信息
 **/
public interface WcfOperationLogService {

    /**
     * @param action
     * @param ip
     * @param author
     * @return void
     * @note 插入新的操作日志
     * @author WCF
     * @time 2018/6/14 22:15
     * @since v1.0
     **/
    void insertLog(String action, String ip, String author) throws PgSqlException;

    /**
     * @param page
     * @param limit
     * @return java.util.List<com.wcf.hellohome.read.model.WcfOperationLogInfo>
     * @note 获取日志信息
     * @author WCF
     * @time 2018/6/14 22:16
     * @since v1.0
     **/
    List<WcfOperationLogInfo> getLogs(int page, int limit) throws PgSqlException;
}
