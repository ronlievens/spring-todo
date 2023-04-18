package com.github.ronlievens.spring.todo.security;

import com.auth0.jwt.JWT;
import com.github.ronlievens.spring.todo.config.TodoProperties;
import com.github.ronlievens.spring.todo.util.RsaKeyUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private static final String SPRING_SEC_PREFIX = "ROLE_";
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String HEADER_STRING = "Authorization";

    private final TodoProperties todoProperties;

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {

        val header = request.getHeader(HEADER_STRING);

        if (isNotBlank(header) && header.startsWith(TOKEN_PREFIX)) {
            // Login spring security
            SecurityContextHolder.getContext().setAuthentication(getAutorisaties(request));
        }

        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAutorisaties(final HttpServletRequest request) {
        try {
            val token = request.getHeader(HEADER_STRING);
            if (isNotBlank(token)) {
                val jwt = JWT.require(RsaKeyUtils.getAlgorithm(todoProperties.getPublicKey(), null))
                    .withIssuer(todoProperties.getRealm())
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""));
                val user = jwt.getSubject();
                if (isNotBlank(user)) {
                    return new UsernamePasswordAuthenticationToken(user,
                        null,
                        loadRoles(jwt.getClaim("roles").asArray(String.class)));
                }
            }
        } catch (Exception e) {
            log.error("Exception: {}", e.getMessage());
        }
        return null;
    }

    private List<GrantedAuthority> loadRoles(final String[] roles) {
        val result = new ArrayList<GrantedAuthority>();
        for (final String role : roles) {
            result.add(new SimpleGrantedAuthority(SPRING_SEC_PREFIX + role.toUpperCase()));
        }
        return result;
    }
}
