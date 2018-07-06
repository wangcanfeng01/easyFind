package com.wcf.hellohome.read.service;

import com.wcf.hellohome.exception.PgSqlException;
import com.wcf.hellohome.read.model.SimpleStatisticInfo;
import com.wcf.hellohome.read.model.WcfArticleInfo;
import com.wcf.hellohome.read.model.WcfCommentInfo;
import com.wcf.hellohome.read.model.WcfStatisticInfo;

import java.util.List;

/**
 * @author WCF
 * @time 2018/5/1
 * @why write something
 **/
public interface WcfStatisticService {

    /**
     * @param limit
     * @return java.util.List<com.wcf.hellohome.read.model.WcfCommentInfo>
     * @note 查找最近的几条评论
     * @author WCF
     * @time 2018/6/14 22:23
     * @since v1.0
     **/
    List<WcfCommentInfo> getRecentComments(Integer limit) throws PgSqlException;

    /**
     * @param limit
     * @return java.util.List<com.wcf.hellohome.read.model.WcfArticleInfo>
     * @note 搜索最近的几篇文章
     * @author WCF
     * @time 2018/6/14 22:23
     * @since v1.0
     **/
    List<WcfArticleInfo> getRecentArticles(Integer limit) throws PgSqlException;


    /**
     * @param
     * @return com.wcf.hellohome.read.model.WcfStatisticInfo
     * @note 获取后台统计数据
     * @author WCF
     * @time 2018/6/14 22:23
     * @since v1.0
     **/
    WcfStatisticInfo getStatistics() throws PgSqlException;

    /**
     * @param
     * @return java.util.List<com.wcf.hellohome.read.model.SimpleStatisticInfo>
     * @note 获取文章最多的分类前几名
     * @author WCF
     * @time 2018/7/2 22:15
     * @since v1.0
     **/
    List<SimpleStatisticInfo> getMostCategoryFiles(Integer num) throws PgSqlException;

    /**
     * @param
     * @return java.util.List<com.wcf.hellohome.read.model.SimpleStatisticInfo>
     * @note 获取文章喜欢最多的前几名
     * @author WCF
     * @time 2018/7/2 22:15
     * @since v1.0
     **/
    List<SimpleStatisticInfo> getMostStars(Integer num) throws PgSqlException;

    /**
     * @param num
     * @return java.util.List<com.wcf.hellohome.read.model.SimpleStatisticInfo>
     * @note 获取字数最多的前几篇文章
     * @author WCF
     * @time 2018/7/3 22:10
     * @since v1.0
     **/
    List<SimpleStatisticInfo> getMostWords(Integer num) throws PgSqlException;

    /**
     * @param time
     * @return java.util.List<com.wcf.hellohome.read.model.SimpleStatisticInfo>
     * @note 获取近几个月的图片上传数量
     * @author WCF
     * @time 2018/7/3 22:10
     * @since v1.0
     **/
    long getPictures(Integer time) throws PgSqlException;

    /**
     * @param
     * @return java.util.List<com.wcf.hellohome.read.model.SimpleStatisticInfo>
     * @note 获取近期星星总数
     * @author WCF
     * @time 2018/7/2 22:15
     * @since v1.0
     **/
    long getStars(Integer time) throws PgSqlException;

    /**
     * @param
     * @return java.util.List<com.wcf.hellohome.read.model.SimpleStatisticInfo>
     * @note 获取近期评论总数
     * @author WCF
     * @time 2018/7/2 22:15
     * @since v1.0
     **/
    long getCommentNum(Integer time) throws PgSqlException;

    /**
     * @param
     * @return java.util.List<com.wcf.hellohome.read.model.SimpleStatisticInfo>
     * @note 获取近期浏览总数
     * @author WCF
     * @time 2018/7/2 22:15
     * @since v1.0
     **/
    long getHits(Integer time) throws PgSqlException;

    /**
     * @param
     * @return java.util.List<com.wcf.hellohome.read.model.SimpleStatisticInfo>
     * @note 获取近期文章字数总数
     * @author WCF
     * @time 2018/7/2 22:15
     * @since v1.0
     **/
    long getWords(Integer time) throws PgSqlException;

    /**
     * @param
     * @return java.util.List<com.wcf.hellohome.read.model.SimpleStatisticInfo>
     * @note 获取所有文章字数总数
     * @author WCF
     * @time 2018/7/2 22:15
     * @since v1.0
     **/
    long getTotalWords() throws PgSqlException;

    /**
     * @param
     * @return java.util.List<com.wcf.hellohome.read.model.SimpleStatisticInfo>
     * @note 获取近期发布的文章的分类情况
     * @author WCF
     * @time 2018/7/2 22:15
     * @since v1.0
     **/
    List<SimpleStatisticInfo> getRecentCategoryIndex(Integer time, Integer num)throws PgSqlException;

    /**
     * @param start
     * @param end
     * @return int
     * @note 查询一段时间内的文章数目
     * @author WCF
     * @time 2018/7/3 23:51
     * @since v1.0
     **/
    int getRecentFileNum(Integer start, Integer end);


}
