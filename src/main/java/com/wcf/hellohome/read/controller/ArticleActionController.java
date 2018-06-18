package com.wcf.hellohome.read.controller;

import com.github.pagehelper.PageInfo;
import com.wcf.hellohome.common.constant.ErrorMessage;
import com.wcf.hellohome.common.constant.WCFConst;
import com.wcf.hellohome.common.controller.BaseController;
import com.wcf.hellohome.common.response.BaseResponse;
import com.wcf.hellohome.common.utils.DateUtils;
import com.wcf.hellohome.common.utils.ObjectUtils;
import com.wcf.hellohome.common.utils.UuidUtils;
import com.wcf.hellohome.exception.PgSqlException;
import com.wcf.hellohome.exception.WCFAuthenticationException;
import com.wcf.hellohome.read.model.WcfArticleInfo;
import com.wcf.hellohome.read.model.WcfMetaInfo;
import com.wcf.hellohome.read.service.WcfArticleService;
import com.wcf.hellohome.read.service.WcfMetaService;
import com.wcf.hellohome.read.service.WcfOperationLogService;
import com.wcf.hellohome.user.model.UserInfo;
import com.wcf.hellohome.user.service.WcfUserService;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WCF
 * @time 2018/5/26
 * @why 阅读相关带有操作权限的动作
 **/
@Controller
@Log4j2
@RequestMapping("/read/admin")
public class ArticleActionController extends BaseController {
    @Autowired
    private WcfArticleService articleService;

    @Autowired
    private WcfOperationLogService operationLogService;

    @Autowired
    private WcfMetaService metaService;

    @Autowired
    private WcfUserService userService;

    /**
     * @param request
     * @return java.lang.String
     * @note 转到写文章页面
     * @author WCF
     * @time 2018/6/14 22:57
     * @since v1.0
     **/
    @GetMapping("/article/write")
    public String createArticle(HttpServletRequest request) {
        List<WcfMetaInfo> categories = null;
        try {
            categories = metaService.getMetasByType(WCFConst.Types.CATEGORY);
        } catch (PgSqlException e) {
            return super.errorTransfer(request, e);
        }
        request.setAttribute("categories", categories);
        return super.readTransfer("write");
    }

    /**
     * @param articleInfo
     * @param request
     * @return com.wcf.hellohome.common.response.BaseResponse
     * @note 写文章并发布
     * @author WCF
     * @time 2018/6/15 19:06
     * @since v1.0
     **/
    @PostMapping("/article/write")
    @ResponseBody
    public BaseResponse publishArticle(WcfArticleInfo articleInfo, HttpServletRequest request) {

        String userName = super.getUserName(request);
        try {
            if (WCFConst.NOT_LOGIN_USER.equals(userName)) {
                return BaseResponse.error("请登录后重试");
            } else {
                UserInfo userInfo = userService.getByUsername(userName);
                if (ObjectUtils.isEmpty(userInfo)) {
                    return BaseResponse.error("请登录后重试");
                } else {
                    articleInfo.setAuthor(userName);
                    articleInfo.setCommentsNum(0);
                    articleInfo.setTitleSimple(UuidUtils.generateShortUuid());
                    articleInfo.setModifyTime(DateUtils.getCurrentUnixTime());
                    articleInfo.setCreateTime(DateUtils.getCurrentUnixTime());
                    articleInfo.setHits(0);
                    if (ObjectUtils.isEmpty(articleInfo.getStatus())) {
                        articleInfo.setStatus("draft");
                    }
                }
            }
            articleService.insertArticle(articleInfo);
            //更新分类信息
            WcfMetaInfo category = metaService.getMetasByNameAndType(articleInfo.getCategories(), WCFConst.Types.CATEGORY);
            if (null != category) {
                category.setCount(category.getCount() + 1);
                category.setModifyTime(DateUtils.getCurrentUnixTime());
                metaService.updateMetaById(category);
            } else {
                metaService.insertMeta(articleInfo.getCategories(), 1, WCFConst.Types.CATEGORY, "");
            }
            //更新关键词信息，先判断存在与否，不存在则新增
            if (!ObjectUtils.isEmpty(articleInfo.getKeywords())) {
                String[] keywords = articleInfo.getKeywords().split(",");
                for (String key : keywords) {
                    WcfMetaInfo keyword = metaService.getMetasByNameAndType(key, WCFConst.Types.KEYWORD);
                    if (null != keyword) {
                        keyword.setCount(keyword.getCount() + 1);
                        keyword.setModifyTime(DateUtils.getCurrentUnixTime());
                        metaService.updateMetaById(keyword);
                    } else {
                        metaService.insertMeta(key, 1, WCFConst.Types.KEYWORD, "");
                    }
                }
            }
            operationLogService.insertLog("发布文章《" + articleInfo.getTitle() + "》",
                    request.getRemoteAddr(), getUserName(request));
        } catch (PgSqlException e) {
            return BaseResponse.error(e);
        }
        return BaseResponse.ok();
    }


    /**
     * @param request
     * @param simple
     * @return java.lang.String
     * @note 文章编辑
     * @author WCF
     * @time 2018/6/15 19:06
     * @since v1.0
     **/
    @GetMapping("/article/write/{simple}")
    public String editArticle(HttpServletRequest request, @PathVariable String simple) {

        String userName = getUserName(request);
        try {
            WcfArticleInfo articleInfo = articleService.getContent(simple);
            if (!articleInfo.getAuthor().equals(userName)) {
                throw new WCFAuthenticationException("不是作者不能修改文章");
            }
            request.setAttribute("article", articleInfo);
            List<WcfMetaInfo> categories = metaService.getMetasByType("category");
            request.setAttribute("categories", categories);
        } catch (PgSqlException e) {
            return super.errorTransfer(request, e);
        }
        return super.readTransfer("write");
    }

    /**
     * @param articleInfo
     * @param request
     * @return com.wcf.hellohome.common.response.BaseResponse
     * @note 修改文章
     * @author WCF
     * @time 2018/6/15 19:07
     * @since v1.0
     **/
    @PostMapping("/article/modify")
    @ResponseBody
    public BaseResponse modifyArticle(WcfArticleInfo articleInfo, HttpServletRequest request) {

        try {
            WcfArticleInfo source = articleService.getContentById(articleInfo.getId());
            //更新分类信息
            if (!source.getCategories().equals(articleInfo.getCategories())) {
                WcfMetaInfo categoryS = metaService.getMetasByNameAndType(source.getCategories(), WCFConst.Types.CATEGORY);
                if (null != categoryS) {
                    metaService.updateMetaCountById(categoryS.getCount() - 1, categoryS.getId());
                } else {
                    metaService.insertMeta(articleInfo.getCategories(), 1, WCFConst.Types.CATEGORY, "");
                }

                WcfMetaInfo categoryD = metaService.getMetasByNameAndType(articleInfo.getCategories(), WCFConst.Types.CATEGORY);
                if (null != categoryD) {
                    metaService.updateMetaCountById(categoryD.getCount() + 1, categoryD.getId());
                } else {
                    metaService.insertMeta(articleInfo.getCategories(), 1, WCFConst.Types.CATEGORY, "");
                }
            }
            //更新关键字，先判断和原文章存在几个不相同的关键字
            //-1表示原来有，现在不存在了，1表示新增
            Map<String, Integer> keyMap = new HashMap<>();
            if (!ObjectUtils.isEmpty(source.getKeywords())) {
                String[] sKeywords = source.getKeywords().split(",");
                for (String sKey : sKeywords) {
                    keyMap.put(sKey, -1);
                }
            }
            if (!ObjectUtils.isEmpty(articleInfo.getKeywords())) {
                String[] keywords = articleInfo.getKeywords().split(",");
                for (String sKey : keywords) {
                    if (null != keyMap.get(sKey)) {
                        keyMap.remove(sKey);
                    } else {
                        keyMap.put(sKey, 1);
                    }
                }
            }

            if (keyMap.size() != 0) {
                for (Map.Entry<String, Integer> entry : keyMap.entrySet()) {
                    WcfMetaInfo keyword = metaService.getMetasByNameAndType(entry.getKey(), WCFConst.Types.KEYWORD);
                    if (entry.getValue() == -1) {
                        if (null != keyword) {
                            metaService.updateMetaCountById(keyword.getCount() - 1, keyword.getId());
                        }
                    } else {
                        if (null != keyword) {
                            metaService.updateMetaCountById(keyword.getCount() + 1, keyword.getId());
                        } else {
                            metaService.insertMeta(entry.getKey(), 1, WCFConst.Types.KEYWORD, "");
                        }
                    }
                }
            }
            articleService.updateArticle(articleInfo);
            operationLogService.insertLog("编辑文章《" + articleInfo.getTitle() + "》",
                    request.getRemoteAddr(), getUserName(request));
        } catch (PgSqlException e) {
            return BaseResponse.error(e);
        }
        return BaseResponse.ok();
    }


    /**
     * @param limit
     * @param request
     * @return java.lang.String
     * @note 文章管理首页
     * @author WCF
     * @time 2018/6/15 19:07
     * @since v1.0
     **/
    @GetMapping("/management")
    public String articleManagement(@RequestParam(value = "limit", defaultValue = "10") int limit,
                                    HttpServletRequest request) {
        return articleManagementList(1, limit, request);
    }


    /**
     * @param page
     * @param limit
     * @param request
     * @return java.lang.String
     * @note 文章管理列表
     * @author WCF
     * @time 2018/6/15 19:07
     * @since v1.0
     **/
    @GetMapping("/management/{page}")
    public String articleManagementList(@PathVariable Integer page,
                                        @RequestParam(value = "limit", defaultValue = "10") int limit,
                                        HttpServletRequest request) {

        page = page < 0 || page > WCFConst.MAX_PAGE ? 0 : page;
        PageInfo<WcfArticleInfo> articles = null;
        try {
            articles = articleService.getContents(page, limit);
        } catch (PgSqlException e) {
            return super.errorTransfer(request, e);
        }
        request.setAttribute("articles", articles);
        return super.readTransfer("management");
    }


    /**
     *@note 通过id删除文章
     *@author WCF
     *@time 2018/6/15 19:07
     *@since v1.0
     * @param id
    * @param request
     *@return com.wcf.hellohome.common.response.BaseResponse
     **/
    @DeleteMapping("/article/delete/{id}")
    @ResponseBody
    public BaseResponse deleteArticle(@PathVariable("id") Integer id, HttpServletRequest request) {
        String userName = getUserName(request);
        WcfArticleInfo articleInfo = null;
        try {
            articleInfo = articleService.getContentById(id);
            if (!articleInfo.getAuthor().equals(userName)) {
                return BaseResponse.error("不是作者不能删除文章");
            }
            articleService.deleteArticleById(id);
            operationLogService.insertLog("删除文章",
                    request.getRemoteAddr(), getUserName(request));
        } catch (PgSqlException e) {
            return BaseResponse.error(e);
        }
        return BaseResponse.ok();
    }


}
