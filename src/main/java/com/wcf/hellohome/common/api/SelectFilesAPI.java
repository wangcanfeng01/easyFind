package com.wcf.hellohome.common.api;

import com.alibaba.fastjson.JSONObject;
import com.wcf.hellohome.common.utils.FindResourceFile;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by Administrator on 2018/2/14.
 */
@Log4j2
@RestController
public class SelectFilesAPI {
    /**
     *@note 获取当前资源中的文件名
     *@author WCF
     *@time 2018/6/10 18:45
     *@since v1.0
     * @param name
     *@return com.alibaba.fastjson.JSONObject
     **/
    @RequestMapping(value = "/get/files", method = RequestMethod.GET)
    public JSONObject getFiles( @RequestParam(name = "name") String name) {
        FindResourceFile findResourceFile = new FindResourceFile();
        JSONObject json = new JSONObject();
        json.put("查询结果", "查询成功了");
        json.put("name:",name);
        return json;
    }

    /**
     *@note 提交文件
     *@author WCF
     *@time 2018/6/10 18:45
     *@since v1.0
     * @param file 文件
     *@return com.alibaba.fastjson.JSONObject
     **/
    @RequestMapping(value = "/post/files", method = RequestMethod.POST,consumes = "multipart/form-data" )
    public JSONObject postFiles(@RequestParam("fileUpload") MultipartFile file) {
        JSONObject json = new JSONObject();

        json.put("提交结果", "提交成功");
        json.put("文件",file.getName());
        return json;
    }

    /**
     *@note 修改文件
     *@author WCF
     *@time 2018/6/10 18:45
     *@since v1.0
     *@return com.alibaba.fastjson.JSONObject
     **/
    @RequestMapping(value = "/put/files", method = RequestMethod.PUT)
    public JSONObject putFiles() {
        JSONObject json = new JSONObject();
        json.put("修改结果", "修改成功");
        return json;
    }

    /**
     *@note 删除接口
     *@author WCF
     *@time 2018/6/10 18:45
     *@since v1.0
     *@return com.alibaba.fastjson.JSONObject
     **/
    @RequestMapping(value = "/delete/files", method = RequestMethod.DELETE)
    public JSONObject deleteFiles() {
        JSONObject json = new JSONObject();
        json.put("删除结果", "删除成功");
        return json;
    }
}
