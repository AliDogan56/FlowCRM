package com.dogan.bilisim.config;


import com.dogan.bilisim.auth.jwt.JwtAuthenticationProvider;
import com.dogan.bilisim.auth.jwt.JwtTokenAuthenticationProcessingFilter;
import com.dogan.bilisim.auth.rest.RestAuthenticationProvider;
import com.dogan.bilisim.auth.rest.RestLoginProcessingFilter;
import com.dogan.bilisim.exception.FlowCrmErrorResponseHandler;
import com.dogan.bilisim.interceptor.RequestLoggingFilter;
import com.dogan.bilisim.service.auth.jwt.JwtHeaderTokenExtractor;
import com.dogan.bilisim.service.auth.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@Component
@EnableWebSecurity
@Order(2)
@RequiredArgsConstructor
public class WebSecurityConfig {


    private static final String FORM_BASED_LOGIN_ENTRY_POINT = "/api/v1/auth/login";

    private static final RequestMatcher JWT_PROTECTED_REQUEST_MATCHER = new NegatedRequestMatcher(new OrRequestMatcher(Stream.of(FORM_BASED_LOGIN_ENTRY_POINT).map(AntPathRequestMatcher::new).collect(Collectors.toList())));

    @Lazy
    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;
    @Lazy
    @Autowired
    private RestAuthenticationProvider restAuthenticationProvider;
    @Lazy
    @Autowired
    private FlowCrmErrorResponseHandler flowCrmErrorResponseHandler;
    @Lazy
    @Autowired
    @Qualifier("defaultAuthenticationSuccessHandler")
    private AuthenticationSuccessHandler successHandler;
    @Lazy
    @Autowired
    @Qualifier("defaultAuthenticationFailureHandler")
    private AuthenticationFailureHandler failureHandler;
    @Lazy
    @Autowired
    private JwtHeaderTokenExtractor jwtHeaderTokenExtractor;
    @Lazy
    @Autowired
    private ObjectMapper objectMapper;
    @Lazy
    @Autowired
    private JwtUtil jwtUtil;
    @Lazy
    @Autowired
    private RequestLoggingFilter requestLoggingFilter;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        .anyRequest().authenticated())
                .authenticationProvider(jwtAuthenticationProvider)
                .authenticationProvider(restAuthenticationProvider)
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer.accessDeniedHandler(flowCrmErrorResponseHandler)
                )
                .addFilterBefore(requestLoggingFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(buildRestLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    protected RestLoginProcessingFilter buildRestLoginProcessingFilter() {
        RestLoginProcessingFilter filter =
                new RestLoginProcessingFilter(FORM_BASED_LOGIN_ENTRY_POINT,
                        successHandler, failureHandler, objectMapper);
        filter.setAuthenticationManager(new ProviderManager(restAuthenticationProvider));
        return filter;
    }

    @Bean
    protected JwtTokenAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter() {
        JwtTokenAuthenticationProcessingFilter filter
                = new JwtTokenAuthenticationProcessingFilter(failureHandler, jwtHeaderTokenExtractor, JWT_PROTECTED_REQUEST_MATCHER, jwtUtil);
        filter.setAuthenticationManager(new ProviderManager(jwtAuthenticationProvider));
        return filter;
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
