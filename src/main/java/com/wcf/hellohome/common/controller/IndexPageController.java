package com.wcf.hellohome.common.controller;

import com.wcf.hellohome.common.utils.FindResourceFile;
import com.wcf.hellohome.common.utils.ObjectUtils;
import com.wcf.hellohome.exception.WCFAuthenticationException;
import com.wcf.hellohome.read.service.WcfOperationLogService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.util.List;


/**
 * @author WCF
 * @time 2018/1/28
 * @why write something
 **/
@Controller
@Log4j2
public class IndexPageController {

    /**
     *@note 首页
     *@author WCF
     *@time 2018/6/10 20:01
     *@since v1.0
     * @param
     *@return java.lang.String
     **/
    @GetMapping(value = {"/home","/"})
    public String index() {
        return "home";
    }

    /**
     *@note 登录入口
     *@author WCF
     *@time 2018/6/10 20:02
     *@since v1.0
     * @param
     *@return java.lang.String
     **/
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     *@note 注册入口
     *@author WCF
     *@time 2018/6/10 20:02
     *@since v1.0
     * @param
     *@return java.lang.String
     **/
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    /**
     *@note  简单搜索，后期需增强
     *@author WCF
     *@time 2018/6/10 20:02
     *@since v1.0
     * @param request
    * @param search
    * @param model
     *@return java.lang.String
     **/
    @GetMapping("/show/files/{search}")
    public String getFiles(HttpServletRequest request,
                           @PathVariable("search") String search, Model model) {
        if (ObjectUtils.isEmpty(request.getUserPrincipal())||!request.getUserPrincipal().getName().equals("wcf")) {
            throw new WCFAuthenticationException("没有权限");
        }
        FindResourceFile findResourceFile = new FindResourceFile();
        String uri = "error/404";
        try {
            List<String> fileNames = findResourceFile.getfiles();
            if (fileNames.contains(search)) {
                model.addAttribute("file", search);
            } else {
                log.info("找不到对应资源");
                model.addAttribute("error", "这个人很懒，还没有更新相关内容");
                return uri;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("找不到文件夹");
        }

        return "show/wmind/pdf";
    }
}
