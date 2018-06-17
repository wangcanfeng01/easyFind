package com.wcf.hellohome.read.controller;

import com.wcf.hellohome.common.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author WCF
 * @time 2018/5/7
 * @why 配置管理
 **/
@Controller
@RequestMapping("/read/admin")
public class SettingsController extends BaseController{
    /**
     *@note 系统设置
     *@author WCF
     *@time 2018/6/15 19:20
     *@since v1.0
     * @param request
     *@return java.lang.String
     **/
    @GetMapping("/settings")
    public String settings(HttpServletRequest request) {
        return super.readTransfer("settings");
    }
}
