package com.wcf.hellohome.read.dao;

import com.wcf.hellohome.read.model.PictureUploadInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author WCF
 * @time 2018/6/18
 * @why 功能：图片上传数据库操作
 **/
@Mapper
public interface PictureUploadInfoMapper {

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
    @Insert("INSERT INTO info_picture(name, path, author, size, type, organisation_id,create_time, modify_time) " +
            " VALUES(#{name}, #{path}, #{author}, #{size}, #{type}, #{organisationId}, #{createTime}, #{modifyTime});")
    void insertPicture(PictureUploadInfo info) throws Exception;

    /**
     * @param
     * @return java.util.List<com.wcf.hellohome.read.model.PictureUploadInfo>
     * @note 查询图片信息
     * @author WCF
     * @time 2018/6/18 15:52
     * @since v1.0
     **/
    @Select("SELECT id, name, path, author, size, type, organisation_id as organisationId, create_time, modify_time" +
            "FROM info_picture")
    List<PictureUploadInfo> getPictureInfos() throws Exception;

    /**
     * @param
     * @return java.util.List<com.wcf.hellohome.read.model.PictureUploadInfo>
     * @note 查询指定图片信息
     * @author WCF
     * @time 2018/6/18 15:52
     * @since v1.0
     **/
    @Select("SELECT id, name, path, author, size, type, organisation_id as organisationId, create_time, modify_time " +
            "FROM info_picture WHERE organisation_id=#{organisationId} and type='cover'")
    PictureUploadInfo getPictureInfoByIdAndName(@Param("organisationId") Integer organisationId) throws Exception;

    /**
     * @param id
     * @return void
     * @note 通过id删除图片
     * @author WCF
     * @time 2018/6/18 15:53
     * @since v1.0
     **/
    @Delete("DELETE FROM info_picture WHERE id = #{id}")
    void deletePictureInfosById(Integer id) throws Exception;

    /**
     * @param info
     * @return void
     * @note 通过id修改图片信息
     * @author WCF
     * @time 2018/6/18 15:53
     * @since v1.0
     **/
    @Update("UPDATE info_picture SET name=#{name},path=#{path},author=#{author}, " +
            "size=#{size}, modify_time=#{modifyTime}" +
            " WHERE id = #{id}")
    void updatePictureInfosById(PictureUploadInfo info) throws Exception;

}
