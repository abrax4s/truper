package com.examen.truper.truper.common.config;

import com.examen.truper.truper.common.filter.JwtAuthFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ServletConfig {

    private final static String PURCHASE_ENDPOINTS = "/*";

    private final static List<String> URL_EXCLUSIONS = List.of("/login");

    @Bean
    public FilterRegistrationBean<JwtAuthFilter> filterFilterRegistrationBean() {
        final FilterRegistrationBean<JwtAuthFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new JwtAuthFilter(URL_EXCLUSIONS));
        registration.addUrlPatterns(PURCHASE_ENDPOINTS);
        registration.setOrder(1);

        return registration;
    }
}
