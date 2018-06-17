package com.wcf.hellohome.read.dao;

import com.wcf.hellohome.read.model.WcfOperationLogInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author WCF
 * @time 2018/5/1
 * @why 操作日志
 **/
@Mapper
public interface WcfOperationLogInfoMapper {

    /**
     *@note 插入新的操作日志
     *@author WCF
     *@time 2018/6/13 22:31
     *@since v1.0
     * @param info
     *@return void
     **/
    @Insert("INSERT INTO info_operation_log(action, author, ip, create_time)" +
            "VALUES(#{action},#{author},#{ip},#{createTime})")
    void insertLog(WcfOperationLogInfo info)throws Exception;


    /**
     *@note 查询操作日志
     *@author WCF
     *@time 2018/6/13 22:32
     *@since v1.0
     * @param
     *@return java.util.List<com.wcf.hellohome.read.model.WcfOperationLogInfo>
     **/
    @Select("SELECT id, action, author, ip, create_time as createTime " +
            "FROM info_operation_log " +
            "ORDER BY create_time DESC")
    List<WcfOperationLogInfo> getLogsList()throws Exception;
}
