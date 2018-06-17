package com.wcf.hellohome.configuration.handler;

import com.wcf.hellohome.common.constant.ErrorMessage;
import com.wcf.hellohome.exception.PgSqlException;
import com.wcf.hellohome.read.service.WcfOperationLogService;
import com.wcf.hellohome.user.model.UserDetailsInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author WCF
 * @time 2018/3/4
 * @why 登录成功处理
 **/
@Log4j2
public class WCFAuthenticationSuessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    /**
     *@note 构造器注入服务
     *@author WCF
     *@time 2018/6/11 23:59
     *@since v1.0
     * @param wcfOperationService
     *@return
     **/
    public WCFAuthenticationSuessHandler(WcfOperationLogService wcfOperationService) {
        this.wcfOperationService = wcfOperationService;
    }

    /**
     * 操作日志服务
     */
    private WcfOperationLogService wcfOperationService;

    /**
     *@note 登录成功后的处理
     *@author WCF
     *@time 2018/6/12 0:00
     *@since v1.0
     * @param request
    * @param response
    * @param authentication
     *@return void
     **/
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
        //用户信息来自 provider 中的 UsernamePasswordAuthenticationToken(userInfo, password, authorities)
        //所以可以强制转换类型
        UserDetailsInfo userDetailsInfo = (UserDetailsInfo) authentication.getPrincipal();
        try {
            wcfOperationService.insertLog("用户登录", request.getRemoteAddr(), userDetailsInfo.getNickname());
        } catch (PgSqlException e) {
            log.error(ErrorMessage.INSERT_USER_ERROR,e);
        }
        log.info("此次登录用户：" + userDetailsInfo.getUsername());
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
