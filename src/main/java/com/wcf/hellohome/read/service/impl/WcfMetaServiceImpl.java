package com.wcf.hellohome.read.service.impl;

import com.wcf.hellohome.common.constant.ErrorMessage;
import com.wcf.hellohome.common.utils.DateUtils;
import com.wcf.hellohome.exception.PgSqlException;
import com.wcf.hellohome.read.dao.WcfMetaInfoMapper;
import com.wcf.hellohome.read.model.WcfMetaInfo;
import com.wcf.hellohome.read.service.WcfMetaService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WCF
 * @time 2018/5/3
 * @why meta服务
 **/
@Service
@Log4j2
public class WcfMetaServiceImpl implements WcfMetaService {
    @Autowired
    WcfMetaInfoMapper mapper;

    /**
     * @param name
     * @param type
     * @param description
     * @return void
     * @note 插入meta
     * @author WCF
     * @time 2018/6/14 0:44
     * @since v1.0
     **/
    @Override
    public void insertMeta(String name, String type, String description) throws PgSqlException {
        WcfMetaInfo info = new WcfMetaInfo();
        info.setName(name);
        info.setDescription(description);
        info.setType(type);
        info.setModifyTime(DateUtils.getCurrentUnixTime());
        info.setCount(0);
        info.setCreateTime(DateUtils.getCurrentUnixTime());
        try {
            mapper.insertMeta(info);
        } catch (Exception e) {
            log.error(ErrorMessage.INSERT_META_ERROR,e);
            throw new PgSqlException(ErrorMessage.INSERT_META_ERROR);
        }
    }

    /**
     * @param name
     * @param count
     * @param type
     * @param description
     * @return void
     * @note 插入新的meta
     * @author WCF
     * @time 2018/6/14 0:51
     * @since v1.0
     **/
    @Override
    public void insertMeta(String name, Integer count, String type, String description) throws PgSqlException {
        WcfMetaInfo info = new WcfMetaInfo();
        info.setName(name);
        info.setDescription(description);
        info.setType(type);
        info.setModifyTime(DateUtils.getCurrentUnixTime());
        info.setCount(count);
        info.setCreateTime(DateUtils.getCurrentUnixTime());
        try {
            mapper.insertMeta(info);
        } catch (Exception e) {
            log.error(ErrorMessage.INSERT_META_ERROR,e);
            throw new PgSqlException(ErrorMessage.INSERT_META_ERROR);
        }
    }

    /**
     * @param type
     * @return java.util.List<com.wcf.hellohome.read.model.WcfMetaInfo>
     * @note 通过类型获取标签
     * @author WCF
     * @time 2018/6/14 0:59
     * @since v1.0
     **/
    @Override
    public List<WcfMetaInfo> getMetasByType(String type) throws PgSqlException {
        try {
            return mapper.getMetasByType(type);
        } catch (Exception e) {
            log.error(ErrorMessage.SELECT_META_ERROR,e);
            throw new PgSqlException(ErrorMessage.SELECT_META_ERROR);
        }
    }

    /**
     * @param name
     * @param type
     * @return com.wcf.hellohome.read.model.WcfMetaInfo
     * @note 通过类型和名字获取指定标签
     * @author WCF
     * @time 2018/6/14 1:01
     * @since v1.0
     **/
    @Override
    public WcfMetaInfo getMetasByNameAndType(String name, String type) throws PgSqlException {
        try {
            return mapper.getMetasByNameAndType(name, type);
        } catch (Exception e) {
            log.error(ErrorMessage.SELECT_META_ERROR,e);
            throw new PgSqlException(ErrorMessage.SELECT_META_ERROR);
        }
    }

    /**
     * @param metaInfo
     * @return void
     * @note 通过id更新标签
     * @author WCF
     * @time 2018/6/14 1:02
     * @since v1.0
     **/
    @Override
    public void updateMetaById(WcfMetaInfo metaInfo) throws PgSqlException {
        try {
            mapper.updateMetaById(metaInfo);
        } catch (Exception e) {
            log.error(ErrorMessage.UPDATE_META_ERROR,e);
            throw new PgSqlException(ErrorMessage.UPDATE_META_ERROR);
        }
    }

    /**
     * @param name
     * @param description
     * @param id
     * @return void
     * @note 通过id更新描述和名称
     * @author WCF
     * @time 2018/6/14 1:06
     * @since v1.0
     **/
    @Override
    public void updateMetaInfoById(String name, String description, Integer id) throws PgSqlException {
        WcfMetaInfo info = new WcfMetaInfo();
        info.setName(name);
        info.setDescription(description);
        info.setModifyTime(DateUtils.getCurrentUnixTime());
        updateMetaById(info);
    }

    /**
     * @param id
     * @return void
     * @note 删除标签
     * @author WCF
     * @time 2018/6/14 1:10
     * @since v1.0
     **/
    @Override
    public void deleteMetaById(Integer id) throws PgSqlException {
        try {
            mapper.deleteMetaById(id);
        } catch (Exception e) {
            log.error(ErrorMessage.DELETE_META_ERROR,e);
            throw new PgSqlException(ErrorMessage.DELETE_META_ERROR);
        }
    }

    /**
     * @param count
     * @param id
     * @return void
     * @note 更新标签对应的统计值通过id
     * @author WCF
     * @time 2018/6/14 1:10
     * @since v1.0
     **/
    @Override
    public void updateMetaCountById(Integer count, Integer id) throws PgSqlException {
        Integer time = DateUtils.getCurrentUnixTime();
        try {
            mapper.updateMetaCountById(time, count, id);
        } catch (Exception e) {
            log.error(ErrorMessage.UPDATE_META_ERROR,e);
            throw new PgSqlException(ErrorMessage.UPDATE_META_ERROR);
        }
    }

}
