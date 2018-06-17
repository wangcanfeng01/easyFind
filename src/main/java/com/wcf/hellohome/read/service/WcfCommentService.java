package com.wcf.hellohome.read.service;

import com.github.pagehelper.PageInfo;
import com.wcf.hellohome.exception.PgSqlException;
import com.wcf.hellohome.read.model.WcfCommentInfo;

/**
 * @author WCF
 * @time 2018/4/15
 * @why 评论服务
 **/
public interface WcfCommentService {

    /**
     * @param id    文章的id
     * @param page
     * @param limit
     * @return com.github.pagehelper.PageInfo<com.wcf.hellohome.read.model.WcfCommentInfo>
     * @note 获取文章的相关评论
     * @author WCF
     * @time 2018/6/14 21:56
     * @since v1.0
     **/
    PageInfo<WcfCommentInfo> getComments(Integer id, int page, int limit) throws PgSqlException;

    /**
     * @param comment
     * @return void
     * @note 保存评论相关内容到数据库中
     * @author WCF
     * @time 2018/6/14 21:59
     * @since v1.0
     **/
    void insertComment(WcfCommentInfo comment) throws PgSqlException;

    /**
     * @param id
     * @return void
     * @note 通过主键删除评论内容
     * @author WCF
     * @time 2018/6/14 21:59
     * @since v1.0
     **/
    void deleteById(Integer id) throws PgSqlException;
}
