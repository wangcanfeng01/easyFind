package com.wcf.hellohome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class HelloHomeApplication extends SpringBootServletInitializer {
    public HelloHomeApplication() {
        super();
        setRegisterErrorPageFilter(false);
    }

    /**
     * 配置到tomcat中必须要加的配置
     * @param application
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(HelloHomeApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(HelloHomeApplication.class, args);
    }
}
