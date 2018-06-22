package com.wcf.hellohome.read.service.impl;

import com.wcf.hellohome.common.constant.ErrorMessage;
import com.wcf.hellohome.exception.PgSqlException;
import com.wcf.hellohome.read.dao.PictureUploadInfoMapper;
import com.wcf.hellohome.read.model.PictureUploadInfo;
import com.wcf.hellohome.read.service.PictureUploadService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WCF
 * @time 2018/6/18
 * @why 功能：图片上传服务实现
 **/
@Service
@Log4j2
public class PictureUploadServiceImpl implements PictureUploadService {

    @Autowired
    private PictureUploadInfoMapper pictureUploadInfoMapper;
    /**
     * 新建图片信息
     *
     * @param info
     * @return void
     * @note
     * @author WCF
     * @time 2018/6/18 15:52
     * @since v1.0
     **/
    @Override
    public void insertPicture(PictureUploadInfo info) throws PgSqlException {
        try {
            pictureUploadInfoMapper.insertPicture(info);
        } catch (Exception e) {
            log.error(ErrorMessage.INSERT_PICTURE_ERROR,e);
            throw new PgSqlException(ErrorMessage.INSERT_PICTURE_ERROR);
        }
    }

    /**
     * @param
     * @return java.util.List<com.wcf.hellohome.read.model.PictureUploadInfo>
     * @note 查询图片信息
     * @author WCF
     * @time 2018/6/18 15:52
     * @since v1.0
     **/
    @Override
    public List<PictureUploadInfo> getPictureInfos() throws PgSqlException {
        try {
            return pictureUploadInfoMapper.getPictureInfos();
        } catch (Exception e) {
            log.error(ErrorMessage.SELECT_PICTURE_ERROR,e);
            throw new PgSqlException(ErrorMessage.SELECT_PICTURE_ERROR);
        }
    }

    /**
     * @param
     * @return java.util.List<com.wcf.hellohome.read.model.PictureUploadInfo>
     * @note 查询指定图片信息
     * @author WCF
     * @time 2018/6/18 15:52
     * @since v1.0
     **/
    @Override
    public PictureUploadInfo getCoverPictureByAid(Integer organisationId) throws PgSqlException {
        try {
            return pictureUploadInfoMapper.getPictureInfoByIdAndName(organisationId);
        } catch (Exception e) {
            log.error(ErrorMessage.SELECT_PICTURE_ERROR,e);
            throw new PgSqlException(ErrorMessage.SELECT_PICTURE_ERROR);
        }
    }

    /**
     * @param id
     * @return void
     * @note 通过id删除图片
     * @author WCF
     * @time 2018/6/18 15:53
     * @since v1.0
     **/
    @Override
    public void deletePictureInfosById(Integer id) throws PgSqlException {
        try {
            pictureUploadInfoMapper.deletePictureInfosById(id);
        } catch (Exception e) {
            log.error(ErrorMessage.DELETE_PICTURE_ERROR,e);
            throw new PgSqlException(ErrorMessage.DELETE_PICTURE_ERROR);
        }
    }

    /**
     * @param info
     * @return void
     * @note 通过id修改图片信息
     * @author WCF
     * @time 2018/6/18 15:53
     * @since v1.0
     **/
    @Override
    public void updatePictureInfosById(PictureUploadInfo info) throws PgSqlException {
        try {
            pictureUploadInfoMapper.updatePictureInfosById(info);
        } catch (Exception e) {
            log.error(ErrorMessage.UPDATE_PICTURE_ERROR,e);
            throw new PgSqlException(ErrorMessage.UPDATE_PICTURE_ERROR);
        }
    }
}
