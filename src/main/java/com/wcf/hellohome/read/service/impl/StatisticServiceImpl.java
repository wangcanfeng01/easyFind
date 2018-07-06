package com.wcf.hellohome.read.service.impl;

import com.wcf.hellohome.common.constant.ErrorMessage;
import com.wcf.hellohome.exception.PgSqlException;
import com.wcf.hellohome.read.dao.StatisticInfoMapper;
import com.wcf.hellohome.read.dao.WcfArticleInfoMapper;
import com.wcf.hellohome.read.dao.WcfCommentInfoMapper;
import com.wcf.hellohome.read.model.SimpleStatisticInfo;
import com.wcf.hellohome.read.model.WcfArticleInfo;
import com.wcf.hellohome.read.model.WcfCommentInfo;
import com.wcf.hellohome.read.model.WcfStatisticInfo;
import com.wcf.hellohome.read.service.WcfStatisticService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WCF
 * @time 2018/5/1
 * @why 统计服务
 **/
@Service
@Log4j2
public class StatisticServiceImpl implements WcfStatisticService {

    @Autowired
    WcfArticleInfoMapper articleInfoMapper;

    @Autowired
    WcfCommentInfoMapper commentInfoMapper;
    @Autowired
    StatisticInfoMapper statisticInfoMapper;


    /**
     * @param limit
     * @return java.util.List<com.wcf.hellohome.read.model.WcfCommentInfo>
     * @note 查找最近的几条评论
     * @author WCF
     * @time 2018/6/13 22:36
     * @since v1.0
     **/
    @Override
    public List<WcfCommentInfo> getRecentComments(Integer limit) throws PgSqlException {
        try {
            return commentInfoMapper.getRecentComments(limit);
        } catch (Exception e) {
            log.error(ErrorMessage.SELECT_COMMENT_ERROR, e);
            throw new PgSqlException(ErrorMessage.SELECT_COMMENT_ERROR);
        }
    }

    /**
     * @param limit
     * @return java.util.List<com.wcf.hellohome.read.model.WcfArticleInfo>
     * @note 搜索最近的几篇文章
     * @author WCF
     * @time 2018/6/13 23:56
     * @since v1.0
     **/
    @Override
    public List<WcfArticleInfo> getRecentArticles(Integer limit) throws PgSqlException {

        try {
            return articleInfoMapper.getRecentArticles(limit);
        } catch (Exception e) {
            log.error(ErrorMessage.SELECT_ARTICLE_ERROR, e);
            throw new PgSqlException(ErrorMessage.SELECT_ARTICLE_ERROR);
        }
    }

    /**
     * @param
     * @return com.wcf.hellohome.read.model.WcfStatisticInfo
     * @note 获取后台统计数据
     * @author WCF
     * @time 2018/6/13 23:56
     * @since v1.0
     **/
    @Override
    public WcfStatisticInfo getStatistics() throws PgSqlException {

        WcfStatisticInfo info = new WcfStatisticInfo();
        //评论总数
        long commentsNum = 0;
        try {
            commentsNum = commentInfoMapper.countComments();
        } catch (Exception e) {
            log.error(ErrorMessage.SELECT_COMMENT_ERROR, e);
            throw new PgSqlException(ErrorMessage.SELECT_COMMENT_ERROR);
        }
        info.setComments(commentsNum);
        //获取点击率总数
        int[] hits = new int[0];
        try {
            hits = articleInfoMapper.countArticlesHits();
        } catch (Exception e) {
            log.error(ErrorMessage.SELECT_ARTICLE_ERROR, e);
            throw new PgSqlException(ErrorMessage.SELECT_ARTICLE_ERROR);
        }
        long sum = 0;
        for (int hit : hits) {
            sum += hit;
        }
        info.setHits(sum);
        //查询文章总数
        long articlesNum = 0;
        try {
            articlesNum = articleInfoMapper.countArticles();
        } catch (Exception e) {
            log.error(ErrorMessage.SELECT_ARTICLE_ERROR, e);
            throw new PgSqlException(ErrorMessage.SELECT_ARTICLE_ERROR);
        }
        info.setArticles(articlesNum);

        //查询文章总字数
        long words = 0;
        try {
            words = statisticInfoMapper.getTotalWords();
        } catch (Exception e) {
            log.error(ErrorMessage.SELECT_ARTICLE_ERROR, e);
            throw new PgSqlException(ErrorMessage.SELECT_ARTICLE_ERROR);
        }
        info.setWords(words);
        return info;
    }

    /**
     * @param
     * @return java.util.List<com.wcf.hellohome.read.model.SimpleStatisticInfo>
     * @note 获取文章最多的分类前几名
     * @author WCF
     * @time 2018/7/2 22:15
     * @since v1.0
     **/
    @Override
    public List<SimpleStatisticInfo> getMostCategoryFiles(Integer num) throws PgSqlException {
        List<SimpleStatisticInfo> list = null;
        try {
            list = statisticInfoMapper.getMostCategoryFiles(num);
        } catch (Exception e) {
            log.error(ErrorMessage.SELECT_META_ERROR, e);
            throw new PgSqlException(ErrorMessage.SELECT_META_ERROR);
        }
        return list;
    }

    /**
     * @param
     * @return java.util.List<com.wcf.hellohome.read.model.SimpleStatisticInfo>
     * @note 获取文章喜欢最多的前几名
     * @author WCF
     * @time 2018/7/2 22:15
     * @since v1.0
     **/
    @Override
    public List<SimpleStatisticInfo> getMostStars(Integer num) throws PgSqlException {
        List<SimpleStatisticInfo> list = null;
        try {
            list = statisticInfoMapper.getMostStars(num);
        } catch (Exception e) {
            log.error(ErrorMessage.SELECT_ARTICLE_ERROR, e);
            throw new PgSqlException(ErrorMessage.SELECT_ARTICLE_ERROR);
        }
        return list;
    }

    /**
     * @param
     * @return java.util.List<com.wcf.hellohome.read.model.SimpleStatisticInfo>
     * @note 获取文章字数最多的前几名
     * @author WCF
     * @time 2018/7/2 22:15
     * @since v1.0
     **/
    @Override
    public List<SimpleStatisticInfo> getMostWords(Integer num) throws PgSqlException {
        List<SimpleStatisticInfo> list = null;
        try {
            list = statisticInfoMapper.getMostWords(num);
        } catch (Exception e) {
            log.error(ErrorMessage.SELECT_ARTICLE_ERROR, e);
            throw new PgSqlException(ErrorMessage.SELECT_ARTICLE_ERROR);
        }
        return list;
    }

    /**
     * @param time
     * @return java.util.List<com.wcf.hellohome.read.model.SimpleStatisticInfo>
     * @note 获取近几个月的图片上传数量
     * @author WCF
     * @time 2018/7/3 22:10
     * @since v1.0
     **/
    public long getPictures(Integer time) throws PgSqlException {
        long pictures = 0l;
        try {
            pictures = statisticInfoMapper.getPictures(time);
        } catch (Exception e) {
            log.error(ErrorMessage.SELECT_PICTURE_ERROR, e);
            throw new PgSqlException(ErrorMessage.SELECT_PICTURE_ERROR);
        }
        return pictures;
    }

    /**
     * @param
     * @return java.util.List<com.wcf.hellohome.read.model.SimpleStatisticInfo>
     * @note 获取近期星星总数
     * @author WCF
     * @time 2018/7/2 22:15
     * @since v1.0
     **/
    @Override
    public long getStars(Integer time) throws PgSqlException {
        long stars = 0l;
        try {
            stars = statisticInfoMapper.getStars(time);
        } catch (Exception e) {
            log.error(ErrorMessage.SELECT_ARTICLE_ERROR, e);
            throw new PgSqlException(ErrorMessage.SELECT_ARTICLE_ERROR);
        }
        return stars;
    }

    /**
     * @param
     * @return java.util.List<com.wcf.hellohome.read.model.SimpleStatisticInfo>
     * @note 获取近期评论总数
     * @author WCF
     * @time 2018/7/2 22:15
     * @since v1.0
     **/
    @Override
    public long getCommentNum(Integer time) throws PgSqlException {
        long commentsNum = 0l;
        try {
            commentsNum = statisticInfoMapper.getCommentNum(time);
        } catch (Exception e) {
            log.error(ErrorMessage.SELECT_ARTICLE_ERROR, e);
            throw new PgSqlException(ErrorMessage.SELECT_ARTICLE_ERROR);
        }
        return commentsNum;
    }

    /**
     * @param
     * @return java.util.List<com.wcf.hellohome.read.model.SimpleStatisticInfo>
     * @note 获取近期浏览总数
     * @author WCF
     * @time 2018/7/2 22:15
     * @since v1.0
     **/
    @Override
    public long getHits(Integer time) throws PgSqlException {
        long hits = 0l;
        try {
            hits = statisticInfoMapper.getHits(time);
        } catch (Exception e) {
            log.error(ErrorMessage.SELECT_ARTICLE_ERROR, e);
            throw new PgSqlException(ErrorMessage.SELECT_ARTICLE_ERROR);
        }
        return hits;
    }

    /**
     * @param
     * @return java.util.List<com.wcf.hellohome.read.model.SimpleStatisticInfo>
     * @note 获取近期文章字数总数
     * @author WCF
     * @time 2018/7/2 22:15
     * @since v1.0
     **/
    @Override
    public long getWords(Integer time) throws PgSqlException {
        long words = 0l;
        try {
            words = statisticInfoMapper.getWords(time);
        } catch (Exception e) {
            log.error(ErrorMessage.SELECT_ARTICLE_ERROR, e);
            throw new PgSqlException(ErrorMessage.SELECT_ARTICLE_ERROR);
        }
        return words;
    }

    /**
     * @param
     * @return java.util.List<com.wcf.hellohome.read.model.SimpleStatisticInfo>
     * @note 获取所有文章字数总数
     * @author WCF
     * @time 2018/7/2 22:15
     * @since v1.0
     **/
    @Override
    public long getTotalWords() throws PgSqlException {
        long words = 0l;
        try {
            words = statisticInfoMapper.getTotalWords();
        } catch (Exception e) {
            log.error(ErrorMessage.SELECT_ARTICLE_ERROR, e);
            throw new PgSqlException(ErrorMessage.SELECT_ARTICLE_ERROR);
        }
        return words;
    }

    /**
     * @param
     * @return java.util.List<com.wcf.hellohome.read.model.SimpleStatisticInfo>
     * @note 获取近期发布的文章的分类情况
     * @author WCF
     * @time 2018/7/2 22:15
     * @since v1.0
     **/
    @Override
    public List<SimpleStatisticInfo> getRecentCategoryIndex(Integer time, Integer num) throws PgSqlException {
        List<SimpleStatisticInfo> list = null;
        try {
            list = statisticInfoMapper.getRecentCategoryIndex(time, num);
        } catch (Exception e) {
            log.error(ErrorMessage.SELECT_ARTICLE_ERROR, e);
            throw new PgSqlException(ErrorMessage.SELECT_ARTICLE_ERROR);
        }
        return list;
    }

    @Override
    public int getRecentFileNum(Integer start, Integer end) {
        int num = 0;
        try {
            num = statisticInfoMapper.getRecentFileNum(start, end);
        } catch (Exception e) {
            log.error(ErrorMessage.SELECT_ARTICLE_ERROR, e);
            num = 0;
        }
        return num;
    }
}
