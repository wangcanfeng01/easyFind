package com.wcf.hellohome.read.controller;

import com.wcf.hellohome.common.controller.BaseController;
import com.wcf.hellohome.common.utils.ObjectUtils;
import com.wcf.hellohome.exception.PgSqlException;
import com.wcf.hellohome.exception.WCFAuthenticationException;
import com.wcf.hellohome.read.model.WcfArticleInfo;
import com.wcf.hellohome.read.model.WcfCommentInfo;
import com.wcf.hellohome.read.model.WcfOperationLogInfo;
import com.wcf.hellohome.read.model.WcfStatisticInfo;
import com.wcf.hellohome.read.service.WcfOperationLogService;
import com.wcf.hellohome.read.service.WcfStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author WCF
 * @time 2018/5/1
 * @why 博客管理controller
 **/
@Controller
@RequestMapping("/read/admin")
public class ReadCenterController extends BaseController {
    @Autowired
    private WcfOperationLogService operationLogService;

    @Autowired
    private WcfStatisticService statisticService;

    /**
     * @param request
     * @return java.lang.String
     * @note 进入管理中心页面
     * @author WCF
     * @time 2018/6/15 19:17
     * @since v1.0
     **/
    @GetMapping(value = "/center")
    public String management(HttpServletRequest request) {
        String userName = getUserName(request);
        if (!"wcf".equals(userName)) {
            throw new WCFAuthenticationException("非管理员不能访问");
        }
        try {
            List<WcfCommentInfo> commentInfos = statisticService.getRecentComments(5);
            if (ObjectUtils.isEmpty(commentInfos)) {
                commentInfos = null;
            }
            List<WcfArticleInfo> articleInfos = statisticService.getRecentArticles(5);
            if (ObjectUtils.isEmpty(articleInfos)) {
                articleInfos = null;
            }
            //获取最近的10条操作日志
            List<WcfOperationLogInfo> operationLogInfos = operationLogService.getLogs(1, 5);
            if (ObjectUtils.isEmpty(operationLogInfos)) {
                operationLogInfos = null;
            }
            request.setAttribute("comments", commentInfos);
            request.setAttribute("articles", articleInfos);
            request.setAttribute("operations", operationLogInfos);
            WcfStatisticInfo statisticInfo = statisticService.getStatistics();
            request.setAttribute("statistic", statisticInfo);
        } catch (PgSqlException e) {
            return super.errorTransfer(request, e);
        }
        return readTransfer("center");
    }


}
