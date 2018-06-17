package com.wcf.hellohome.read.service.impl;

import com.github.pagehelper.PageHelper;
import com.wcf.hellohome.common.constant.ErrorMessage;
import com.wcf.hellohome.common.utils.DateUtils;
import com.wcf.hellohome.exception.PgSqlException;
import com.wcf.hellohome.read.dao.WcfOperationLogInfoMapper;
import com.wcf.hellohome.read.model.WcfOperationLogInfo;
import com.wcf.hellohome.read.service.WcfOperationLogService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WCF
 * @time 2018/5/1
 * @why 操作日志信息服务实现类
 **/
@Service
@Log4j2
public class WcfOperationLogServiceImpl implements WcfOperationLogService {

    @Autowired
    WcfOperationLogInfoMapper mapper;

    /**
     * @param action
     * @param ip
     * @param author
     * @return void
     * @note 插入新的操作日志
     * @author WCF
     * @time 2018/6/14 1:15
     * @since v1.0
     **/
    @Override
    public void insertLog(String action, String ip, String author) throws PgSqlException {
        int unix = DateUtils.getCurrentUnixTime();
        WcfOperationLogInfo info = new WcfOperationLogInfo();
        info.setAuthor(author);
        info.setCreateTime(unix);
        info.setIp(ip);
        info.setAction(action);
        try {
            mapper.insertLog(info);
        } catch (Exception e) {
            log.error(ErrorMessage.INSERT_OPERATION_ERROR,e);
            throw new PgSqlException(ErrorMessage.INSERT_OPERATION_ERROR);
        }
    }

    /**
     * @param page
     * @param limit
     * @return java.util.List<com.wcf.hellohome.read.model.WcfOperationLogInfo>
     * @note 获取操作日志
     * @author WCF
     * @time 2018/6/14 1:19
     * @since v1.0
     **/
    @Override
    public List<WcfOperationLogInfo> getLogs(int page, int limit)throws PgSqlException {
        if (page <= 0) {
            page = 1;
        }
        if (limit <= 0) {
            limit = 10;
        }
        PageHelper.startPage(page, limit);
        try {
            return mapper.getLogsList();
        } catch (Exception e) {
            log.error(ErrorMessage.SELECT_OPERATION_ERROR,e);
            throw new PgSqlException(ErrorMessage.SELECT_OPERATION_ERROR);
        }
    }
}
