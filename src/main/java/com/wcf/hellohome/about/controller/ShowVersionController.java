package com.wcf.hellohome.about.controller;

import com.wcf.hellohome.about.model.VersionInfo;
import com.wcf.hellohome.about.model.VersionInfoVo;
import com.wcf.hellohome.about.service.VersionService;
import com.wcf.hellohome.common.controller.BaseController;
import com.wcf.hellohome.common.response.BaseResponse;
import com.wcf.hellohome.common.utils.DateUtils;
import com.wcf.hellohome.common.utils.ObjectUtils;
import com.wcf.hellohome.exception.PgSqlException;
import com.wcf.hellohome.user.model.MySelfInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author WCF
 * @time 2018/3/31
 * @why 关于本站
 **/
@Controller
@Log4j2
public class ShowVersionController extends BaseController {

    @Autowired
    private VersionService versionService;

    /**
     * @param request
     * @return java.lang.String
     * @note 展示版本信息
     * @author WCF
     * @time 2018/6/10 19:21
     * @since v1.0
     **/
    @GetMapping("/about")
    public String myself(HttpServletRequest request) {
        if (!ObjectUtils.isEmpty(request.getUserPrincipal())) {
            if (request.getUserPrincipal().getName().equals("wangaina")
                    || request.getUserPrincipal().getName().equals("王爱娜")) {
                return "show/love";
            }
        }
        MySelfInfo mySelfInfo = new MySelfInfo();
        mySelfInfo.setEmail("373711598@qq.com");
        mySelfInfo.setName("王灿锋");
        mySelfInfo.setQQ("QQ:373811598");
        mySelfInfo.setGithub("http://www.baidu.com");
        mySelfInfo.setWeibo("微博");
        mySelfInfo.setJianli("/show/files/王灿锋个人简历");
        mySelfInfo.setJianshu("简书");
        mySelfInfo.setLearning("/mind");
        request.setAttribute("wangcanfeng", mySelfInfo);

        try {
            List<VersionInfo> list = versionService.getAllVersions();
            request.setAttribute("versions", list);
        } catch (PgSqlException e) {
            return errorTransfer(request, e);
        }

        return "show/about/about";
    }

    /**
     * @param request
     * @param info
     * @return com.wcf.hellohome.common.response.BaseResponse
     * @note 保存版本信息
     * @author WCF
     * @time 2018/6/30 11:37
     * @since v1.0
     **/
    @PostMapping("/save/version")
    @ResponseBody
    public BaseResponse saveVersion(HttpServletRequest request, VersionInfoVo info) {
        String name = getUserName(request);
        VersionInfo versionInfo = new VersionInfo();
        versionInfo.setAuthor(name);
        versionInfo.setDescription(info.getDescription());
        versionInfo.setModifyTime(DateUtils.getCurrentUnixTime());
        versionInfo.setPublishTime(DateUtils.toUnixTime(info.getPublishTime()));
        versionInfo.setVersion(info.getVersion());
        try {
            if (null == info.getId()) {
                versionService.insertVersion(versionInfo);
            } else {
                versionInfo.setId(info.getId());
                versionService.updateVersionById(versionInfo);
            }
        } catch (PgSqlException e) {
            return BaseResponse.error(e);
        }
        return BaseResponse.ok();
    }

}
