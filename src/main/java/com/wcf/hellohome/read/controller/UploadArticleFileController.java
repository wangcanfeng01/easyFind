package com.wcf.hellohome.read.controller;

import com.wcf.hellohome.common.controller.BaseController;
import com.wcf.hellohome.common.response.BaseResponse;
import com.wcf.hellohome.common.utils.DateUtils;
import com.wcf.hellohome.common.utils.ObjectUtils;
import com.wcf.hellohome.common.utils.UploadFileUtils;
import com.wcf.hellohome.common.utils.UuidUtils;
import com.wcf.hellohome.exception.PgSqlException;
import com.wcf.hellohome.read.model.PictureUploadInfo;
import com.wcf.hellohome.read.service.PictureUploadService;
import com.wcf.hellohome.read.service.WcfArticleService;
import com.wcf.hellohome.read.service.WcfOperationLogService;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

/**
 * @author WCF
 * @time 2018/6/18
 * @why 功能：上传功能
 **/
@Controller
@Log4j2
@RequestMapping("/read/admin")
public class UploadArticleFileController extends BaseController {

    /**
     * 图片资源基础路径
     */
    @Value("${static.picture}")
    private String staticPicturePath;
    /**
     * 图片资源备份路径
     */
    @Value("${upload.backup.path}")
    private String backupPicturePath;
    /**
     * 文章内图片相对路径
     */
    @Value("${upload.picture.path}")
    private String picturePath;
    /**
     * 文章封面图片路径
     */
    @Value("${upload.cover.path}")
    private String coverPath;

    @Autowired
    private WcfArticleService articleService;

    @Autowired
    private WcfOperationLogService operationLogService;

    @Autowired
    private PictureUploadService pictureUploadService;

    /**
     * @param file
     * @return com.wcf.hellohome.common.response.BaseResponse
     * @note 上传文章内图片
     * @author WCF
     * @time 2018/6/15 19:08
     * @since v1.0
     **/
    @PostMapping(value = "article/add/picture")
    @ResponseBody
    public BaseResponse onUploadPicture(HttpServletRequest request,
                                        @RequestParam("file") MultipartFile file,
                                        @RequestParam("idField") Integer articleId) {
        String authorName = getUserName(request);
        if (file.isEmpty() || !file.getContentType().startsWith("image")) {
            return BaseResponse.error("非图片资源，上传失败");
        }
        long size = file.getSize();
        if (size > 1048576) {
            return BaseResponse.error("图片不能超过1MB");
        }
        PictureUploadInfo info = new PictureUploadInfo();
        UploadFileUtils uploadFileUtils=new UploadFileUtils(staticPicturePath,backupPicturePath);
        String fileLink = uploadFileUtils.uploadPicture(file, picturePath, info);
        if (!ObjectUtils.isEmpty(fileLink)) {
            info.setAuthor(authorName);
            info.setSize((int) size / 1024);
            info.setOrganisationId(articleId);
            info.setType("picture");
            info.setCreateTime(DateUtils.getCurrentUnixTime());
            info.setModifyTime(DateUtils.getCurrentUnixTime());
            try {
                pictureUploadService.insertPicture(info);
            } catch (PgSqlException e) {
                return BaseResponse.error(e);
            }
            return BaseResponse.ok(fileLink);
        }
        return BaseResponse.error("图片上传失败");
    }

    /**
     * @param request
     * @param articleCover
     * @param articleId
     * @return com.wcf.hellohome.common.response.BaseResponse
     * @note 上传封面
     * @author WCF
     * @time 2018/6/15 19:08
     * @since v1.0
     **/
    @PostMapping(value = "article/add/cover")
    @ResponseBody
    public BaseResponse onUploadCover(HttpServletRequest request,
                                      @RequestParam("articleCover") MultipartFile articleCover,
                                      @RequestParam("articleId") Integer articleId) {
        String authorName = getUserName(request);
        if (articleCover.isEmpty() || !articleCover.getContentType().startsWith("image")) {
            return BaseResponse.error("非图片资源，上传失败");
        }
        long size = articleCover.getSize();
        if (size > 1048576) {
            return BaseResponse.error("图片不能超过1MB");
        }
        PictureUploadInfo info = new PictureUploadInfo();
        UploadFileUtils uploadFileUtils=new UploadFileUtils(staticPicturePath,backupPicturePath);
        String fileLink = uploadFileUtils.uploadPicture(articleCover, coverPath, info);
        if (!ObjectUtils.isEmpty(fileLink)) {
            try {
                info.setSize((int) size / 1024);
                info.setOrganisationId(articleId);
                info.setAuthor(authorName);
                info.setType("cover");
                info.setCreateTime(DateUtils.getCurrentUnixTime());
                info.setModifyTime(DateUtils.getCurrentUnixTime());
                PictureUploadInfo oldInfo = pictureUploadService.getCoverPictureByAid(articleId);
                if (ObjectUtils.isEmpty(oldInfo)) {
                    pictureUploadService.insertPicture(info);
                } else {
                    if (!oldInfo.getName().equals(info.getName())) {
                        uploadFileUtils.deletePictures(oldInfo.getPath());
                    }
                    info.setId(oldInfo.getId());
                    pictureUploadService.updatePictureInfosById(info);
                }
                articleService.updateArticleCover(articleId, fileLink);
                operationLogService.insertLog("上传封面",
                        request.getRemoteAddr(), getUserName(request));
            } catch (PgSqlException e) {
                return BaseResponse.error(e);
            }
            return BaseResponse.ok(fileLink);
        } else {
            return BaseResponse.error("图片名不能为空");
        }
    }




}
