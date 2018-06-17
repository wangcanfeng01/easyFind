package com.wcf.hellohome.user.model;

/**
 * @author WCF
 * @time 2018/4/24
 * @why 我的个人信息
 **/
public class MySelfInfo {
    /**
     * 名字
     */
    private String name;
    /**
     * 微博
     */
    private String weibo;
    /**
     * 邮件
     */
    private String email;
    /**
     * github地址
     */
    private String github;
    /**
     * 简历链接
     */
    private String jianli;
    /**
     * qq号码
     */
    private String QQ;
    /**
     * 简书地址
     */
    private String jianshu;
    /**
     * 学习思维导图链接
     */
    private String learning;

    public String getJianshu() {
        return jianshu;
    }

    public void setJianshu(String jianshu) {
        this.jianshu = jianshu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getJianli() {
        return jianli;
    }

    public void setJianli(String jianli) {
        this.jianli = jianli;
    }

    public String getQQ() {
        return QQ;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ;
    }

    public String getLearning() {
        return learning;
    }

    public void setLearning(String learning) {
        this.learning = learning;
    }
}
