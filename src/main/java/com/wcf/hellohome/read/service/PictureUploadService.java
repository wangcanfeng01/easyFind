package com.wcf.hellohome.read.service;

import com.wcf.hellohome.exception.PgSqlException;
import com.wcf.hellohome.read.model.PictureUploadInfo;

import java.util.List;

/**
 * @author WCF
 * @time 2018/6/18
 * @why 功能：图片上传服务
 **/
public interface PictureUploadService {

    /** 新建图片信息
     *@note
     *@author WCF
     *@time 2018/6/18 15:52
     *@since v1.0
     * @param info
     *@return void
     **/
    void insertPicture(PictureUploadInfo info)throws PgSqlException;

    /**
     *@note 查询图片信息
     *@author WCF
     *@time 2018/6/18 15:52
     *@since v1.0
     * @param
     *@return java.util.List<com.wcf.hellohome.read.model.PictureUploadInfo>
     **/
    List<PictureUploadInfo> getPictureInfos()throws PgSqlException;

    /**
     *@note 通过id删除图片
     *@author WCF
     *@time 2018/6/18 15:53
     *@since v1.0
     * @param id
     *@return void
     **/
    void deletePictureInfosById(Integer id)throws PgSqlException;

    /**
     *@note 通过id修改图片信息
     *@author WCF
     *@time 2018/6/18 15:53
     *@since v1.0
     * @param info
     *@return void
     **/
    void updatePictureInfosById(PictureUploadInfo info)throws PgSqlException;
}
