package com.wcf.hellohome.read.service.impl;

import com.wcf.hellohome.common.constant.ErrorMessage;
import com.wcf.hellohome.exception.PgSqlException;
import com.wcf.hellohome.read.dao.WcfArticleInfoMapper;
import com.wcf.hellohome.read.dao.WcfCommentInfoMapper;
import com.wcf.hellohome.read.model.WcfArticleInfo;
import com.wcf.hellohome.read.model.WcfCommentInfo;
import com.wcf.hellohome.read.model.WcfStatisticInfo;
import com.wcf.hellohome.read.service.WcfStatisticService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WCF
 * @time 2018/5/1
 * @why 统计服务
 **/
@Service
@Log4j2
public class StatisticServiceImpl implements WcfStatisticService{

    @Autowired
    WcfArticleInfoMapper articleInfoMapper;

    @Autowired
    WcfCommentInfoMapper commentInfoMapper;


    /**
     *@note 查找最近的几条评论
     *@author WCF
     *@time 2018/6/13 22:36
     *@since v1.0
     * @param limit
     *@return java.util.List<com.wcf.hellohome.read.model.WcfCommentInfo>
     **/
    @Override
    public List<WcfCommentInfo> getRecentComments(Integer limit) throws PgSqlException{
        try {
            return commentInfoMapper.getRecentComments(limit);
        } catch (Exception e) {
            log.error(ErrorMessage.SELECT_COMMENT_ERROR,e);
            throw new PgSqlException(ErrorMessage.SELECT_COMMENT_ERROR);
        }
    }

    /**
     *@note 搜索最近的几篇文章
     *@author WCF
     *@time 2018/6/13 23:56
     *@since v1.0
     * @param limit
     *@return java.util.List<com.wcf.hellohome.read.model.WcfArticleInfo>
     **/
    @Override
    public List<WcfArticleInfo> getRecentArticles(Integer limit)throws PgSqlException {

        try {
            return articleInfoMapper.getRecentArticles(limit);
        } catch (Exception e) {
            log.error(ErrorMessage.SELECT_ARTICLE_ERROR,e);
            throw new PgSqlException(ErrorMessage.SELECT_ARTICLE_ERROR);
        }
    }

    /**
     *@note 获取后台统计数据
     *@author WCF
     *@time 2018/6/13 23:56
     *@since v1.0
     * @param
     *@return com.wcf.hellohome.read.model.WcfStatisticInfo
     **/
    @Override
    public WcfStatisticInfo getStatistics() throws PgSqlException{

        WcfStatisticInfo info =new WcfStatisticInfo();
        //评论总数
        long commentsNum= 0;
        try {
            commentsNum = commentInfoMapper.countComments();
        } catch (Exception e) {
            log.error(ErrorMessage.SELECT_COMMENT_ERROR,e);
            throw new PgSqlException(ErrorMessage.SELECT_COMMENT_ERROR);
        }
        info.setComments(commentsNum);
        //获取点击率总数
        int[] hits= new int[0];
        try {
            hits = articleInfoMapper.countArticlesHits();
        } catch (Exception e) {
            log.error(ErrorMessage.SELECT_ARTICLE_ERROR,e);
            throw new PgSqlException(ErrorMessage.SELECT_ARTICLE_ERROR);
        }
        long sum=0;
        for(int hit:hits){
            sum+=hit;
        }
        info.setHits(sum);
        //查询文章总数
        long articlesNum= 0;
        try {
            articlesNum = articleInfoMapper.countArticles();
        } catch (Exception e) {
            log.error(ErrorMessage.SELECT_ARTICLE_ERROR,e);
            throw new PgSqlException(ErrorMessage.SELECT_ARTICLE_ERROR);
        }
        info.setArticles(articlesNum);
        return info;
    }
}
