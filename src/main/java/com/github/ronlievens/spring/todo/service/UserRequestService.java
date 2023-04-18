package com.github.ronlievens.spring.todo.service;

import com.github.ronlievens.spring.todo.model.User;
import com.github.ronlievens.spring.todo.model.UserToken;
import com.github.ronlievens.spring.todo.model.enumeration.UserTokenType;
import com.github.ronlievens.spring.todo.repository.UserRepository;
import com.github.ronlievens.spring.todo.repository.UserTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.UUID;

import static com.github.ronlievens.spring.todo.model.enumeration.UserRoleType.AUTHENTICATED;
import static com.github.ronlievens.spring.todo.model.enumeration.UserRoleType.VERIFIED;
import static com.github.ronlievens.spring.todo.model.enumeration.UserTokenType.PASSWORD_RESET;
import static com.github.ronlievens.spring.todo.model.enumeration.UserTokenType.VERIFICATION;
import static java.lang.String.format;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserRequestService {

    private static final int TOKEN_PASSES = 2;
    private static final int EXPIRES_IN_24_HOURS = 24;
    private static final int EXPIRES_IN_1_WEEK = 168;

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepository;
    private final UserRoleService userRoleService;
    private final MessageService messageService;

    // --[ VERIFY MAIL ]------------------------------------------------------------------------------------------------
    public void createVerificationRequest(final User user) {
        createRequest(user, VERIFICATION, EXPIRES_IN_1_WEEK);
    }

    public void verification(final String token) {
        val userRequest = lookup(token, VERIFICATION);
        userRoleService.grant(userRequest.getUserId(), VERIFIED);
        userRoleService.revoke(userRequest.getUserId(), AUTHENTICATED);
        userTokenRepository.delete(userRequest);
    }

    // --[ RESET PASSWORD ]---------------------------------------------------------------------------------------------
    public void createResetPasswordRequest(final String email) {
        val user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, format("No user found with email address: {}", email));
        }
        createRequest(user.get(), PASSWORD_RESET, EXPIRES_IN_24_HOURS);
    }

    public void changePassword(final String token, final String newPassword) {
        val userRequest = lookup(token, PASSWORD_RESET);
        val newPassHash = passwordEncoder.encode(newPassword);
        userRepository.updatePassHash(userRequest.getUserId(), newPassHash);
        userTokenRepository.delete(userRequest);
    }

    // --[ LOGIC ]------------------------------------------------------------------------------------------------------
    private void createRequest(final User user,
                               final UserTokenType userTokenType,
                               final int expireHours) {
        val createdOn = OffsetDateTime.now();
        val expire = createdOn.plusHours(expireHours);
        val userRequest = userTokenRepository.save(UserToken.builder()
            .type(userTokenType)
            .userId(user.getId())
            .value(generateToken())
            .validUntil(expire)
            .createdOn(createdOn)
            .build());
        messageService.createMessage(userRequest, user);
    }

    private UserToken lookup(final String token, final UserTokenType userTokenType) {
        val userRequest = userTokenRepository.lookup(token, OffsetDateTime.now());
        if (userRequest.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, format("Token %s not found or expired.", token));
        }

        if (!userTokenType.equals(userRequest.get().getType())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, format("Request has wrong type %s.", userRequest.get().getType()));
        }

        return userRequest.get();
    }

    private static String generateToken() {
        val sb = new StringBuilder();
        for (int i = 0; i < TOKEN_PASSES; i++) {
            sb.append(UUID.randomUUID().toString().replaceAll("-", ""));
        }
        return sb.toString();
    }
}
