package Startup;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Class Class used for Web Security configurations required for the tokens' functionality.
 */
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    /**
     * Function to add a customized interceptor for requests before reaching the endpoints themselves.
     * @param registry Entity.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        try {
            registry.addInterceptor(new CustomRequestInterceptor()).addPathPatterns("/**");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function to configure http security.
     * @param http Http entity.
     * @throws Exception Exception thrown in case of error.
     */
    @Override
    protected void configure(@NotNull HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/**")
                .permitAll();
        http.csrf().disable();
    }

    /**
     * Function that add a mapping to the allowed methods.
     * @param registry Entity.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*");
    }
}