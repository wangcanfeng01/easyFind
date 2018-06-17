package com.wcf.hellohome.read.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author WCF
 * @time 2018/4/15
 * @why write something
 **/
@Data
public class WcfCommentInfo implements Serializable {
    private static final long serialVersionUID = 2L;

    /**
     * 评论id
     */
    private Integer id;

    /**
     * 文章id
     */
    private Integer articleId;

    /**
     * 评论生成时的GMT unix时间戳
     */
    private Integer createTime;

    /**
     * 评论人
     */
    private String author;
    /**
     * 作者id
     */
    private Integer authorId;
    /**
     *
     */
    private Integer ownerId;
    /**
     * 路径
     */
    private String url;

    /**
     * ip
     */
    private String ip;

    /**
     *
     */
    private String agent;
    /**
     * 评论具体内容
     */
    private String text;
    /**
     * 类型
     */
    private String type;
    /**
     * 状态
     */
    private String status;
    private Integer parent;

}
