package com.wcf.hellohome.exception;

import com.wcf.hellohome.common.constant.ErrorMessage;

/**
 * @author WCF
 * @time 2018/6/24
 * @why 功能：
 **/
public class UserException extends BaseException {
    /**
     * 抛出默认异常
     */
    public UserException() {
        super();
        setCode("00010000");
        setChinese("用户异常");
        setMsg("Default use error");
    }

    /**
     * 携带错误信息
     *
     * @param msg
     */
    public UserException(String msg) {
        super(msg);
        setChinese("用户异常");
        setMsg(msg);
        setCode("00010000");
    }

    /**
     * 抛出携带错误码和错误信息的异常
     *
     * @param code
     * @param msg
     */
    public UserException(String code, String msg) {
        super(msg);
        setChinese("用户异常");
        setMsg(msg);
        setCode(code);
    }

    /**
     * @param errorMessage
     * @return
     * @note 通过枚举
     * @author WCF
     * @time 2018/6/13 22:48
     * @since v1.0
     **/
    public UserException(ErrorMessage errorMessage) {
        super(errorMessage.getReason());
        setChinese(errorMessage.getChinese());
        setMsg(errorMessage.getReason());
        setCode(errorMessage.getCode());
    }
}
