package attendance.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    @Profile("dev")
    public WebMvcConfigurer devcorsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(
                                "http://localhost:63342",
                                "http://127.0.0.1:63342",
                                "http://localhost:3000",
                                "http://localhost:8000",
                                "http://localhost:5500",
                                "http://localhost:5173",
                                "file://"
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                        .allowedHeaders("*")
                        .exposedHeaders("Authorization", "Content-Type")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }
    @Value("${app.frontend.url:https://yourcompany.com}")
    private String productionFrontendUrl;

    @Bean
    @Profile("prod")
    public WebMvcConfigurer prodCorsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**") // Restrict to API paths only
                        .allowedOrigins(productionFrontendUrl) // Strict corporate domain
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH") // Drop OPTIONS if handled by gateway
                        .allowedHeaders("Authorization", "Content-Type") // Explicitly define safe headers
                        .exposedHeaders("Authorization")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }



}