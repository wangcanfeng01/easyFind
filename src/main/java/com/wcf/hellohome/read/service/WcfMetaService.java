package com.wcf.hellohome.read.service;

import com.wcf.hellohome.exception.PgSqlException;
import com.wcf.hellohome.read.model.WcfMetaInfo;

import java.util.List;

/**
 * @author WCF
 * @time 2018/5/3
 * @why meta服务
 **/
public interface WcfMetaService {

    /**
     * @param name
     * @param type
     * @param description
     * @return void
     * @note 插入新的meta
     * @author WCF
     * @time 2018/6/14 22:00
     * @since v1.0
     **/
    void insertMeta(String name, String type, String description) throws PgSqlException;

    /**
     * @param name
     * @param count
     * @param type
     * @param description
     * @return void
     * @note 插入新的meta
     * @author WCF
     * @time 2018/6/14 22:00
     * @since v1.0
     **/
    void insertMeta(String name, Integer count, String type, String description) throws PgSqlException;

    /**
     * @param type
     * @return java.util.List<com.wcf.hellohome.read.model.WcfMetaInfo>
     * @note 根据类型查询meta
     * @author WCF
     * @time 2018/6/14 22:01
     * @since v1.0
     **/
    List<WcfMetaInfo> getMetasByType(String type) throws PgSqlException;

    /**
     * @param name
     * @param type
     * @return com.wcf.hellohome.read.model.WcfMetaInfo
     * @note 通过名称和类型获取指定标签
     * @author WCF
     * @time 2018/6/14 22:02
     * @since v1.0
     **/
    WcfMetaInfo getMetasByNameAndType(String name, String type) throws PgSqlException;

    /**
     * @param metaInfo
     * @return void
     * @note 根据标签或是关键词更新统计值
     * @author WCF
     * @time 2018/6/14 22:05
     * @since v1.0
     **/
    void updateMetaById(WcfMetaInfo metaInfo) throws PgSqlException;

    /**
     * @param name
     * @param description
     * @param id
     * @return void
     * @note 更新标签名称，和描述
     * @author WCF
     * @time 2018/6/14 22:06
     * @since v1.0
     **/
    void updateMetaInfoById(String name, String description, Integer id) throws PgSqlException;

    /**
     * @param id
     * @return void
     * @note 根据id删除标签或是关键词
     * @author WCF
     * @time 2018/6/14 22:06
     * @since v1.0
     **/
    void deleteMetaById(Integer id) throws PgSqlException;

    /**
     * @param count
     * @param id
     * @return void
     * @note 根据id更新统计值
     * @author WCF
     * @time 2018/6/14 22:06
     * @since v1.0
     **/
    void updateMetaCountById(Integer count, Integer id) throws PgSqlException;

}
