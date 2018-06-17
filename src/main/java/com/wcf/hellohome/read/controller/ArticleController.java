package com.wcf.hellohome.read.controller;

import com.github.pagehelper.PageInfo;
import com.wcf.hellohome.common.constant.WCFConst;
import com.wcf.hellohome.common.controller.BaseController;
import com.wcf.hellohome.common.response.BaseResponse;
import com.wcf.hellohome.common.utils.ObjectUtils;
import com.wcf.hellohome.exception.PgSqlException;
import com.wcf.hellohome.read.model.WcfArticleInfo;
import com.wcf.hellohome.read.model.WcfCommentInfo;
import com.wcf.hellohome.read.model.WcfMetaInfo;
import com.wcf.hellohome.read.service.WcfArticleService;
import com.wcf.hellohome.read.service.WcfCommentService;
import com.wcf.hellohome.read.service.WcfMetaService;
import com.wcf.hellohome.read.service.WcfOperationLogService;
import com.wcf.hellohome.user.model.UserInfo;
import com.wcf.hellohome.user.service.WcfUserService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @author WCF
 * @time 2018/3/31
 * @why 文章无权限操作
 **/
@Controller
@Log4j2
@RequestMapping("/read/all")
public class ArticleController extends BaseController {

    @Autowired
    private WcfArticleService articleService;

    @Autowired
    private WcfCommentService commentService;

    @Autowired
    private WcfOperationLogService operationLogService;

    @Autowired
    private WcfMetaService metaService;

    @Autowired
    private WcfUserService userService;

    /**
     * @param request 请求参数
     * @param limit   分页限制
     * @return java.lang.String
     * @note 进入阅读列表默认页
     * @author WCF
     * @time 2018/6/15 19:10
     * @since v1.0
     **/
    @GetMapping("/list")
    public String read(HttpServletRequest request, @RequestParam(value = "limit", defaultValue = "10") int limit) {
        List<WcfMetaInfo> categories = null;
        try {
            categories = metaService.getMetasByType("category");
        } catch (PgSqlException e) {
            return errorTransfer(request, e);
        }
        request.setAttribute("allCategories", categories);
        return this.read(request, 1, limit);
    }

    /**
     * @param request
     * @param category
     * @param limit
     * @return java.lang.String
     * @note 根据分类标签进入文章列表
     * @author WCF
     * @time 2018/6/15 19:11
     * @since v1.0
     **/
    @GetMapping("/list/{category}")
    public String readSubList(HttpServletRequest request,
                              @PathVariable("category") String category,
                              @RequestParam(value = "limit", defaultValue = "10") int limit) {
        List<WcfMetaInfo> categories = null;
        try {
            categories = metaService.getMetasByType("category");
        } catch (PgSqlException e) {
            return errorTransfer(request, e);
        }
        request.setAttribute("allCategories", categories);
        return this.read(request, 1, category, limit);
    }

    /**
     * @param request
     * @param p
     * @param limit
     * @return java.lang.String
     * @note 阅读分页
     * @author WCF
     * @time 2018/6/15 19:11
     * @since v1.0
     **/
    @GetMapping(value = "/page/{p}")
    public String read(HttpServletRequest request,
                       @PathVariable int p,
                       @RequestParam(value = "limit", defaultValue = "10") int limit) {
        p = p < 0 || p > WCFConst.MAX_PAGE ? 1 : p;
        PageInfo<WcfArticleInfo> articles = null;
        try {
            articles = articleService.getContents(p, limit);
        } catch (PgSqlException e) {
            return errorTransfer(request, e);
        }
        request.setAttribute("articles", articles);
        return this.readTransfer("readlist");
    }


    /**
     * @param request
     * @param p
     * @param category
     * @param limit
     * @return java.lang.String
     * @note 阅读分类分页
     * @author WCF
     * @time 2018/6/15 19:11
     * @since v1.0
     **/
    @GetMapping(value = "/page/{p}/{category}")
    public String read(HttpServletRequest request,
                       @PathVariable int p,
                       @PathVariable(value = "category", required = false) String category,
                       @RequestParam(value = "limit", defaultValue = "10") int limit) {
        p = p < 0 || p > WCFConst.MAX_PAGE ? 1 : p;
        PageInfo<WcfArticleInfo> articles = null;
        try {
            if (ObjectUtils.isEmpty(category)) {
                articles = articleService.getContents(p, limit);
            } else {
                articles = articleService.getContentsBycategory(category, p, limit);
            }
        } catch (PgSqlException e) {
            return errorTransfer(request, e);
        }
        request.setAttribute("cName", category);
        request.setAttribute("articles", articles);
        return this.readTransfer("readlist");
    }

    /**
     * @param request
     * @param simple
     * @return java.lang.String
     * @note 文章展示页面
     * @author WCF
     * @time 2018/6/15 19:11
     * @since v1.0
     **/
    @GetMapping(value = "/article/{simple}")
    public String getArticle(HttpServletRequest request, @PathVariable String simple) {
        try {
            WcfArticleInfo article = articleService.getContent(simple);
            if (null == article) {
                return super.notFound();
            }
            request.setAttribute("article", article);
            String author = article.getAuthor();
            UserInfo userInfo = userService.getByUsername(author);
            request.setAttribute("author", userInfo);
            //文章评论处理
            articleComment(request, article);
            //更新文章点击次数
            updateArticleHits(request, article);
        } catch (PgSqlException e) {
            return super.errorTransfer(request, e);
        }
        return super.readTransfer("article");
    }

    /**
     * @param request
     * @param id
     * @param stars
     * @return com.wcf.hellohome.common.response.BaseResponse
     * @note 更新喜欢
     * @author WCF
     * @time 2018/6/15 19:11
     * @since v1.0
     **/
    @PostMapping(value = "/article/star")
    @ResponseBody
    public BaseResponse ArticleStar(HttpServletRequest request,
                                    @RequestParam("id") Integer id,
                                    @RequestParam("stars") Integer stars) {
        stars += 1;
        try {
            articleService.updateStarsById(id, stars);
            operationLogService.insertLog("喜欢文章",
                    request.getRemoteAddr(), getUserName(request));
        } catch (PgSqlException e) {
            return BaseResponse.error(e);
        }
        return BaseResponse.ok(stars.toString());
    }

    /**
     * @param request
     * @param articleInfo
     * @return void
     * @note 内部引用方法，获取文章相关的东西，如评论
     * @author WCF
     * @time 2018/6/15 19:12
     * @since v1.0
     **/
    private void articleComment(HttpServletRequest request, WcfArticleInfo articleInfo) throws PgSqlException {
        if (articleInfo.getAllowComment()) {
            //cp是评论页的页数参数
            String cp = request.getParameter("cp");
            if (StringUtils.isBlank(cp)) {
                cp = "1";
            }
            request.setAttribute("cp", cp);
            PageInfo<WcfCommentInfo> comments = commentService.getComments(articleInfo.getId(), Integer.parseInt(cp), 10);
            request.setAttribute("comments", comments);
        }
    }

    /**
     * @param request
     * @param article
     * @return void
     * @note 更新文章点击次数（被阅读数）
     * @author WCF
     * @time 2018/6/15 19:12
     * @since v1.0
     **/
    private void updateArticleHits(HttpServletRequest request, WcfArticleInfo article) throws PgSqlException {
        Integer hits = article.getHits();
        hits++;
        articleService.updateHitsById(article.getId(), hits);
        operationLogService.insertLog("阅读文章《" + article.getTitle() + "》",
                request.getRemoteAddr(), getUserName(request));
    }
}
