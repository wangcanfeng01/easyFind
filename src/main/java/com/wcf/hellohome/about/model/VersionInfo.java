package com.wcf.hellohome.about.model;

import lombok.Data;

/**
 * @author WCF
 * @time 2018/6/28
 * @why 功能：版本信息
 **/
@Data
public class VersionInfo {
    /**
     * id
     */
    private Integer id;
    /**
     * 版本号
     */
    private String version;
    /**
     * 发布时间
     */
    private Integer publishTime;
    /**
     * 描述
     */
    private String description;
    /**
     * 作者
     */
    private String author;
    /**
     * 修改时间
     */
    private Integer modifyTime;
}
