package org.example.provider.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/") // все эндпоинты, начинающиеся с /api
                .allowedOrigins("http://localhost:5173") // разрешаем фронтенд
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // какие методы разрешены
                .allowedHeaders("*") // любые заголовки
                .exposedHeaders("Authorization"); // можно добавить нужные заголовки

    }
}