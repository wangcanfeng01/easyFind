package com.wcf.hellohome.configuration.commons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author WCF
 * @time 2018/4/7
 * @why 注册拦截器
 **/
@Configuration
public class WebMvcAdapter extends WebMvcConfigurerAdapter{

    /**
     * 注入拦截器
     */
    @Autowired
    private BaseInterceptor baseInterceptor;

    /**
     *@note 将拦截器加入到拦截链中
     *@author WCF
     *@time 2018/6/11 23:49
     *@since v1.0
     * @param registry
     *@return void
     **/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(baseInterceptor);
    }
}
