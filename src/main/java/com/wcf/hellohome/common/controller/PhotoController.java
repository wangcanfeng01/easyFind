package com.wcf.hellohome.common.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author WCF
 * @time 2018/3/31
 * @why 相册服务
 **/
@Controller
@Log4j2
public class PhotoController {

    /**
     *@note 相册入口
     *@author WCF
     *@time 2018/6/10 20:04
     *@since v1.0
     * @param
     *@return java.lang.String
     **/
    @GetMapping("/album")
    public String album() {
        return "show/album";
    }
}
