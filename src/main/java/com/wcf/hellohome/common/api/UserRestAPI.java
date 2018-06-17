package com.wcf.hellohome.common.api;

import com.wcf.hellohome.common.utils.MD5Utils;
import com.wcf.hellohome.exception.PgSqlException;
import com.wcf.hellohome.read.service.WcfOperationLogService;
import com.wcf.hellohome.user.model.UserDetailsInfo;
import com.wcf.hellohome.user.model.UserInfo;
import com.wcf.hellohome.user.service.WcfRelationService;
import com.wcf.hellohome.user.service.WcfUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author WCF
 * @time 2018/1/29
 * @why write something
 **/
@RestController
@RequestMapping("/user")
@Log4j2
public class UserRestAPI {
    /**
     * 用户服务
     */
    @Autowired
    private WcfUserService userService;
    /**
     * 关系信息
     */
    @Autowired
    private WcfRelationService relationService;
    /**
     *
     */
    @Autowired
    private WcfOperationLogService operationLogService;

    /**
     * @author WCF
     * @time 2018/1/29 23:55
     * @Description 注册新用户
     **/
    @PostMapping("/register")
    public boolean register(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        password = MD5Utils.encode(password);
        UserInfo info = new UserInfo(username, password);
        try {
            operationLogService.insertLog("新用户注册", request.getRemoteAddr(), username);
            return userService.addNewUser(info);
        } catch (PgSqlException e) {
            return false;
        }

    }

    /**
     * @param userDetailsInfo
     * @param friendName
     * @return boolean
     * @note 添加好友
     * @author WCF
     * @time 2018/6/13 0:27
     * @since v1.0
     **/
    @PostMapping("/add")
    public boolean add(@AuthenticationPrincipal UserDetailsInfo userDetailsInfo, @RequestParam String friendName) {
        try {
            return relationService.addFriend(userDetailsInfo.getUsername(), friendName);
        } catch (PgSqlException e) {
            return false;
        }
    }
}
