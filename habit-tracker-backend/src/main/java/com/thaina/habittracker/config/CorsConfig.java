package com.thaina.habittracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration //indicando que essa classe terá configurações do Spring
public class CorsConfig {

    @Bean //este objeto será gerenciado pelo Spring
    public WebMvcConfigurer corsConfigurer(){ //essa interface permite configurar o comportamento do spring mvc -> cors, interceptadores, paths...

        return new WebMvcConfigurer(){

            @Override
            public void addCorsMappings(CorsRegistry registry){ //quais origens podem acessar a API?

                registry.addMapping("/**") //a regra vale para todos os endpoints
                        .allowedOrigins("http://127.0.0.1:5500")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
            }

        };

    }

}