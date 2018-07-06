package com.wcf.hellohome.about.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author WCF
 * @time 2018/3/31
 * @why 显示学习信息
 **/
@Controller
@Log4j2
public class LearningController {

    /**
     *@note 显示思维导图
     *@author WCF
     *@time 2018/6/10 19:34
     *@since v1.0
     * @param
     *@return java.lang.String
     **/
    @GetMapping("/mind")
    public String mind() {
        return "show/wmind/mind";
    }
}
