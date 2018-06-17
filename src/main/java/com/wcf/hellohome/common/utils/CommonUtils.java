package com.wcf.hellohome.common.utils;

import com.wcf.hellohome.exception.PgSqlException;
import com.wcf.hellohome.user.model.UserInfo;
import com.wcf.hellohome.user.service.WcfUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;

/**
 * @author WCF
 * @time 2018/4/3
 * @why 通用方法类
 **/
@Service
public class CommonUtils {

    /**
     * 用户个人信息服务
     */
    @Autowired
    WcfUserService userService;


    /**
     * @param simple
     * @return java.lang.String
     * @note 返回文章链接地址
     * @author WCF
     * @time 2018/6/10 20:15
     * @since v1.0
     **/
    public static String articleLink(String simple) {
        return "/read/all/article/" + simple;
    }

    /**
     * @param simple
     * @return java.lang.String
     * @note 返回文章编辑地址
     * @author WCF
     * @time 2018/6/10 20:16
     * @since v1.0
     **/
    public static String writeArticle(String simple) {
        return "/read/admin/article/write/" + simple;
    }

    /**
     * @param categories
     * @return java.lang.String
     * @note 显示文档分类信息
     * @author WCF
     * @time 2018/6/10 20:16
     * @since v1.0
     **/
    public static String showCategories(String categories) throws UnsupportedEncodingException {
        //isNotBlank能排除空格的情况
        if (StringUtils.isNotBlank(categories)) {
            String[] arr = categories.split(",");
            StringBuffer sb = new StringBuffer();
            for (String s : arr) {
                sb.append("<a href=\"/category/" + URLEncoder.encode(s, "UTF-8") + "\">" + s + "</a>");
            }
            return sb.toString();
        }
        return showCategories("默认分类");
    }


    /**
     * @param unixTime
     * @return java.lang.String
     * @note 格式化unix时间戳为日期字符串
     * @author WCF
     * @time 2018/6/10 20:17
     * @since v1.0
     **/
    public static String fmtDate(Integer unixTime) {
        return DateUtils.getTimeByUnixTime(unixTime);
    }


    /**
     * @param keywords
     * @return java.lang.String
     * @note 显示文档标签
     * @author WCF
     * @time 2018/6/10 20:18
     * @since v1.0
     **/
    public static String showTages(String keywords) throws UnsupportedEncodingException {
        if (StringUtils.isNotBlank(keywords)) {
            String[] arr = keywords.split(",");
            StringBuffer sb = new StringBuffer();
            for (String s : arr) {
                sb.append("<a href=\"/tag/" + URLEncoder.encode(s, "UTF-8") + "\">" + s + "</a>");
            }
            return sb.toString();
        }
        return "";
    }

    /**
     * @param value
     * @return java.lang.String
     * @note 展示文章内容
     * @author WCF
     * @time 2018/6/10 20:18
     * @since v1.0
     **/
    public static String showArticle(String value) {
        if (StringUtils.isNotBlank(value)) {
            return TaleUtils.mdToHtml(value);
        }
        return "";
    }

    /**
     * @param value
     * @return java.lang.String
     * @note 展示评论内容
     * @author WCF
     * @time 2018/6/10 20:20
     * @since v1.0
     **/
    public static String showComment(String value) {
        //可能会使用markdown语法，所以直接调用显示文章的方法即可
        return showArticle(value);
    }

    /**
     * @param id
     * @return java.lang.String
     * @note 根据评论人的id获取他的头像路径
     * @author WCF
     * @time 2018/6/10 20:21
     * @since v1.0
     **/
    public String showPhotoPath(Integer id) {
        UserInfo user = null;
        try {
            user = userService.getUserByid(id);
        } catch (PgSqlException e) {
            return "";
        }
        return user.getFacePath();
    }

    /**
     * @param username
     * @param commentAuthor
     * @return boolean
     * @note 判断是否用户就是本人
     * @author WCF
     * @time 2018/6/10 20:21
     * @since v1.0
     **/
    public static boolean isMyself(String username, String commentAuthor) {
        return ObjectUtils.equal(username, commentAuthor);
    }


    /**
     * 各类颜色class
     */
    private static final String[] COLORS = {"default", "primary", "success", "info", "warning", "danger", "inverse", "purple", "pink"};

    /**
     * @param
     * @return java.lang.String
     * @note 随机取色
     * @author WCF
     * @time 2018/6/10 20:22
     * @since v1.0
     **/
    public static String randColor() {
        int r = rand(0, COLORS.length - 1);
        return COLORS[r];
    }

    /**
     * @param min
     * @param max
     * @return int
     * @note 取随机数字
     * @author WCF
     * @time 2018/6/10 20:23
     * @since v1.0
     **/
    public static int rand(int min, int max) {
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

}
