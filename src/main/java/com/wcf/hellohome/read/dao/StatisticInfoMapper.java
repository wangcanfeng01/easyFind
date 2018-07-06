package com.wcf.hellohome.read.dao;

import com.wcf.hellohome.read.model.SimpleStatisticInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author WCF
 * @time 2018/7/2
 * @why 功能：信息统计Mapper
 **/
@Mapper
public interface StatisticInfoMapper {

    /**
     * @param
     * @return java.util.List<com.wcf.hellohome.read.model.SimpleStatisticInfo>
     * @note 获取文章最多的分类前几名
     * @author WCF
     * @time 2018/7/2 22:15
     * @since v1.0
     **/
    @Select("SELECT name, count FROM info_metas WHERE type='category' ORDER BY count DESC LIMIT #{num}")
    List<SimpleStatisticInfo> getMostCategoryFiles(Integer num) throws Exception;

    /**
     * @param
     * @return java.util.List<com.wcf.hellohome.read.model.SimpleStatisticInfo>
     * @note 获取文章喜欢最多的前几名
     * @author WCF
     * @time 2018/7/2 22:15
     * @since v1.0
     **/
    @Select("SELECT title as name, stars as count FROM info_article WHERE delete_flag=0 ORDER BY stars DESC LIMIT #{num}")
    List<SimpleStatisticInfo> getMostStars(Integer num) throws Exception;

    /**
     * @param
     * @return java.util.List<com.wcf.hellohome.read.model.SimpleStatisticInfo>
     * @note 获取文章字数最多的前几名
     * @author WCF
     * @time 2018/7/2 22:15
     * @since v1.0
     **/
    @Select("SELECT title as name, CHAR_LENGTH(text) as count FROM info_article WHERE delete_flag=0 ORDER BY CHAR_LENGTH(text) DESC LIMIT #{num}")
    List<SimpleStatisticInfo> getMostWords(Integer num) throws Exception;

    /**
     * @param
     * @return java.util.List<com.wcf.hellohome.read.model.SimpleStatisticInfo>
     * @note 获取近期图片上传总数
     * @author WCF
     * @time 2018/7/2 22:15
     * @since v1.0
     **/
    @Select("SELECT count(1) as count FROM info_picture WHERE organisation_id >0 and create_time>#{time}")
    long getPictures(Integer time) throws Exception;

    /**
     * @param
     * @return java.util.List<com.wcf.hellohome.read.model.SimpleStatisticInfo>
     * @note 获取近期星星总数
     * @author WCF
     * @time 2018/7/2 22:15
     * @since v1.0
     **/
    @Select("SELECT SUM(stars) FROM info_article WHERE delete_flag=0 and modify_time>#{time}")
    long getStars(Integer time) throws Exception;

    /**
     * @param
     * @return java.util.List<com.wcf.hellohome.read.model.SimpleStatisticInfo>
     * @note 获取近期评论总数
     * @author WCF
     * @time 2018/7/2 22:15
     * @since v1.0
     **/
    @Select("SELECT SUM(comments_num) FROM info_article WHERE delete_flag=0 and modify_time>#{time}")
    long getCommentNum(Integer time) throws Exception;

    /**
     * @param
     * @return java.util.List<com.wcf.hellohome.read.model.SimpleStatisticInfo>
     * @note 获取近期浏览总数
     * @author WCF
     * @time 2018/7/2 22:15
     * @since v1.0
     **/
    @Select("SELECT SUM(hits) FROM info_article WHERE delete_flag=0 and modify_time>#{time}")
    long getHits(Integer time) throws Exception;

    /**
     * @param
     * @return java.util.List<com.wcf.hellohome.read.model.SimpleStatisticInfo>
     * @note 获取近期文章字数总数
     * @author WCF
     * @time 2018/7/2 22:15
     * @since v1.0
     **/
    @Select("SELECT SUM(CHAR_LENGTH(text)) FROM info_article WHERE delete_flag=0 and modify_time>#{time}")
    long getWords(Integer time) throws Exception;

    /**
     * @param
     * @return java.util.List<com.wcf.hellohome.read.model.SimpleStatisticInfo>
     * @note 获取所有文章字数总数
     * @author WCF
     * @time 2018/7/2 22:15
     * @since v1.0
     **/
    @Select("SELECT SUM(CHAR_LENGTH(text)) FROM info_article WHERE delete_flag=0 ")
    long getTotalWords() throws Exception;

    /**
     * @param
     * @return java.util.List<com.wcf.hellohome.read.model.SimpleStatisticInfo>
     * @note 获取近期发布的文章的分类情况
     * @author WCF
     * @time 2018/7/2 22:15
     * @since v1.0
     **/
    @Select("SELECT categories as name,COUNT(categories) as count  FROM info_article " +
            " WHERE modify_time>#{time} and delete_flag=0 and CHAR_LENGTH(categories)>0 " +
            " GROUP BY categories ORDER BY COUNT(categories) DESC LIMIT #{num}")
    List<SimpleStatisticInfo> getRecentCategoryIndex(@Param("time") Integer time, @Param("num") Integer num) throws Exception;

    /**
     * @param start
     * @param end
     * @return int
     * @note 查询一段时间内的文章数目
     * @author WCF
     * @time 2018/7/3 23:51
     * @since v1.0
     **/
    @Select("SELECT count(1) FROM info_article  WHERE modify_time>=#{start} and modify_time<=#{end}")
    int getRecentFileNum(@Param("start") Integer start, @Param("end") Integer end) throws Exception;

}
