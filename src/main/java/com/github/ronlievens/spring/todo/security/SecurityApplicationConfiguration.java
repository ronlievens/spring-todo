package com.github.ronlievens.spring.todo.security;

import com.github.ronlievens.spring.todo.config.TodoProperties;
import com.github.ronlievens.spring.todo.filter.LogFilter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityApplicationConfiguration {

    private static final String DEFAULT_ENCODING = "UTF-8";

    private final TodoProperties todoProperties;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ReloadableResourceBundleMessageSource mailSource() {
        val messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/languages/mail");
        messageSource.setDefaultEncoding(DEFAULT_ENCODING);
        return messageSource;
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        // Disable CSRF (rely on JWT)
        http.csrf().disable();

        // Add log filter
        http.addFilterBefore(new LogFilter(), WebAsyncManagerIntegrationFilter.class);

        // Enable Content Security Policy
        http.headers(headers -> headers.contentSecurityPolicy(contentSecurityPolicy -> contentSecurityPolicy.policyDirectives("default-src 'self'; img-src 'self' data:")));

        // Entry points
        http.authorizeHttpRequests()
            .requestMatchers("/actuator",
                "/actuator/**",
                "/v3/**",
                "/authenticate",
                "/authenticate/**",
                "/signup",
                "/signup/**")
            .permitAll()
            .anyRequest()
            .authenticated();

        http.apply(new JwtAuthenticationManager(todoProperties));

        return http.build();
    }
}
