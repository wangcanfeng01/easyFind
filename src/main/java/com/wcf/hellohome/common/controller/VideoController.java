package com.wcf.hellohome.common.controller;

import com.wcf.hellohome.exception.WCFAuthenticationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author WCF
 * @time 2018/3/31
 * @why 视频业务
 **/
@Controller
@Log4j2
public class VideoController {

    /**
     *@note 视频入口
     *@author WCF
     *@time 2018/6/10 20:04
     *@since v1.0
     * @param
     *@return java.lang.String
     **/
    @GetMapping("/video")
    public String video() {
        throw new WCFAuthenticationException("还在开发中");
    }
}
