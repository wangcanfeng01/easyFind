package com.wcf.hellohome.common.response;

import com.wcf.hellohome.common.constant.ErrorMessage;
import com.wcf.hellohome.common.utils.DateUtils;
import com.wcf.hellohome.exception.BaseException;

/**
 * @author WCF
 * @time 2018/4/17
 * @why 展示返回参数
 **/
public class BaseResponse {
    /**
     * 返回信息
     */
    private String msg;

    /**
     * 请求是否成功
     */
    private boolean success;

    /**
     * 状态码
     */
    private String code = "-1";

    /**
     * 当前时间
     */
    private String time;


    /**
     * @param msg
     * @return com.wcf.hellohome.common.response.BaseResponse
     * @note 返回正确信息相关类
     * @author WCF
     * @time 2018/6/14 23:44
     * @since v1.0
     **/
    public static BaseResponse ok(String msg) {
        return new BaseResponse().setCode("0").setSuccess(true).setMsg(msg).setTime(DateUtils.now());
    }

    /**
     * @param
     * @return com.wcf.hellohome.common.response.BaseResponse
     * @note 默认操作成功
     * @author WCF
     * @time 2018/6/14 23:44
     * @since v1.0
     **/
    public static BaseResponse ok() {
        return ok("操作成功");
    }

    /**
     * @param
     * @return com.wcf.hellohome.common.response.BaseResponse
     * @note 默认操作失败
     * @author WCF
     * @time 2018/6/14 23:45
     * @since v1.0
     **/
    public static BaseResponse error() {
        return error("操作失败");
    }

    /**
     * @param msg
     * @return com.wcf.hellohome.common.response.BaseResponse
     * @note 返回异常信息相关类
     * @author WCF
     * @time 2018/6/14 23:45
     * @since v1.0
     **/
    public static BaseResponse error(String msg) {
        return new BaseResponse().setSuccess(false).setMsg(msg).setTime(DateUtils.now());
    }

    /**
     * @param e
     * @return com.wcf.hellohome.common.response.BaseResponse
     * @note 通过异常返回错误信息
     * @author WCF
     * @time 2018/6/14 23:54
     * @since v1.0
     **/
    public static BaseResponse error(BaseException e) {
        return new BaseResponse().setCode(e.getCode()).setSuccess(false).setMsg(e.getChinese()).setTime(DateUtils.now());
    }

    public String getMsg() {
        return msg;
    }

    public BaseResponse setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public BaseResponse setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getCode() {
        return code;
    }

    public BaseResponse setCode(String code) {
        this.code = code;
        return this;
    }

    public String getTime() {
        return time;
    }

    public BaseResponse setTime(String time) {
        this.time = time;
        return this;
    }
}
