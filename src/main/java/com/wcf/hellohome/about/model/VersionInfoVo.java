package com.wcf.hellohome.about.model;

import lombok.Data;

/**
 * @author WCF
 * @time 2018/6/30
 * @why 功能：版本信息vo
 **/
@Data
public class VersionInfoVo {
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
    private String publishTime;
    /**
     * 描述
     */
    private String description;
}
