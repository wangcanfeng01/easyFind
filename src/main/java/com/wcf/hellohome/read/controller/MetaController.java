package com.wcf.hellohome.read.controller;

import com.wcf.hellohome.common.constant.WCFConst;
import com.wcf.hellohome.common.controller.BaseController;
import com.wcf.hellohome.common.response.BaseResponse;
import com.wcf.hellohome.common.utils.ObjectUtils;
import com.wcf.hellohome.exception.PgSqlException;
import com.wcf.hellohome.exception.WCFAuthenticationException;
import com.wcf.hellohome.read.model.WcfMetaInfo;
import com.wcf.hellohome.read.service.WcfMetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author WCF
 * @time 2018/5/6
 * @why 标签操作
 **/
@Controller
@RequestMapping("/read/admin/metas")
public class MetaController extends BaseController {
    @Autowired
    private WcfMetaService metaService;

    /**
     *@note 查询标签
     *@author WCF
     *@time 2018/6/15 19:13
     *@since v1.0
     * @param request
     *@return java.lang.String
     **/
    @GetMapping("")
    public String getMeta(HttpServletRequest request) {
        String userName = getUserName(request);
        if (!"wcf".equals(userName)) {
            throw new WCFAuthenticationException("不是管理员不能访问");
        }
        try {
            List<WcfMetaInfo> categories = metaService.getMetasByType("category");
            List<WcfMetaInfo> keywords = metaService.getMetasByType("keyword");
            request.setAttribute("categories", categories);
            request.setAttribute("keywords", keywords);
        }catch (PgSqlException e){
            return super.errorTransfer(request,e);
        }
        return super.readTransfer("metas");
    }

    /**
     *@note 保存标签
     *@author WCF
     *@time 2018/6/15 19:14
     *@since v1.0
     * @param id
    * @param name
    * @param description
     *@return com.wcf.hellohome.common.response.BaseResponse
     **/
    @PostMapping("/save")
    @ResponseBody
    public BaseResponse saveMeta(@RequestParam("id") Integer id,
                                 @RequestParam("name") String name,
                                 @RequestParam("description") String description) {
        try {
            if(!ObjectUtils.isEmpty(id)){
                metaService.updateMetaInfoById(name, description, id);
                return BaseResponse.ok();
            }else {
                metaService.insertMeta(name, WCFConst.Types.CATEGORY, description);
                return BaseResponse.ok();
            }
        }catch (PgSqlException e){
         return BaseResponse.error(e);
        }
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public BaseResponse deleteMeta(@PathVariable("id") Integer id) {
        try {
            metaService.deleteMetaById(id);
        } catch (PgSqlException e) {
            return BaseResponse.error(e);
        }
        return BaseResponse.ok();
    }
}
