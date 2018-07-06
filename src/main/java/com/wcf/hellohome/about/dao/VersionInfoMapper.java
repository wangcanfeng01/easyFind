package com.wcf.hellohome.about.dao;

import com.wcf.hellohome.about.model.VersionInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author WCF
 * @time 2018/6/28
 * @why 功能：版本对应sql操作
 **/
@Mapper
public interface VersionInfoMapper {

    /**
     * @param info
     * @return void
     * @note 插入版本信息
     * @author WCF
     * @time 2018/6/29 0:08
     * @since v1.0
     **/
    @Insert("INSERT INTO info_version(version, publish_time, description, author, modify_time)" +
            " VALUES(#{version},#{publishTime},#{description},#{author},#{modifyTime})")
    void insertVersion(VersionInfo info) throws Exception;

    /**
     * @param
     * @return java.util.List<com.wcf.hellohome.about.model.VersionInfo>
     * @note 查询版本信息
     * @author WCF
     * @time 2018/6/28 23:50
     * @since v1.0
     **/
    @Select("SELECT id, version, publish_time as publishTime, description, author, modify_time as modifyTime " +
            "FROM info_version")
    List<VersionInfo> getAllVersions() throws Exception;

    /**
     * @param info
     * @return void
     * @note 通过id更新版本信息
     * @author WCF
     * @time 2018/6/28 23:57
     * @since v1.0
     **/
    @Update("UPDATE info_version SET version=#{version},publish_time=#{publishTime},description=#{description}," +
            " author=#{author}, modify_time=#{modifyTime} WHERE id = #{id} ")
    void updateVersionById(VersionInfo info) throws Exception;
}
