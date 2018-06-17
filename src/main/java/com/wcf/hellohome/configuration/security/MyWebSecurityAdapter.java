package com.wcf.hellohome.configuration.security;

import com.wcf.hellohome.configuration.handler.WCFAuthenticationSuessHandler;
import com.wcf.hellohome.read.service.WcfOperationLogService;
import com.wcf.hellohome.user.service.impl.UserDetailsServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author WCF
 * @time 2018/1/28
 * @why 用户安全配置
 **/
@Configuration
@EnableWebSecurity
@Log4j2
public class MyWebSecurityAdapter extends WebSecurityConfigurerAdapter {

    /**
     * 用户服务
     */
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    /**
     * 用户鉴定
     */
    @Autowired
    private MyAuthenticationProvider myAuthenticationProvider;

    /**
     * 操作日志服务
     */
    @Autowired
    private WcfOperationLogService operationLogService;

    /**
     * 配置授权提供
     *
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(myAuthenticationProvider);

        //如果直接使用默认的DaoAuthenticationProvider,就需要配置UserDetailsService，
        // 其中实现的loadUserByUsername方法，如果使用自己的provider就不必配置，因为密码验证是自己实现的，不是托管的
        //   auth.userDetailsService(this.mySafeDetailService);
    }

    /**
     * @param http
     * @return void
     * @note 各种路径设置
     * @author WCF
     * @time 2018/6/12 0:13
     * @since v1.0
     **/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/read/all/**").permitAll()
//                .antMatchers("/read/admin/**").hasRole("11")
                .antMatchers("/login").permitAll()
                .antMatchers("/user/**", "/register").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/home", true).successHandler(new WCFAuthenticationSuessHandler(operationLogService))
                .failureUrl("/login?error=true").and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .and()
                .rememberMe()
//                .tokenRepository(persistentTokenRepository())
//                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                .userDetailsService(userDetailsServiceImpl).and()
                .httpBasic();
    }

    /**
     * @param web
     * @return void
     * @note 静态资源不进行拦截
     * @author WCF
     * @time 2018/6/12 0:15
     * @since v1.0
     **/
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.
                ignoring().antMatchers("/resources/static/**")
                .and().ignoring().antMatchers("/image/**")
                .and().ignoring().antMatchers("/music/**")
                .and().ignoring().antMatchers("/webjars/**")
                .and().ignoring().antMatchers("/jsmind/**")
                .and().ignoring().antMatchers("/js/**")
                .and().ignoring().antMatchers("/css/**")
                .and().ignoring().antMatchers("/test/**")
                .and().ignoring().antMatchers("/files/**");
    }


}
