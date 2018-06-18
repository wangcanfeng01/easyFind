package com.wcf.hellohome.read.model;

import lombok.Data;

/**
 * @author WCF
 * @time 2018/6/18
 * @why 功能：图片信息类
 **/
@Data
public class PictureUploadInfo {
    /**
     * 图片id
     */
    private Integer id;
    /**
     * 图片名称
     */
    private String name;
    /**
     * 图片路径
     */
    private String path;
    /**
     * 图片上传者
     */
    private String author;
    /**
     * 图片大小
     */
    private Integer size;
    /**
     * 所属资源id
     */
    private Integer organisationId;
    /**
     * 文章类型
     */
    private String type;
    /**
     * 创建时间
     */
    private Integer createTime;

    /**
     * 修改时间
     */
    private Integer modifyTime;
}
