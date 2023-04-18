package com.github.ronlievens.spring.todo.service;

import com.auth0.jwt.JWT;
import com.github.ronlievens.spring.todo.config.TodoProperties;
import com.github.ronlievens.spring.todo.model.User;
import com.github.ronlievens.spring.todo.model.enumeration.UserRoleType;
import com.github.ronlievens.spring.todo.util.RsaKeyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
@Service
public class JwtTokenService {
    private static final int EXPIRATION_OFFSET_IN_HOURS = 24;

    private final TodoProperties properties;

    public String generateToken(final User user) throws Exception {
        val expire = OffsetDateTime.now().plusHours(EXPIRATION_OFFSET_IN_HOURS);
        return JWT.create()
            .withSubject(user.getId().toString())
            .withIssuer(properties.getRealm().toString())
            .withExpiresAt(Date.from(expire.toInstant()))
            .withClaim("firstName", user.getFirstName())
            .withClaim("infix", user.getInfix())
            .withClaim("lastName", user.getLastName())
            .withArrayClaim("roles", getRoles(user.getRoles()))
            .sign(RsaKeyUtils.getAlgorithm(null, properties.getPrivateKey()));
    }

    private String[] getRoles(final Set<UserRoleType> roles) {
        val result = new ArrayList<String>();
        for (val role : roles) {
            result.add(role.toString().toLowerCase());
        }
        return result.toArray(new String[result.size()]);
    }
}
