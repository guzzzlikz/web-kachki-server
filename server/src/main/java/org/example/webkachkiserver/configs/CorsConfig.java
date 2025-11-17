package org.example.webkachkiserver.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins("http://localhost:8080",
                        "http://localhost:5173",
                        "https://web-kachki-server-latest.onrender.com",
                        "https://test-server-kachki.vercel.app",
                        "https://web-kachki-client2.vercel.app",
                        "https://reqbin.com/post-online");
    }
}
