package com.itdfq.upload.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: GodChin
 * @Date: 2021/12/29 11:14
 * @Blog: itdfq.com
 * @QQ: 909256107
 * @Description:
 */
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {
    @Bean
    public VisitWebInterceptor webInterceptor() {
        return new VisitWebInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webInterceptor()).addPathPatterns("/**").order(1);
    }

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 设置允许的网站域名，如果全允许则设为 *
        corsConfiguration.addAllowedOrigin("*");
        // 如果要限制 HEADER 或 METHOD
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }


    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }
}
