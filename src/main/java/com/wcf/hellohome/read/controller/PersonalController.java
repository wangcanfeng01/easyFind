package com.wcf.hellohome.read.controller;

import com.wcf.hellohome.common.constant.WCFConst;
import com.wcf.hellohome.common.controller.BaseController;
import com.wcf.hellohome.common.utils.ObjectUtils;
import com.wcf.hellohome.exception.PgSqlException;
import com.wcf.hellohome.read.model.WcfArticleInfo;
import com.wcf.hellohome.read.model.WcfCommentInfo;
import com.wcf.hellohome.read.model.WcfMetaInfo;
import com.wcf.hellohome.read.service.WcfMetaService;
import com.wcf.hellohome.read.service.WcfStatisticService;
import com.wcf.hellohome.user.model.UserInfo;
import com.wcf.hellohome.user.service.WcfUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author WCF
 * @time 2018/5/7
 * @why 个人主页
 **/
@Controller
@RequestMapping("/read/all")
public class PersonalController extends BaseController {

    @Autowired
    private WcfStatisticService statisticService;

    @Autowired
    private WcfMetaService metaService;

    @Autowired
    private WcfUserService userService;

    /**
     * @param request
     * @return java.lang.String
     * @note 系统设置
     * @author WCF
     * @time 2018/6/15 19:16
     * @since v1.0
     **/
    @GetMapping(value = {"/personal", ""})
    public String settings(HttpServletRequest request) {
        String name = getUserName(request);
        try {
            UserInfo userInfo = userService.getByUsername(name);
            if (ObjectUtils.isEmpty(userInfo)) {
                userInfo = userService.getByUsername("wcf");
            }
            request.setAttribute("person", userInfo);
            List<WcfArticleInfo> articles = statisticService.getRecentArticles(10);
            for (WcfArticleInfo articleInfo : articles) {
                String text = articleInfo.getText();
                if (text.length() > 150) {
                    text = text.substring(0, 150);
                }
                text += ".....";
                articleInfo.setText(text);
            }
            request.setAttribute("articles", articles);
            List<WcfCommentInfo> comments = statisticService.getRecentComments(10);
            request.setAttribute("comments", comments);
            List<WcfMetaInfo> categories = metaService.getMetasByType(WCFConst.Types.CATEGORY);
            request.setAttribute("categories", categories);
        } catch (PgSqlException e) {
            return super.errorTransfer(request, e);
        }
        return super.readTransfer("personal");
    }
}
