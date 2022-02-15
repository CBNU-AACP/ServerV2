package aacp.server.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);       // json을 자바스크립트에서 처리할 수 있게 할지를 설정, false일 경우 js로 요청시에 가지 않는다
        config.addAllowedOrigin("*");           // 모든 ip의 응답을 허용
        config.addAllowedMethod("*");           // 모든 header의 응답을 허용
        config.addAllowedHeader("*");           // 모든 http request 요청을 허용한다
        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }
}
