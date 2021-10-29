package com.imooc.config;

import com.mysql.cj.CoreSession;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.filter.CorsFilter;

/**
 * 前后端设置跨域配置
 * @author XARQL
 * @version 2021-09-24
 */
@Configuration
public class CorsConfig {
    public CorsConfig(){

    }
    @Bean
    public CorsFilter crosFilter(){
         //1.添加cors配置信息
        CorsConfiguration config=new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:8080");
        //设置是否发送cookie 信息
        config.setAllowCredentials(true);
        //设置允许请求的方式
        config.addAllowedMethod("*");
        //设置允许的header
        config.addAllowedHeader("*");
          //2.为url 添加映射路径
        UrlBasedCorsConfigurationSource corsSource=new UrlBasedCorsConfigurationSource();
        corsSource.registerCorsConfiguration("/**",config);
          //3.返回重新定义好的corsSource
        return new CorsFilter(corsSource);
    }
}
