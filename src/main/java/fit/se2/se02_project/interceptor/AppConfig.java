package fit.se2.se02_project.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtCookieInterceptor()).addPathPatterns("/order", "/user");
        registry.addInterceptor(new JwtCheckRoleInterceptor()).addPathPatterns("/api/**", "/admin/**");
    }
}
