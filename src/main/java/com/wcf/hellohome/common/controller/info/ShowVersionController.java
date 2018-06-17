package com.wcf.hellohome.common.controller.info;

import com.wcf.hellohome.common.utils.ObjectUtils;
import com.wcf.hellohome.user.model.MySelfInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author WCF
 * @time 2018/3/31
 * @why write something
 **/
@Controller
@Log4j2
public class ShowVersionController {
    /**
     *@note 展示版本信息
     *@author WCF
     *@time 2018/6/10 19:21
     *@since v1.0
     * @param request
     *@return java.lang.String
     **/
    @GetMapping("/info")
    public String myself(HttpServletRequest request) {
        if(!ObjectUtils.isEmpty(request.getUserPrincipal())){
            if(request.getUserPrincipal().getName().equals("wangaina")
                    ||request.getUserPrincipal().getName().equals("王爱娜")){
                return "show/love";
            }
        }
        MySelfInfo mySelfInfo=new MySelfInfo();
        mySelfInfo.setEmail("373711598@qq.com");
        mySelfInfo.setName("王灿锋");
        mySelfInfo.setQQ("QQ:373811598");
        mySelfInfo.setGithub("http://www.baidu.com");
        mySelfInfo.setWeibo("微博");
        mySelfInfo.setJianli("/show/files/王灿锋个人简历");
        mySelfInfo.setJianshu("简书");
        mySelfInfo.setLearning("/mind");
        request.setAttribute("wangcanfeng",mySelfInfo);
        return "show/info/info";
    }
}
