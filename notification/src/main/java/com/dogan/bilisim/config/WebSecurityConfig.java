package com.dogan.bilisim.config;


import com.dogan.bilisim.exception.FlowCrmErrorResponseHandler;
import com.dogan.bilisim.interceptor.RequestLoggingFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@Component
@EnableWebSecurity
@Order(2)
@RequiredArgsConstructor
public class WebSecurityConfig {


    @Lazy
    @Autowired
    private FlowCrmErrorResponseHandler flowCrmErrorResponseHandler;
    @Lazy
    @Autowired
    private RequestLoggingFilter requestLoggingFilter;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/ws/**").permitAll()
                        .requestMatchers("/api/v1/notification/send-message").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer.accessDeniedHandler(flowCrmErrorResponseHandler)
                )
                .addFilterBefore(requestLoggingFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    SimpleUrlAuthenticationSuccessHandler successHandler() {
        final SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler();
        successHandler.setRedirectStrategy(new NoRedirectStrategy());
        return successHandler;
    }

    @Bean
    public CorsFilter corsFilter(@Autowired MvcCorsProperties mvcCorsProperties) {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        source.registerCorsConfiguration("/**", config);

        if (!mvcCorsProperties.getMappings().isEmpty()) {
            source.setCorsConfigurations(mvcCorsProperties.getMappings());
        }

        return new CorsFilter(source);
    }
}
