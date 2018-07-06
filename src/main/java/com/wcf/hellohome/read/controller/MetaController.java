package com.wcf.hellohome.read.controller;

import com.wcf.hellohome.common.constant.WCFConst;
import com.wcf.hellohome.common.controller.BaseController;
import com.wcf.hellohome.common.response.BaseResponse;
import com.wcf.hellohome.common.utils.DateUtils;
import com.wcf.hellohome.common.utils.ObjectUtils;
import com.wcf.hellohome.common.utils.UploadFileUtils;
import com.wcf.hellohome.exception.PgSqlException;
import com.wcf.hellohome.exception.WCFAuthenticationException;
import com.wcf.hellohome.read.model.PictureUploadInfo;
import com.wcf.hellohome.read.model.WcfMetaInfo;
import com.wcf.hellohome.read.service.PictureUploadService;
import com.wcf.hellohome.read.service.WcfMetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author WCF
 * @time 2018/5/6
 * @why 标签操作
 **/
@Controller
@RequestMapping("/read/admin/metas")
public class MetaController extends BaseController {
    /**
     * 图片资源基础路径
     */
    @Value("${static.picture}")
    private String staticPicturePath;
    /**
     * 图片资源备份基础路径
     */
    @Value("${upload.backup.path}")
    private String backupPicturePath;

    /**
     * 分类标签图片路径
     */
    @Value("${upload.category.path}")
    private String categoryPath;

    @Autowired
    private WcfMetaService metaService;
    @Autowired
    private PictureUploadService pictureUploadService;

    /**
     * @param request
     * @return java.lang.String
     * @note 查询标签
     * @author WCF
     * @time 2018/6/15 19:13
     * @since v1.0
     **/
    @GetMapping("")
    public String getMeta(HttpServletRequest request) {
        String userName = getUserName(request);
        if (!"wcf".equals(userName)) {
            throw new WCFAuthenticationException("不是管理员不能访问");
        }
        try {
            List<WcfMetaInfo> categories = metaService.getMetasByType("category");
            List<WcfMetaInfo> keywords = metaService.getMetasByType("keyword");
            request.setAttribute("categories", categories);
            request.setAttribute("keywords", keywords);
        } catch (PgSqlException e) {
            return super.errorTransfer(request, e);
        }
        return super.readTransfer("metas");
    }

    /**
     * @param id
     * @param name
     * @param description
     * @return com.wcf.hellohome.common.response.BaseResponse
     * @note 保存标签
     * @author WCF
     * @time 2018/6/15 19:14
     * @since v1.0
     **/
    @PostMapping("/save")
    @ResponseBody
    public BaseResponse saveMeta(@RequestParam("metaId") Integer id,
                                 @RequestParam("metaName") String name,
                                 @RequestParam("description") String description,
                                 @RequestParam("metaCover") MultipartFile metaCover,
                                 HttpServletRequest request) {
        if (metaCover.getSize() > 0) {
            if (metaCover.isEmpty() || !metaCover.getContentType().startsWith("image")) {
                return BaseResponse.error("非图片资源，上传失败");
            }
            long size = metaCover.getSize();
            if (size > 1048576) {
                return BaseResponse.error("图片不能超过1MB");
            }
            try {
                BaseResponse response = processUpload(size, request, metaCover, id);
                if(!response.isSuccess()){
                    return response;
                }
                if (!ObjectUtils.isEmpty(id)) {
                    metaService.updateMetaInfoById(name, description,response.getMsg(), id);
                    return BaseResponse.ok();
                } else {
                    metaService.insertMeta(name, WCFConst.Types.CATEGORY, description,response.getMsg());
                    return BaseResponse.ok();
                }
            } catch (PgSqlException e) {
                return BaseResponse.error(e);
            }
        } else {
            try {
                if (!ObjectUtils.isEmpty(id)) {
                    metaService.updateMetaInfoById(name, description, id);
                    return BaseResponse.ok();
                } else {
                    metaService.insertMeta(name, WCFConst.Types.CATEGORY, description,null);
                    return BaseResponse.ok();
                }
            } catch (PgSqlException e) {
                return BaseResponse.error(e);
            }
        }
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public BaseResponse deleteMeta(@PathVariable("id") Integer id) {
        try {
            metaService.deleteMetaById(id);
        } catch (PgSqlException e) {
            return BaseResponse.error(e);
        }
        return BaseResponse.ok();
    }

    /**
     * @param size
     * @param request
     * @param metaCover
     * @return com.wcf.hellohome.common.response.BaseResponse
     * @note 图片上传处理
     * @author WCF
     * @time 2018/6/25 22:02
     * @since v1.0
     **/
    private BaseResponse processUpload(long size, HttpServletRequest request, MultipartFile metaCover, Integer id) {
        String authorName = getUserName(request);
        PictureUploadInfo info = new PictureUploadInfo();
        UploadFileUtils uploadFileUtils = new UploadFileUtils(staticPicturePath, backupPicturePath);
        String fileLink = uploadFileUtils.uploadPicture(metaCover, categoryPath, info);
        if (!ObjectUtils.isEmpty(fileLink)) {
            info.setAuthor(authorName);
            info.setSize((int) size / 1024);
            info.setOrganisationId(id);
            info.setType("category");
            info.setCreateTime(DateUtils.getCurrentUnixTime());
            info.setModifyTime(DateUtils.getCurrentUnixTime());
            try {
                pictureUploadService.insertPicture(info);
            } catch (PgSqlException e) {
                return BaseResponse.error(e);
            }
            return BaseResponse.ok(fileLink);
        }
        return BaseResponse.ok("图片上传失败");
    }
}
