package com.wcf.hellohome.read.controller;

import com.wcf.hellohome.common.controller.BaseController;
import com.wcf.hellohome.common.utils.DateUtils;
import com.wcf.hellohome.common.utils.ObjectUtils;
import com.wcf.hellohome.exception.PgSqlException;
import com.wcf.hellohome.exception.WCFAuthenticationException;
import com.wcf.hellohome.read.model.*;
import com.wcf.hellohome.read.service.WcfOperationLogService;
import com.wcf.hellohome.read.service.WcfStatisticService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WCF
 * @time 2018/5/1
 * @why 博客管理controller
 **/
@Controller
@RequestMapping("/read/admin")
@Log4j2
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
//        if (!"wcf".equals(userName)) {
//            throw new WCFAuthenticationException("非管理员不能访问");
//        }
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
        request.setAttribute("mostCategoryFiles", getMostCategoryFiles());
        request.setAttribute("mostStars", getMostStars());
        request.setAttribute("mostWords", getMostWords());
        request.setAttribute("recentPictures", getRecentPictures());
        request.setAttribute("recentWords", getRecentWords() / 1000);
        request.setAttribute("recentHits", getRecentHits() / 10);
        request.setAttribute("recentStars", getRecentStars());
        request.setAttribute("recentComments", getRecentCommentNum());
        request.setAttribute("recentCategoryIndex", getRecentCategoryIndex());
        request.setAttribute("recentFilesLine", getRecentFilesLine());
        return readTransfer("center");
    }

    /**
     * @param
     * @return java.util.List<com.wcf.hellohome.read.model.SimpleStatisticInfo>
     * @note 获取文章最多的分类前几名
     * @author WCF
     * @time 2018/7/2 22:15
     * @since v1.0
     **/
    private List<SimpleStatisticInfo> getMostCategoryFiles() {
        //取前7名
        Integer num = 7;
        List<SimpleStatisticInfo> list = new ArrayList<>(num);
        try {
            List<SimpleStatisticInfo> simpleStatisticInfos = statisticService.getMostCategoryFiles(num);
            int replenish = 0;
            if (ObjectUtils.isEmpty(simpleStatisticInfos)) {
                replenish = num;
            } else {
                for (SimpleStatisticInfo info : simpleStatisticInfos) {
                    list.add(info);
                }
                replenish = num - simpleStatisticInfos.size();
            }
            setReplenish(list, replenish);
        } catch (PgSqlException e) {
            setReplenish(list, num);
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
    private List<SimpleStatisticInfo> getMostStars() {
        //取前7名
        Integer num = 7;
        List<SimpleStatisticInfo> list = new ArrayList<>(num);
        try {
            List<SimpleStatisticInfo> simpleStatisticInfos = statisticService.getMostStars(num);
            int replenish = 0;
            if (ObjectUtils.isEmpty(simpleStatisticInfos)) {
                replenish = num;
            } else {
                for (SimpleStatisticInfo info : simpleStatisticInfos) {
                    list.add(info);
                }
                replenish = num - simpleStatisticInfos.size();
            }
            setReplenish(list, replenish);
        } catch (PgSqlException e) {
            setReplenish(list, num);
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
    private List<SimpleStatisticInfo> getMostWords() {
        //取前7名
        Integer num = 7;
        List<SimpleStatisticInfo> list = new ArrayList<>(num);
        try {
            List<SimpleStatisticInfo> simpleStatisticInfos = statisticService.getMostWords(num);
            int replenish = 0;
            if (ObjectUtils.isEmpty(simpleStatisticInfos)) {
                replenish = num;
            } else {
                for (SimpleStatisticInfo info : simpleStatisticInfos) {
                    list.add(info);
                }
                replenish = num - simpleStatisticInfos.size();
            }
            setReplenish(list, replenish);
        } catch (PgSqlException e) {
            setReplenish(list, num);
        }
        return list;
    }


    /**
     * @param
     * @return java.util.List<com.wcf.hellohome.read.model.SimpleStatisticInfo>
     * @note 获取近期发布的文章的分类情况
     * @author WCF
     * @time 2018/7/2 22:15
     * @since v1.0
     **/
    private List<SimpleStatisticInfo> getRecentCategoryIndex() {
        //取前7名
        Integer num = 7;
        List<SimpleStatisticInfo> list = new ArrayList<>(num);
        int time = DateUtils.toUnixTime(LocalDateTime.now().minusMonths(7));
        try {
            List<SimpleStatisticInfo> simpleStatisticInfos = statisticService.getRecentCategoryIndex(time, num);
            int replenish = 0;
            if (ObjectUtils.isEmpty(simpleStatisticInfos)) {
                replenish = num;
            } else {
                for (SimpleStatisticInfo info : simpleStatisticInfos) {
                    list.add(info);
                }
                replenish = num - simpleStatisticInfos.size();
            }
            setReplenish(list, replenish);
        } catch (PgSqlException e) {
            setReplenish(list, num);
        }
        return list;
    }

    private List<SimpleStatisticInfo> getRecentFilesLine() {
        Integer num = 7;
        List<SimpleStatisticInfo> list = new ArrayList<>(num);
        int num1;
        int num2;
        int num3;
        int num4;
        int num5;
        int num6;
        int num7;
        LocalDateTime date1 = LocalDateTime.now();
        LocalDateTime date2 = date1.minusMonths(1);
        LocalDateTime date3 = date2.minusMonths(1);
        LocalDateTime date4 = date3.minusMonths(1);
        LocalDateTime date5 = date4.minusMonths(1);
        LocalDateTime date6 = date5.minusMonths(1);
        LocalDateTime date7 = date6.minusMonths(1);
        num1 = statisticService.getRecentFileNum(DateUtils.thisMonthZeroUnix(date1), DateUtils.toUnixTime(date1));
        num2 = statisticService.getRecentFileNum(DateUtils.thisMonthZeroUnix(date2), DateUtils.thisMonthZeroUnix(date1));
        num3 = statisticService.getRecentFileNum(DateUtils.thisMonthZeroUnix(date3), DateUtils.thisMonthZeroUnix(date2));
        num4 = statisticService.getRecentFileNum(DateUtils.thisMonthZeroUnix(date4), DateUtils.thisMonthZeroUnix(date3));
        num5 = statisticService.getRecentFileNum(DateUtils.thisMonthZeroUnix(date5), DateUtils.thisMonthZeroUnix(date4));
        num6 = statisticService.getRecentFileNum(DateUtils.thisMonthZeroUnix(date6), DateUtils.thisMonthZeroUnix(date5));
        num7 = statisticService.getRecentFileNum(DateUtils.thisMonthZeroUnix(date7), DateUtils.thisMonthZeroUnix(date6));
        String m1 = DateUtils.getYearAndMonth(date1);
        String m2 = DateUtils.getYearAndMonth(date2);
        String m3 = DateUtils.getYearAndMonth(date3);
        String m4 = DateUtils.getYearAndMonth(date4);
        String m5 = DateUtils.getYearAndMonth(date5);
        String m6 = DateUtils.getYearAndMonth(date6);
        String m7 = DateUtils.getYearAndMonth(date7);
        SimpleStatisticInfo info1 = new SimpleStatisticInfo();
        info1.setName(m1);
        info1.setCount(num1);
        SimpleStatisticInfo info2 = new SimpleStatisticInfo();
        info2.setName(m2);
        info2.setCount(num2);
        SimpleStatisticInfo info3 = new SimpleStatisticInfo();
        info3.setName(m3);
        info3.setCount(num3);
        SimpleStatisticInfo info4 = new SimpleStatisticInfo();
        info4.setName(m4);
        info4.setCount(num4);
        SimpleStatisticInfo info5 = new SimpleStatisticInfo();
        info5.setName(m5);
        info5.setCount(num5);
        SimpleStatisticInfo info6 = new SimpleStatisticInfo();
        info6.setName(m6);
        info6.setCount(num6);
        SimpleStatisticInfo info7 = new SimpleStatisticInfo();
        info7.setName(m7);
        info7.setCount(num7);
        list.add(info1);
        list.add(info2);
        list.add(info3);
        list.add(info4);
        list.add(info5);
        list.add(info6);
        list.add(info7);
        return list;
    }

    /**
     * @param
     * @return long
     * @note 获取近期上传的图片数量
     * @author WCF
     * @time 2018/7/3 22:45
     * @since v1.0
     **/
    private long getRecentPictures() {
        int num = 7;
        int time = DateUtils.toUnixTime(LocalDateTime.now().minusMonths(7));
        long pictures = 0l;
        try {
            pictures = statisticService.getPictures(time);
        } catch (PgSqlException e) {
            pictures = 0l;
        }
        return pictures;
    }

    /**
     * @param
     * @return long
     * @note 获取近期文章字数总数
     * @author WCF
     * @time 2018/7/3 22:45
     * @since v1.0
     **/
    private long getRecentWords() {
        int num = 7;
        int time = DateUtils.toUnixTime(LocalDateTime.now().minusMonths(7));
        long words = 0l;
        try {
            words = statisticService.getWords(time);
        } catch (PgSqlException e) {
            words = 0l;
        }
        return words;
    }

    /**
     * @param
     * @return long
     * @note 获取近期评论总数
     * @author WCF
     * @time 2018/7/3 22:45
     * @since v1.0
     **/
    private long getRecentCommentNum() {
        int num = 7;
        int time = DateUtils.toUnixTime(LocalDateTime.now().minusMonths(7));
        long comments = 0l;
        try {
            comments = statisticService.getCommentNum(time);
        } catch (PgSqlException e) {
            comments = 0l;
        }
        return comments;
    }


    /**
     * @param
     * @return long
     * @note 获取近期文章星星总数
     * @author WCF
     * @time 2018/7/3 22:45
     * @since v1.0
     **/
    private long getRecentStars() {
        int num = 7;
        int time = DateUtils.toUnixTime(LocalDateTime.now().minusMonths(7));
        long stars = 0l;
        try {
            stars = statisticService.getStars(time);
        } catch (PgSqlException e) {
            stars = 0l;
        }
        return stars;
    }


    /**
     * @param
     * @return long
     * @note 获取近期文章字数总数
     * @author WCF
     * @time 2018/7/3 22:45
     * @since v1.0
     **/
    private long getRecentHits() {
        int num = 7;
        int time = DateUtils.toUnixTime(LocalDateTime.now().minusMonths(7));
        long hits = 0l;
        try {
            hits = statisticService.getHits(time);
        } catch (PgSqlException e) {
            hits = 0l;
        }
        return hits;
    }


    /**
     * @param replenish
     * @return java.util.List<com.wcf.hellohome.read.model.SimpleStatisticInfo>
     * @note 填充列表
     * @author WCF
     * @time 2018/7/2 23:01
     * @since v1.0
     **/
    private void setReplenish(List<SimpleStatisticInfo> list, Integer replenish) {
        for (int i = 0; i < replenish; i++) {
            SimpleStatisticInfo info = new SimpleStatisticInfo();
            info.setName("未知");
            info.setCount(0);
            list.add(info);
        }
    }

}
