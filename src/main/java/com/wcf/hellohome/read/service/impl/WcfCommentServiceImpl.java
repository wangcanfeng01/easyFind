package com.wcf.hellohome.read.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wcf.hellohome.common.constant.ErrorMessage;
import com.wcf.hellohome.exception.PgSqlException;
import com.wcf.hellohome.read.dao.WcfCommentInfoMapper;
import com.wcf.hellohome.read.model.WcfCommentInfo;
import com.wcf.hellohome.read.service.WcfCommentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WCF
 * @time 2018/4/15
 * @why 文章评论相关信息
 **/
@Service
@Log4j2
public class WcfCommentServiceImpl implements WcfCommentService {
    @Autowired
    WcfCommentInfoMapper commentInfoMapper;

    /**
     * @param id    文章的id
     * @param page
     * @param limit
     * @return com.github.pagehelper.PageInfo<com.wcf.hellohome.read.model.WcfCommentInfo>
     * @note 获取文章的相关评论
     * @author WCF
     * @time 2018/6/14 0:24
     * @since v1.0
     **/
    @Override
    public PageInfo<WcfCommentInfo> getComments(Integer id, int page, int limit) throws PgSqlException {
        if (null != id) {
            PageHelper.startPage(page, limit);
            List<WcfCommentInfo> comments = null;
            try {
                comments = commentInfoMapper.getCommentsByArticleId(id);
            } catch (Exception e) {
                log.error(ErrorMessage.SELECT_COMMENT_ERROR,e);
                throw new PgSqlException(ErrorMessage.SELECT_COMMENT_ERROR);
            }
            PageInfo<WcfCommentInfo> pageComments = new PageInfo<>(comments);
            return pageComments;
        }
        return null;
    }

    /**
     * @param comment
     * @return void
     * @note 保存评论相关内容到数据库中
     * @author WCF
     * @time 2018/6/14 0:28
     * @since v1.0
     **/
    @Override
    public void insertComment(WcfCommentInfo comment) throws PgSqlException {
        try {
            commentInfoMapper.insertComment(comment);
        } catch (Exception e) {
            log.error(ErrorMessage.INSERT_COMMENT_ERROR,e);
            throw new PgSqlException(ErrorMessage.INSERT_COMMENT_ERROR);
        }

    }

    /**
     * @param id
     * @return void
     * @note 根据评论主键删除评论
     * @author WCF
     * @time 2018/6/14 0:30
     * @since v1.0
     **/
    @Override
    public void deleteById(Integer id) throws PgSqlException {
        try {
            commentInfoMapper.deleteById(id);
        } catch (Exception e) {
            log.error(ErrorMessage.DELETE_COMMENT_ERROR,e);
            throw new PgSqlException(ErrorMessage.DELETE_COMMENT_ERROR);
        }
    }

}
