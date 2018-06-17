package com.wcf.hellohome.read.controller;

import com.wcf.hellohome.common.response.BaseResponse;
import com.wcf.hellohome.common.constant.WCFCache;
import com.wcf.hellohome.common.constant.WCFConst;
import com.wcf.hellohome.common.utils.DateUtils;
import com.wcf.hellohome.exception.PgSqlException;
import com.wcf.hellohome.read.model.WcfCommentInfo;
import com.wcf.hellohome.read.service.WcfCommentService;
import com.wcf.hellohome.user.model.UserDetailsInfo;
import com.wcf.hellohome.user.model.UserInfo;
import com.wcf.hellohome.user.service.WcfUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author WCF
 * @time 2018/4/16
 * @why 评论相关操作
 **/
@RestController
@RequestMapping("/read/comment")
public class CommentsController {
    WCFCache cache = WCFCache.getInstance();
    @Autowired
    private WcfCommentService commentService;
    @Autowired
    private WcfUserService userService;

    /**
     * @param request
     * @param text
     * @param id
     * @return com.wcf.hellohome.common.response.BaseResponse
     * @note 发表评论
     * @author WCF
     * @time 2018/6/15 19:13
     * @since v1.0
     **/
    @PostMapping("/insert")
    public BaseResponse comment(HttpServletRequest request,
                                @RequestParam("text") String text,
                                @RequestParam("id") String id) {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) request.getUserPrincipal();
        if (ObjectUtils.isEmpty(authenticationToken)) {
            return BaseResponse.error("请登录后再评论");
        }
        UserDetailsInfo userDetailsInfo = (UserDetailsInfo) authenticationToken.getPrincipal();
        String name = userDetailsInfo.getNickname();
        String key = WCFConst.Types.COMMENT_FREQUENCY + name;
        String something = cache.select(key);
        if (null != something) {
            return BaseResponse.error("评论发表太快");
        } else {
            cache.insert(key, "too fast", DateUtils.getTimeAfterNSeconds(10));
            //将评论插入到数据库中
            try {
                WcfCommentInfo commentInfo = new WcfCommentInfo();
                commentInfo.setArticleId(Integer.valueOf(id));
                commentInfo.setAuthor(name);
                UserInfo user = userService.getByUsername(name);
                commentInfo.setAuthorId(user.getId());
                commentInfo.setText(text);
                commentInfo.setIp(request.getRemoteAddr());
                commentInfo.setType(WCFConst.Types.EVERYONE);
                commentInfo.setCreateTime(DateUtils.getCurrentUnixTime());
                commentService.insertComment(commentInfo);
            } catch (PgSqlException e) {
                return BaseResponse.error(e);
            }
            return BaseResponse.ok("评论发表成功");
        }
    }

    /**
     * @param id
     * @return com.wcf.hellohome.common.response.BaseResponse
     * @note 删除评论
     * @author WCF
     * @time 2018/6/15 19:13
     * @since v1.0
     **/
    @DeleteMapping("/delete/{id}")
    public BaseResponse deleteComment(@PathVariable("id") Integer id) {
        if (null == id) {
            return BaseResponse.error("删除评论失败");
        } else {
            try {
                commentService.deleteById(id);
            } catch (PgSqlException e) {
                return BaseResponse.error(e);
            }
            return BaseResponse.ok("删除评论成功");
        }
    }
}
