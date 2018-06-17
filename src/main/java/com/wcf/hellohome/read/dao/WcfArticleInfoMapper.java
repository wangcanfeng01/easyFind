package com.wcf.hellohome.read.dao;

import com.wcf.hellohome.read.model.WcfArticleInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author WCF
 * @time 2018/3/31
 * @why 文章信息处理
 **/
@Mapper
public interface WcfArticleInfoMapper {

    /**
     *@note 根据缩略名文章
     *@author WCF
     *@time 2018/6/13 21:45
     *@since v1.0
     * @param slug
     *@return com.wcf.hellohome.read.model.WcfArticleInfo
     **/
    @Select("SELECT id, title, title_simple as titleSimple, cover, create_time as createTime, modify_time as modifyTime, text," +
            " author, keywords, status, categories, hits, stars, comments_num as commentsNum, allow_comment as allowComment," +
            " allow_see as allowSee FROM info_article where title_simple=#{slug}")
    WcfArticleInfo getArticleInfoBySlug(String slug) throws Exception;


    /**
     *@note 根据id寻找文章
     *@author WCF
     *@time 2018/6/13 21:46
     *@since v1.0
     * @param id
     *@return com.wcf.hellohome.read.model.WcfArticleInfo
     **/
    @Select("SELECT id, title, title_simple as titleSimple, cover, create_time as createTime, modify_time as modifyTime, text," +
            " author, keywords, status, categories, hits, stars, comments_num as commentsNum, allow_comment as allowComment," +
            " allow_see as allowSee FROM info_article where id=#{id}")
    WcfArticleInfo getArticleInfoById(Integer id)throws Exception;

    /**
     *@note 搜索所有文章
     *@author WCF
     *@time 2018/6/13 21:46
     *@since v1.0
     * @param
     *@return java.util.List<com.wcf.hellohome.read.model.WcfArticleInfo>
     **/
    @Select("SELECT id, title, title_simple as titleSimple, cover, create_time as createTime, modify_time as modifyTime, text," +
            " author, keywords, status, categories, hits, stars, comments_num as commentsNum, allow_comment as allowComment," +
            " allow_see as allowSee  FROM info_article ORDER BY create_time DESC")
    List<WcfArticleInfo> getAllArticleInfo()throws Exception;

    /**
     *@note 搜索所有文章
     *@author WCF
     *@time 2018/6/13 21:47
     *@since v1.0
     * @param categories
     *@return java.util.List<com.wcf.hellohome.read.model.WcfArticleInfo>
     **/
    @Select("SELECT id, title, title_simple as titleSimple, cover, create_time as createTime, modify_time as modifyTime, text," +
            " author, keywords, status, categories, hits, stars, comments_num as commentsNum, allow_comment as allowComment," +
            " allow_see as allowSee  FROM info_article WHERE categories=#{categories} ORDER BY create_time DESC")
    List<WcfArticleInfo> getAllArticleInfoByCategories(String categories)throws Exception;

    /**
     *@note 搜索最近的几篇文章
     *@author WCF
     *@time 2018/6/13 21:47
     *@since v1.0
     * @param limit
     *@return java.util.List<com.wcf.hellohome.read.model.WcfArticleInfo>
     **/
    @Select("SELECT id, title, title_simple as titleSimple, cover, create_time as createTime, modify_time as modifyTime, text," +
            " author, keywords,  status, categories, hits, stars, comments_num as commentsNum, allow_comment as allowComment," +
            " allow_see as allowSee FROM info_article ORDER BY create_time DESC LIMIT #{limit}")
    List<WcfArticleInfo> getRecentArticles(Integer limit)throws Exception;


    /**
     *@note 统计文章篇数
     *@author WCF
     *@time 2018/6/13 21:47
     *@since v1.0
     * @param
     *@return long
     **/
    @Select("SELECT count(id) FROM info_article")
    long countArticles()throws Exception;

    /**
     *@note 查询文章点击次数
     *@author WCF
     *@time 2018/6/13 21:47
     *@since v1.0
     * @param
     *@return int[]
     **/
    @Select("SELECT hits FROM info_article")
    int[] countArticlesHits()throws Exception;

    /**
     *@note 通过分类类型查询文章点击次数
     *@author WCF
     *@time 2018/6/13 21:48
     *@since v1.0
     * @param categories
     *@return long
     **/
    @Select("SELECT sum(hits)" +
            " FROM info_article " +
            " WHERE categories='#{categories}'")
    long countArticlesHitsByCategory(String categories)throws Exception;

    /**
     *@note 通过关键词类型查询文章点击次数
     *@author WCF
     *@time 2018/6/13 21:48
     *@since v1.0
     * @param keywords
     *@return long
     **/
    @Select("SELECT sum(hits)" +
            " FROM info_article " +
            " WHERE keywords='#{keywords}'")
    long countArticlesHitsByTag(String keywords)throws Exception;


    /**
     *@note 通过id更新点击率
     *@author WCF
     *@time 2018/6/13 21:48
     *@since v1.0
     * @param id
    * @param hits
     *@return void
     **/
    @Update("UPDATE info_article SET hits = #{hits} WHERE id = #{id}")
    void updateHitsById(@Param("id") Integer id, @Param("hits") Integer hits)throws Exception;

    /**
     *@note 根据id更新喜欢数
     *@author WCF
     *@time 2018/6/13 21:49
     *@since v1.0
     * @param id
    * @param stars
     *@return void
     **/
    @Update("UPDATE info_article SET stars = #{stars} WHERE id = #{id}")
    void updateStarsById(@Param("id") Integer id, @Param("stars") Integer stars)throws Exception;

    /**
     *@note 更新文章封面
     *@author WCF
     *@time 2018/6/13 22:11
     *@since v1.0
     * @param id
    * @param cover
     *@return void
     **/
    @Update("UPDATE info_article SET cover = #{cover} WHERE id = #{id}")
    void updateArticleCover(@Param("id") Integer id, @Param("cover") String cover)throws Exception;

    /**
     *@note 根据id删除文章
     *@author WCF
     *@time 2018/6/13 22:11
     *@since v1.0
     * @param id
     *@return void
     **/
    @Delete("DELETE FROM info_article WHERE id = #{id}")
    void deleteArticleById(@Param("id") Integer id)throws Exception;

    /**
     *@note 插入新的文章
     *@author WCF
     *@time 2018/6/13 22:12
     *@since v1.0
     * @param articleInfo
     *@return int
     **/
    @Insert("INSERT INTO info_article(title, title_simple, cover, create_time, modify_time, text,author, keywords,status," +
            " categories, hits,stars, comments_num,allow_comment, allow_see ) VALUES(#{title},#{titleSimple},#{createTime}," +
            "#{modifyTime},#{text},#{author},#{keywords},#{status}," +
            "#{categories},#{hits},#{commentsNum},#{allowComment},#{allowSee});")
    int insertArticle(WcfArticleInfo articleInfo)throws Exception;

    /**
     *@note 更新文章内容
     *@author WCF
     *@time 2018/6/13 22:12
     *@since v1.0
     * @param articleInfo
     *@return void
     **/
    @UpdateProvider(type = com.wcf.hellohome.read.dao.provider.ArticleProvider.class, method = "updateArticle")
    void updateArticle(WcfArticleInfo articleInfo)throws Exception;
}
