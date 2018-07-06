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
     * @param slug
     * @return com.wcf.hellohome.read.model.WcfArticleInfo
     * @note 根据缩略名文章
     * @author WCF
     * @time 2018/6/13 21:45
     * @since v1.0
     **/
    @Select("SELECT id, title, title_simple as titleSimple, cover, create_time as createTime, modify_time as modifyTime, text," +
            " author, keywords, status, categories, hits, stars, comments_num as commentsNum, allow_comment as allowComment," +
            " allow_see as allowSee FROM info_article WHERE title_simple=#{slug} and delete_flag=0")
    WcfArticleInfo getArticleInfoBySlug(String slug) throws Exception;


    /**
     * @param id
     * @return com.wcf.hellohome.read.model.WcfArticleInfo
     * @note 根据id寻找文章
     * @author WCF
     * @time 2018/6/13 21:46
     * @since v1.0
     **/
    @Select("SELECT id, title, title_simple as titleSimple, cover, create_time as createTime, modify_time as modifyTime, text," +
            " author, keywords, status, categories, hits, stars, comments_num as commentsNum, allow_comment as allowComment," +
            " allow_see as allowSee FROM info_article WHERE id=#{id} and delete_flag=0")
    WcfArticleInfo getArticleInfoById(Integer id) throws Exception;

    /**
     * @param
     * @return java.util.List<com.wcf.hellohome.read.model.WcfArticleInfo>
     * @note 搜索所有文章
     * @author WCF
     * @time 2018/6/13 21:46
     * @since v1.0
     **/
    @Select("SELECT id, title, title_simple as titleSimple, cover, create_time as createTime, modify_time as modifyTime, text," +
            " author, keywords, status, categories, hits, stars, comments_num as commentsNum, allow_comment as allowComment," +
            " allow_see as allowSee  FROM info_article " +
            "WHERE delete_flag=0 ORDER BY create_time DESC")
    List<WcfArticleInfo> getAllArticleInfo() throws Exception;

    /**
     * @param categories
     * @return java.util.List<com.wcf.hellohome.read.model.WcfArticleInfo>
     * @note 搜索分类内的所有文章
     * @author WCF
     * @time 2018/6/13 21:47
     * @since v1.0
     **/
    @Select("SELECT id, title, title_simple as titleSimple, cover, create_time as createTime, modify_time as modifyTime, text," +
            " author, keywords, status, categories, hits, stars, comments_num as commentsNum, allow_comment as allowComment," +
            " allow_see as allowSee  FROM info_article WHERE categories=#{categories} and delete_flag=0 ORDER BY create_time DESC")
    List<WcfArticleInfo> getAllArticleInfoByCategories(String categories) throws Exception;

    /**
     * @param limit
     * @return java.util.List<com.wcf.hellohome.read.model.WcfArticleInfo>
     * @note 搜索最近的几篇文章
     * @author WCF
     * @time 2018/6/13 21:47
     * @since v1.0
     **/
    @Select("SELECT id, title, title_simple as titleSimple, cover, create_time as createTime, modify_time as modifyTime, text," +
            " author, keywords,  status, categories, hits, stars, comments_num as commentsNum, allow_comment as allowComment," +
            " allow_see as allowSee FROM info_article " +
            "WHERE delete_flag=0 ORDER BY create_time DESC LIMIT #{limit}")
    List<WcfArticleInfo> getRecentArticles(Integer limit) throws Exception;


    /**
     * @param
     * @return long
     * @note 统计文章篇数
     * @author WCF
     * @time 2018/6/13 21:47
     * @since v1.0
     **/
    @Select("SELECT count(id) FROM info_article WHERE delete_flag=0")
    long countArticles() throws Exception;

    /**
     * @param
     * @return int[]
     * @note 查询文章点击次数
     * @author WCF
     * @time 2018/6/13 21:47
     * @since v1.0
     **/
    @Select("SELECT hits FROM info_article WHERE delete_flag=0")
    int[] countArticlesHits() throws Exception;

    /**
     * @param categories
     * @return long
     * @note 通过分类类型查询文章点击次数
     * @author WCF
     * @time 2018/6/13 21:48
     * @since v1.0
     **/
    @Select("SELECT sum(hits)" +
            " FROM info_article " +
            " WHERE categories='#{categories}' AND delete_flag=0")
    long countArticlesHitsByCategory(String categories) throws Exception;

    /**
     * @param keywords
     * @return long
     * @note 通过关键词类型查询文章点击次数
     * @author WCF
     * @time 2018/6/13 21:48
     * @since v1.0
     **/
    @Select("SELECT sum(hits)" +
            " FROM info_article " +
            " WHERE keywords='#{keywords}' AND delete_flag=0")
    long countArticlesHitsByTag(String keywords) throws Exception;


    /**
     * @param id
     * @param hits
     * @return void
     * @note 通过id更新点击率
     * @author WCF
     * @time 2018/6/13 21:48
     * @since v1.0
     **/
    @Update("UPDATE info_article SET hits = #{hits} WHERE id = #{id}")
    void updateHitsById(@Param("id") Integer id, @Param("hits") Integer hits) throws Exception;

    /**
     * @param id
     * @param stars
     * @return void
     * @note 根据id更新喜欢数
     * @author WCF
     * @time 2018/6/13 21:49
     * @since v1.0
     **/
    @Update("UPDATE info_article SET stars = #{stars} WHERE id = #{id}")
    void updateStarsById(@Param("id") Integer id, @Param("stars") Integer stars) throws Exception;

    /**
     * @param id
     * @param cover
     * @return void
     * @note 更新文章封面
     * @author WCF
     * @time 2018/6/13 22:11
     * @since v1.0
     **/
    @Update("UPDATE info_article SET cover = #{cover} WHERE id = #{id}")
    void updateArticleCover(@Param("id") Integer id, @Param("cover") String cover) throws Exception;

    /**
     * @param id
     * @return void
     * @note 根据id删除文章
     * @author WCF
     * @time 2018/6/13 22:11
     * @since v1.0
     **/
    @Delete("DELETE FROM info_article WHERE id = #{id}")
    void deleteArticleById(@Param("id") Integer id) throws Exception;

    /**
     * @param id
     * @return void
     * @note 根据id删除文章(伪删除)
     * @author WCF
     * @time 2018/6/13 22:11
     * @since v1.0
     **/
    @Update("UPDATE info_article SET delete_flag = #{delete} WHERE id = #{id}")
    void deleteArticleById2(@Param("id") Integer id, @Param("delete") Integer delete) throws Exception;

    /**
     * @param articleInfo
     * @return int
     * @note 插入新的文章，主内容为空，只有骨架
     * @author WCF
     * @time 2018/6/13 22:12
     * @since v1.0
     **/
    @Insert("INSERT INTO info_article(title, title_simple, cover, create_time, modify_time, text,author, keywords,status," +
            " categories, hits,stars, comments_num,allow_comment, allow_see, delete_flag ) VALUES(#{title}, #{titleSimple}," +
            " #{cover}, #{createTime}, #{modifyTime},#{text},#{author},#{keywords},#{status}," +
            "#{categories},#{hits},#{stars},#{commentsNum},#{allowComment},#{allowSee},#{deleteFlag});")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertArticle(WcfArticleInfo articleInfo) throws Exception;

    /**
     * @param articleInfo
     * @return int
     * @note 创建新的文章
     * @author WCF
     * @time 2018/6/13 22:12
     * @since v1.0
     **/
    @Update("UPDATE info_article SET title=#{title}, text=#{text}, keywords=#{keywords}, status=#{status}," +
            "categories=#{categories}, allow_comment=#{allowComment},allow_see=#{allowSee} " +
            "WHERE id = #{id};")
    void createArticle(WcfArticleInfo articleInfo) throws Exception;

    /**
     * @param articleInfo
     * @return void
     * @note 更新文章内容
     * @author WCF
     * @time 2018/6/13 22:12
     * @since v1.0
     **/
    @UpdateProvider(type = com.wcf.hellohome.read.dao.provider.ArticleProvider.class, method = "updateArticle")
    void updateArticle(WcfArticleInfo articleInfo) throws Exception;
}
