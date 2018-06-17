package com.wcf.hellohome.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author WCF
 * @time 2018/3/27
 * @why 鉴权异常
 **/
public class WCFAuthenticationException extends AuthenticationException{
    /**
     * 消息
     */
    public String message;
    /**
     *@note 构造器传入异常
     *@author WCF
     *@time 2018/6/12 0:24
     *@since v1.0
     * @param msg
     *@return
     **/
    public WCFAuthenticationException(String msg) {
        super(msg);
        this.message=msg;
    }

    /**
     *@note 鉴权信息转string
     *@author WCF
     *@time 2018/6/12 0:25
     *@since v1.0
     * @param
     *@return java.lang.String
     **/
    @Override
    public String toString() {
        return message;
    }
}
