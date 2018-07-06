package com.wcf.hellohome.common.utils;

import com.wcf.hellohome.read.model.PictureUploadInfo;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @author WCF
 * @time 2018/6/25
 * @why 功能：文件上传方法
 **/
@Log4j2
public class UploadFileUtils {

    /**
     * 图片资源基础路径
     */
    private String staticPicturePath;

    /**
     * 图片资源备份基础路径
     */
    private String backupPicturePath;

    /**
     * 静态资源路径
     */
    private String staticPath;

    /**
     * @param staticPicturePath
     * @param baWCFpPicturePa2018/6/25 23:27rn
     * @note 构* @param staticPicturePath
* @param backupPicturePath/6/25 21:33
     * @since v1.0
     **/
    public UploadFileUtils(String staticPicturePath, String backupPicturePath) {
        // 静态资源基础路径
        this.staticPath = "/static";
        this.staticPicturePath = staticPicturePath;
        this.backupPicturePath = backupPicturePath;
    }

    /**
     * @param file
     * @param pathStr
     * @param info
     * @return java.lang.String
     * @note 保存图片
     * @author WCF
     * @time 2018/6/15 19:09
     * @since v1.0
     **/
    public String uploadPicture(MultipartFile file, String pathStr, PictureUploadInfo info) {

        String fileName = file.getOriginalFilename();
        fileName = fileName.replaceAll("\\\\", "/");
        String[] arr = fileName.split("/");
        if (arr.length < 2) {
            if (!ObjectUtils.isEmpty(arr[0])) {
                fileName = arr[0];
            } else {
                return null;
            }
        } else {
            fileName = arr[arr.length - 1];
        }
        //插入数据库中的是图片原名
        info.setName(fileName);
        //显示名称改成uuid
        fileName = UuidUtils.generateUuid() + ".jpg";
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        path = path.replaceAll("\\\\", "/");
        String filePath = path + staticPath + staticPicturePath + pathStr + fileName;
        filePath = filePath.replaceAll("//", "/");
        info.setPath(pathStr + fileName);
        //上传图片
        try (InputStream in = file.getInputStream();
             OutputStream out = new FileOutputStream(filePath)) {
            IOUtils.copy(in, out);
        } catch (IOException e) {
            log.error("Can't upload picture to: " + filePath, e);
        } catch (Exception e) {
            log.error("Can't upload picture to: " + filePath, e);
        }
        //上传到备份路径
        String backupPath = backupPicturePath + pathStr + fileName;
        try (InputStream in = file.getInputStream();
             OutputStream backup = new FileOutputStream(backupPath)) {
            IOUtils.copy(in, backup);
        } catch (IOException e) {
            log.error("Can't upload picture to backup path: " + filePath, e);
        } catch (Exception e) {
            log.error("Can't upload picture to backup path: " + filePath, e);
        }

        return staticPicturePath + pathStr + fileName;
    }

    /**
     * @param
     * @return java.lang.String
     * @note 删除图片
     * @author WCF
     * @time 2018/6/18 21:05
     * @since v1.0
     **/
    public void deletePictures(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        return;
    }
}
