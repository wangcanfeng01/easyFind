package com.wcf.hellohome.read.dao.provider;

import com.wcf.hellohome.common.utils.ObjectUtils;
import com.wcf.hellohome.read.model.WcfArticleInfo;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author WCF
 * @time 2018/5/23
 * @why 动态生成修改文章语句
 **/
public class ArticleProvider {
    public String updateArticle(WcfArticleInfo articleInfo){
        String sql= new SQL() {{
            UPDATE("info_article");
            if(!ObjectUtils.isEmpty(articleInfo.getModifyTime())){
                SET(" modify_time = '"+articleInfo.getModifyTime()+"'");
            }
            if(!ObjectUtils.isEmpty(articleInfo.getTitle())){
                SET(" title = '"+articleInfo.getTitle()+"'");
            }
            if(!ObjectUtils.isEmpty(articleInfo.getStatus())){
                SET(" status = '"+articleInfo.getStatus()+"'");
            }
            if(!ObjectUtils.isEmpty(articleInfo.getText())){
                String text=articleInfo.getText();
               text=text.replaceAll("'", "''");
                SET(" text = '"+text+"'");
            }
            if(!ObjectUtils.isEmpty(articleInfo.getKeywords())){
                SET(" keywords = '"+articleInfo.getKeywords()+"'");
            }
            if(!ObjectUtils.isEmpty(articleInfo.getCategories())){
                SET(" categories = '"+articleInfo.getCategories()+"'");
            }
            if(!ObjectUtils.isEmpty(articleInfo.getAllowSee())){
                SET(" allow_see = '"+articleInfo.getAllowSee()+"'");
            }
            if(!ObjectUtils.isEmpty(articleInfo.getAllowComment())){
                SET(" allow_comment = "+articleInfo.getAllowComment());
            }
            WHERE("id="+articleInfo.getId());
        }}.toString();
        return sql;
    }

}
