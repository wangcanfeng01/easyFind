package com.wcf.hellohome.read.model;

import lombok.Data;

/**
 * @author WCF
 * @time 2018/5/1
 * @why 统计信息
 **/
@Data
public class WcfStatisticInfo {
    /**
     * 总文章篇数
     */
    private Long articles;

    /**
     * 总评论数
     */
    private Long comments;

    /**
     * 总点击次数
     */
    private Long hits;
}
