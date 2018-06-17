package com.wcf.hellohome.read.service;

import com.wcf.hellohome.exception.PgSqlException;
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
}
