package com.wcf.hellohome.read.model;

import java.io.Serializable;

/**
 * @author WCF
 * @time 2018/3/31
 * @why write something
 **/
public class WcfArticleInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 文章表主键
     */
    private Integer id;

    /**
     * 内容标题
     */
    private String title;
    /**
     * 缩略名，用于当成地址访问,对标题进行加密，防止外链
     */
    private String titleSimple;

    /**
     * 文章封面链接
     */
    private String cover;

    /**
     * 创建时间
     */
    private Integer createTime;

    /**
     * 修改时间
     */
    private Integer modifyTime;
    /**
     * 具体内容信息
     */
    private String text;
    /**
     * 作者名字
     */
    private String author;
    /**
     * 关键字
     */
    private String keywords;

    /**
     * 内容状态
     */
    private String status;

    /**
     * 文章类型
     */
    private String categories;
    /**
     * 文章被点击次数
     */
    private Integer hits;
    /**
     * 点赞数目
     */
    private Integer stars;
    /**
     * 文章被评论次数
     */
    private Integer commentsNum;

    /**
     * 是否允许评论
     */
    private Boolean allowComment;
    /**
     * 是否允许可见
     */
    private String allowSee;

    /**
     * 文章删除标志
     * @return
     */
    private Short deleteFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleSimple() {
        return titleSimple;
    }

    public void setTitleSimple(String titleSimple) {
        this.titleSimple = titleSimple;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Integer modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getAllowSee() {
        return allowSee;
    }

    public void setAllowSee(String allowSee) {
        this.allowSee = allowSee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public Integer getHits() {
        return hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public Integer getCommentsNum() {
        return commentsNum;
    }

    public void setCommentsNum(Integer commentsNum) {
        this.commentsNum = commentsNum;
    }

    public Boolean getAllowComment() {
        return allowComment;
    }

    public void setAllowComment(Boolean allowComment) {
        this.allowComment = allowComment;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public Short getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Short deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
