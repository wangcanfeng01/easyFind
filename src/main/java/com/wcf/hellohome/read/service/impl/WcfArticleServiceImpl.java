package com.wcf.hellohome.read.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wcf.hellohome.common.constant.ErrorMessage;
import com.wcf.hellohome.exception.PgSqlException;
import com.wcf.hellohome.read.dao.WcfArticleInfoMapper;
import com.wcf.hellohome.read.model.WcfArticleInfo;
import com.wcf.hellohome.read.service.WcfArticleService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WCF
 * @time 2018/4/3
 * @why 文章相关业务类
 **/
@Log4j2
@Service
public class WcfArticleServiceImpl implements WcfArticleService {
    @Autowired
    WcfArticleInfoMapper articleInfoMapper;

    /**
     * @param p
     * @param limit
     * @return com.github.pagehelper.PageInfo<com.wcf.hellohome.read.model.WcfArticleInfo>
     * @note 分页查询文章
     * @author WCF
     * @time 2018/6/13 23:57
     * @since v1.0
     **/
    @Override
    public PageInfo<WcfArticleInfo> getContents(Integer p, Integer limit) throws PgSqlException {
        PageHelper.startPage(p, limit);
        List<WcfArticleInfo> list = null;
        try {
            list = articleInfoMapper.getAllArticleInfo();
        } catch (Exception e) {
            log.error(ErrorMessage.SELECT_ARTICLE_ERROR,e);
            throw new PgSqlException(ErrorMessage.SELECT_ARTICLE_ERROR);
        }
        PageInfo<WcfArticleInfo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * @param categories
     * @param p
     * @param limit
     * @return com.github.pagehelper.PageInfo<com.wcf.hellohome.read.model.WcfArticleInfo>
     * @note 通过分类查询文章列表
     * @author WCF
     * @time 2018/6/13 23:58
     * @since v1.0
     **/
    @Override
    public PageInfo<WcfArticleInfo> getContentsBycategory(String categories, Integer p, Integer limit) throws PgSqlException {
        PageHelper.startPage(p, limit);
        List<WcfArticleInfo> list = null;
        try {
            list = articleInfoMapper.getAllArticleInfoByCategories(categories);
        } catch (Exception e) {
            log.error(ErrorMessage.SELECT_ARTICLE_ERROR,e);
            throw new PgSqlException(ErrorMessage.SELECT_ARTICLE_ERROR);
        }
        PageInfo<WcfArticleInfo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * @param slug
     * @return com.wcf.hellohome.read.model.WcfArticleInfo
     * @note 通过文章短查询文章
     * @author WCF
     * @time 2018/6/13 23:59
     * @since v1.0
     **/
    @Override
    public WcfArticleInfo getContent(String slug) throws PgSqlException {
        if (StringUtils.isNotBlank(slug)) {
            WcfArticleInfo article = null;
            try {
                article = articleInfoMapper.getArticleInfoBySlug(slug);
            } catch (Exception e) {
                log.error(ErrorMessage.SELECT_ARTICLE_ERROR,e);
                throw new PgSqlException(ErrorMessage.SELECT_ARTICLE_ERROR);
            }
            return article;
        }
        return null;
    }

    /**
     * @param id
     * @return com.wcf.hellohome.read.model.WcfArticleInfo
     * @note 根据id获取文章
     * @author WCF
     * @time 2018/6/14 0:01
     * @since v1.0
     **/
    @Override
    public WcfArticleInfo getContentById(Integer id) throws PgSqlException {
        try {
            return articleInfoMapper.getArticleInfoById(id);
        } catch (Exception e) {
            log.error(ErrorMessage.SELECT_ARTICLE_ERROR,e);
            throw new PgSqlException(ErrorMessage.SELECT_ARTICLE_ERROR);
        }
    }

    /**
     * @param articleId
     * @param hits
     * @return void
     * @note 更新文章被阅览次数
     * @author WCF
     * @time 2018/6/14 0:04
     * @since v1.0
     **/
    @Override
    public void updateHitsById(Integer articleId, Integer hits) throws PgSqlException {
        try {
            articleInfoMapper.updateHitsById(articleId, hits);
        } catch (Exception e) {
            log.error(ErrorMessage.UPDATE_ARTICLE_ERROR,e);
            throw new PgSqlException(ErrorMessage.UPDATE_ARTICLE_ERROR);
        }
    }

    /**
     * @param articleId
     * @param stars
     * @return void
     * @note 更新喜欢数通过文章id
     * @author WCF
     * @time 2018/6/14 0:04
     * @since v1.0
     **/
    @Override
    public void updateStarsById(Integer articleId, Integer stars) throws PgSqlException {
        try {
            articleInfoMapper.updateStarsById(articleId, stars);
        } catch (Exception e) {
            log.error(ErrorMessage.UPDATE_ARTICLE_ERROR,e);
            throw new PgSqlException(ErrorMessage.UPDATE_ARTICLE_ERROR);
        }
    }

    /**
     * @param articleId
     * @param cover
     * @return void
     * @note 通过文章id更新封面
     * @author WCF
     * @time 2018/6/14 0:08
     * @since v1.0
     **/
    @Override
    public void updateArticleCover(Integer articleId, String cover) throws PgSqlException {
        try {
            articleInfoMapper.updateArticleCover(articleId, cover);
        } catch (Exception e) {
            log.error(ErrorMessage.UPDATE_ARTICLE_ERROR,e);
            throw new PgSqlException(ErrorMessage.UPDATE_ARTICLE_ERROR);
        }
    }

    /**
     * @param articleId
     * @return void
     * @note 通过文章id删除文章
     * @author WCF
     * @time 2018/6/14 0:12
     * @since v1.0
     **/
    @Override
    public void deleteArticleById(Integer articleId) throws PgSqlException {
        try {
            articleInfoMapper.deleteArticleById(articleId);
        } catch (Exception e) {
            log.error(ErrorMessage.DELETE_ARTICLE_ERROR,e);
            throw new PgSqlException(ErrorMessage.DELETE_ARTICLE_ERROR);
        }
    }

    /**
     * @param id
     * @return void
     * @note 根据id删除文章(伪删除)
     * @author WCF
     * @time 2018/6/13 22:11
     * @since v1.0
     **/
    @Override
    public void deleteArticleById2(Integer id, Integer delete) throws PgSqlException {
        try {
            articleInfoMapper.deleteArticleById2(id,delete);
        } catch (Exception e) {
            log.error(ErrorMessage.DELETE_ARTICLE_ERROR,e);
            throw new PgSqlException(ErrorMessage.DELETE_ARTICLE_ERROR);
        }
    }

    /**
     *@note 统计对应分类的文章总数
     *@author WCF
     *@time 2018/6/14 0:13
     *@since v1.0
     * @param categories
     *@return long
     **/
    @Override
    public long countArticlesHitsByCategory(String categories)throws PgSqlException {
        try {
            return articleInfoMapper.countArticlesHitsByCategory(categories);
        } catch (Exception e) {
            log.error(ErrorMessage.SELECT_ARTICLE_ERROR,e);
            throw new PgSqlException(ErrorMessage.SELECT_ARTICLE_ERROR);
        }
    }

    /**
     *@note 根据关键词统计文章数
     *@author WCF
     *@time 2018/6/14 0:16
     *@since v1.0
     * @param keywords
     *@return long
     **/
    @Override
    public long countArticlesHitsByTag(String keywords)throws PgSqlException {
        try {
            return articleInfoMapper.countArticlesHitsByTag(keywords);
        } catch (Exception e) {
            log.error(ErrorMessage.SELECT_ARTICLE_ERROR,e);
            throw new PgSqlException(ErrorMessage.SELECT_ARTICLE_ERROR);
        }
    }

    /**
     *@note 插入新的文章
     *@author WCF
     *@time 2018/6/14 0:20
     *@since v1.0
     * @param articleInfo
     *@return int
     **/
    @Override
    public int insertArticle(WcfArticleInfo articleInfo) throws PgSqlException{
        try {
            return articleInfoMapper.insertArticle(articleInfo);
        } catch (Exception e) {
            log.error(ErrorMessage.INSERT_ARTICLE_ERROR,e);
            throw new PgSqlException(ErrorMessage.INSERT_ARTICLE_ERROR);
        }
    }

    /**
     * @param articleInfo
     * @return int
     * @note 创建新的文章
     * @author WCF
     * @time 2018/6/13 22:12
     * @since v1.0
     **/
    @Override
    public void createArticle(WcfArticleInfo articleInfo) throws PgSqlException {
        try {
             articleInfoMapper.createArticle(articleInfo);
        } catch (Exception e) {
            log.error(ErrorMessage.UPDATE_ARTICLE_ERROR,e);
            throw new PgSqlException(ErrorMessage.INSERT_ARTICLE_ERROR);
        }
    }

    /**
     *@note 更新文章
     *@author WCF
     *@time 2018/6/14 0:23
     *@since v1.0
     * @param articleInfo
     *@return void
     **/
    @Override
    public void updateArticle(WcfArticleInfo articleInfo)throws PgSqlException {
        try {
            articleInfoMapper.updateArticle(articleInfo);
        } catch (Exception e) {
            log.error(ErrorMessage.UPDATE_ARTICLE_ERROR,e);
            throw new PgSqlException(ErrorMessage.UPDATE_ARTICLE_ERROR);
        }
    }


}
