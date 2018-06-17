package com.wcf.hellohome.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.pegdown.PegDownProcessor;

/**
 * @author WCF
 * @time 2018/4/9
 * @why markdown数据转换为html
 **/
public class TaleUtils {


    /**
     *@note markdown文字转成html
     *@author WCF
     *@time 2018/6/10 20:52
     *@since v1.0
     * @param markdown
     *@return java.lang.String
     **/
    public static String mdToHtml(String markdown){
        if (StringUtils.isBlank(markdown)) {
            return "";
        }
        PegDownProcessor pdp = new PegDownProcessor(Integer.MAX_VALUE);
        String content=pdp.markdownToHtml(markdown);
        return content;
    }
}
