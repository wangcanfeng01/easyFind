package com.wcf.hellohome.read.dao;

import com.wcf.hellohome.read.model.WcfCommentInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author WCF
 * @time 2018/4/15
 * @why write something
 **/
@Mapper
public interface WcfCommentInfoMapper {

    /**
     *@note 通过文章id寻找评论
     *@author WCF
     *@time 2018/6/13 22:19
     *@since v1.0
     * @param id
     *@return java.util.List<com.wcf.hellohome.read.model.WcfCommentInfo>
     **/
    @Select("SELECT id, article_id as articleId, create_time as createTime, author, author_id as authorId," +
            " owner_id as ownerId, url, ip, agent, text, type, status, parent FROM info_comment where article_id=#{id}" +
            " order by create_time DESC")
    List<WcfCommentInfo> getCommentsByArticleId(Integer id) throws Exception;


    /** 查找最近的几条评论
     *@note
     *@author WCF
     *@time 2018/6/13 22:19
     *@since v1.0
     * @param limit
     *@return java.util.List<com.wcf.hellohome.read.model.WcfCommentInfo>
     **/
    @Select("SELECT id, article_id as articleId, create_time as createTime, author, author_id as authorId," +
            " owner_id as ownerId, url, ip, agent, text, type, status, parent FROM info_comment" +
            " order by create_time DESC limit #{limit}")
    List<WcfCommentInfo> getRecentComments(Integer limit) throws Exception;


    /**
     *@note 统计总评论条数
     *@author WCF
     *@time 2018/6/13 22:19
     *@since v1.0
     * @param
     *@return long
     **/
    @Select("SELECT count(id) FROM info_comment;")
    long countComments()throws Exception;

    /**
     *@note 插入新的评论
     *@author WCF
     *@time 2018/6/13 22:19
     *@since v1.0
     * @param info
     *@return int
     **/
    @Insert("insert into info_comment(article_id,create_time,author,author_id,ip,text,type)" +
            " values(#{articleId},#{createTime},#{author},#{authorId},#{ip},#{text},#{type})")
    int insertComment(WcfCommentInfo info)throws  Exception;

    /**
     *@note 通过id删除文章
     *@author WCF
     *@time 2018/6/13 22:20
     *@since v1.0
     * @param id
     *@return int
     **/
    @Delete("DELETE FROM info_comment WHERE id = #{id}")
    int deleteById(Integer id)throws Exception;

}
