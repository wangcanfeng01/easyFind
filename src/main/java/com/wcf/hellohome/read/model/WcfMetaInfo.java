package com.wcf.hellohome.read.model;

import lombok.Data;

/**
 * @author WCF
 * @time 2018/5/3
 * @why 分类标签和关键词信息
 **/
@Data
public class WcfMetaInfo {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 标签或者关键词名
     */
    private String name;
    /**
     * 类型
     */
    private String type;

    /**
     * 专栏封面
     */
    private String cover;
    /**
     * 描述
     */
    private String description;
    /**
     * 所属标签或者关键词的文章数目
     */
    private Integer count;

    /**
     * 创建时间
     */
    private Integer createTime;
    /**
     * 最后修改时间
     */
    private Integer modifyTime;

}
