package com.sven.wms.web.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 *  浏览器对复杂跨域请求的处理，在发送真正的请求前, 会先发送一个方法为OPTIONS的预请求(preflight request), 用于试探服务端是否能接受真正的请求，如果options获得的回应是拒绝性质的，比如404\403\500等http状态，就会停止post、put等请求的发出。

 有三种方式会导致这种现象：
 1、请求方法不是GET/HEAD/POST
 2、POST请求的Content-Type并非application/x-www-form-urlencoded, multipart/form-data, 或text/plain
 3、请求设置了自定义的header字段

 解决方法：
 设置Access-Control-Max-Age，，，，，预检一次设置一个有效期，在有效期内不再重复预检。
 */

/**
 * 跨域设置,替换到filter中的SimpleCORSFilter
 * ajax Content-Type: application/json,Accept: application/json的设置需要设置为可识别的格式
 */
@Configuration
public class CorsConfig {
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // 1
        corsConfiguration.addAllowedHeader("*"); // 2
        corsConfiguration.addAllowedMethod("*"); // 3
        corsConfiguration.setAllowCredentials(true);
		//设置预检
        corsConfiguration.setMaxAge(3600l);

        return corsConfiguration;
    }
    
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); // 4
        return new CorsFilter(source);
    }
}
