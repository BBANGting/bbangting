package com.khu.bbangting.config;

import com.khu.bbangting.domain.notification.interceptor.NotificationInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.StaticResourceLocation;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final NotificationInterceptor notificationInterceptor;

    @Value("${uploadPath}")
    String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations(uploadPath);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) { // static location 제외
        List<String> staticResourcesPath = Stream.of(StaticResourceLocation.values())
                .flatMap(StaticResourceLocation::getPatterns)
                .collect(Collectors.toList());
        staticResourcesPath.add("/node_modules/**");
        registry.addInterceptor(notificationInterceptor)
                .excludePathPatterns(staticResourcesPath);
    }

}