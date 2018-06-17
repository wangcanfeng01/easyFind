package com.wcf.hellohome.common.utils;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wcf on 2018/2/14.
 */
public class FindResourceFile {
    /**
     * 文件名列表
     */
    public static List<String> fileNames;

    /**
     *@note 构造器获取文件名称列表
     *@author WCF
     *@time 2018/6/10 20:41
     *@since v1.0
     * @param
     *@return
     **/
    public FindResourceFile() {
        if (this.fileNames == null) {
            this.fileNames = new ArrayList<>();
        }
    }

    /**
     *@note 搜索文件
     *@author WCF
     *@time 2018/6/10 20:44
     *@since v1.0
     * @param
     *@return java.util.List<java.lang.String>
     **/
    public List<String> getfiles() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:static/files");
        String[] strings = file.list();
        for (String name : strings) {
            name=removeSuffix(name);
            fileNames.add(name);
        }
        return fileNames;
    }

    /**
     *@note 直接丢掉后缀
     *@author WCF
     *@time 2018/6/10 20:45
     *@since v1.0
     * @param name
     *@return java.lang.String
     **/
    public String removeSuffix(String name){
        int index=name.indexOf('.');
        name=name.substring(0,index);
        return name;
    }


}
