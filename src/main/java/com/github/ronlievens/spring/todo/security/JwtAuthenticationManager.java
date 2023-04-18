package com.github.ronlievens.spring.todo.security;

import com.github.ronlievens.spring.todo.config.TodoProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@RequiredArgsConstructor
public class JwtAuthenticationManager extends AbstractHttpConfigurer<JwtAuthenticationManager, HttpSecurity> {

    private final TodoProperties todoProperties;

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http.addFilterBefore(new JwtAuthorizationFilter(todoProperties), BasicAuthenticationFilter.class);
    }

}
