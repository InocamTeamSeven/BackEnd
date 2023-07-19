package com.spring.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // Cors 보안 오픈
        config.addAllowedOriginPattern("*"); // 주소 제한
        config.addAllowedHeader("*"); // 헤더 전송 제한
        config.addAllowedMethod("*"); // GET, POST 같은 메서드 제한
        config.addExposedHeader("*"); // 내가 보내는 헤더 제한
        source.registerCorsConfiguration("/**",config ); // 이대로 설정
        return new CorsFilter(source);
    }
}