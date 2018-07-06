package com.wcf.hellohome.read.service;

import com.github.pagehelper.PageInfo;
import com.wcf.hellohome.exception.PgSqlException;
import com.wcf.hellohome.read.model.WcfArticleInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author WCF
 * @time 2018/4/2
 * @why 文章服务
 **/

public interface WcfArticleService {
    /**
     * @param p     当前页
     * @param limit 每页条数
     * @return com.github.pagehelper.PageInfo<com.wcf.hellohome.read.model.WcfArticleInfo>
     * @note 查询文章返回多条数据
     * @author WCF
     * @time 2018/6/14 21:53
     * @since v1.0
     **/
    PageInfo<WcfArticleInfo> getContents(Integer p, Integer limit) throws PgSqlException;

    /**
     * @param category
     * @param p
     * @param limit
     * @return com.github.pagehelper.PageInfo<com.wcf.hellohome.read.model.WcfArticleInfo>
     * @note 根据标签显示文章列表
     * @author WCF
     * @time 2018/6/14 21:53
     * @since v1.0
     **/
    PageInfo<WcfArticleInfo> getContentsBycategory(String category, Integer p, Integer limit) throws PgSqlException;

    /**
     * @param slug
     * @return com.wcf.hellohome.read.model.WcfArticleInfo
     * @note 根据slug获取文章
     * @author WCF
     * @time 2018/6/14 21:54
     * @since v1.0
     **/
    WcfArticleInfo getContent(String slug) throws PgSqlException;

    /**
     * @param id
     * @return com.wcf.hellohome.read.model.WcfArticleInfo
     * @note 根据id获取文章
     * @author WCF
     * @time 2018/6/14 21:54
     * @since v1.0
     **/
    WcfArticleInfo getContentById(Integer id) throws PgSqlException;

    /**
     * @param articleId
     * @param hits
     * @return void
     * @note 通过文章主键更新文章点击次数（被阅读数）
     * @author WCF
     * @time 2018/6/14 21:54
     * @since v1.0
     **/
    void updateHitsById(Integer articleId, Integer hits) throws PgSqlException;

    /**
     * @param id
     * @param stars
     * @return void
     * @note 更新文章点赞数
     * @author WCF
     * @time 2018/6/14 21:54
     * @since v1.0
     **/
    void updateStarsById(Integer id, Integer stars) throws PgSqlException;

    /**
     * @param id
     * @param cover
     * @return void
     * @note 更新文章封面
     * @author WCF
     * @time 2018/6/14 21:55
     * @since v1.0
     **/
    void updateArticleCover(Integer id, String cover) throws PgSqlException;


    /**
     * @param articleId
     * @return void
     * @note 通过文章id主键删除文章
     * @author WCF
     * @time 2018/6/14 21:55
     * @since v1.0
     **/
    void deleteArticleById(Integer articleId) throws PgSqlException;

    /**
     * @param id
     * @return void
     * @note 根据id删除文章(伪删除)
     * @author WCF
     * @time 2018/6/13 22:11
     * @since v1.0
     **/
    void deleteArticleById2(Integer id, Integer delete) throws PgSqlException;

    /**
     * @param categories
     * @return long
     * @note 通过分类类型查询文章点击次数
     * @author WCF
     * @time 2018/6/14 21:55
     * @since v1.0
     **/
    long countArticlesHitsByCategory(String categories) throws PgSqlException;

    /**
     * @param keywords
     * @return long
     * @note 通过关键词类型查询文章点击次数
     * @author WCF
     * @time 2018/6/14 21:55
     * @since v1.0
     **/
    long countArticlesHitsByTag(String keywords) throws PgSqlException;

    /**
     * @param articleInfo
     * @return int
     * @note 插入新的文章
     * @author WCF
     * @time 2018/6/14 21:55
     * @since v1.0
     **/
    int insertArticle(WcfArticleInfo articleInfo) throws PgSqlException;


    /**
     * @param articleInfo
     * @return int
     * @note 创建新的文章
     * @author WCF
     * @time 2018/6/13 22:12
     * @since v1.0
     **/
    void createArticle(WcfArticleInfo articleInfo) throws PgSqlException;

    /**
     * @param articleInfo
     * @return void
     * @note 更新文章
     * @author WCF
     * @time 2018/6/14 21:55
     * @since v1.0
     **/
    void updateArticle(WcfArticleInfo articleInfo) throws PgSqlException;


}
