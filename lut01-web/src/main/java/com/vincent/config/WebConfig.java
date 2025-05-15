// ✅ WebConfig.java
package com.vincent.config;

import com.vincent.config.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private com.vincent.config.JwtInterceptor jwtInterceptor;

    /** CORS 全局配置 */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:8080",        // 本地开发前端
                        "http://10.0.2.2:8080"          // 鸿蒙模拟器访问宿主机
                )

                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                // 让浏览器可以拿到自定义响应头（如 JWT、文件下载名）
                .exposedHeaders("Authorization", "Content-Disposition")
                .allowCredentials(true)               // 需要携带 Cookie / Authorization
                .maxAge(3600);
    }



    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/",
                        "/login",
                        "/register",
                        "/files/**",
                        "/api/harmony/user/login",
                        "/api/harmony/greenhouse/*/toggle/*",
                        "/uploads/**",            // ← 静态资源
                        "/error", "/error/**"     // ← Spring 的错误页
                );
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/uploads/");
    }
}
