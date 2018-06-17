package com.wcf.hellohome.read.model;

import lombok.Data;

/**
 * @author WCF
 * @time 2018/5/1
 * @why 操作日志bo
 **/
@Data
public class WcfOperationLogInfo {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 操作
     */
    private String action;
    /**
     * 操作者名
     */
    private String author;
    /**
     * 操作者ip
     */
    private String ip;
    /**
     * 操作时间
     */
    private Integer createTime;
}
