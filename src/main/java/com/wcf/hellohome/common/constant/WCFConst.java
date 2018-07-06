package com.wcf.hellohome.common.constant;

/**
 * @author WCF
 * @time 2018/4/3
 * @why write something 通用常量
 **/

public class WCFConst {
    /**
     * 每次请求获取的最多文章数
     */
    public static final int MAX_PAGE = 1000;
    public static final String NOT_LOGIN_USER="爱学习的朋友";
    /**
     * 文章删除标志
     */
    public static final int DELETE_FLAG=1;
    /**
     * 通用类型
     */
    public static class Types {
        /**
         * 关键词
         */
        public final static String KEYWORD = "keyword";
        /**
         * 分类
         */
        public final static String CATEGORY = "category";
        /**
         * 文章
         */
        public final static String ARTICLE = "article";
        /**
         * 连接
         */
        public final static String LINK = "link";
        /**
         * 评论频率
         */
        public final static String COMMENT_FREQUENCY = "commentFrequency";
        /**
         * 所有人可见
         */
        public final static String EVERYONE = "everyone";

    }
}
