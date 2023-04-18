package com.github.ronlievens.spring.todo.service;

import com.github.ronlievens.spring.todo.model.User;
import com.github.ronlievens.spring.todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static com.github.ronlievens.spring.todo.model.enumeration.UserRoleType.AUTHENTICATED;


@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserRequestService userRequestService;
    private final UserRoleService userRoleService;

    public void create(final User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email address already in use.");
        }

        user.setPassHash(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        userRoleService.grant(user.getId(), AUTHENTICATED);
        userRequestService.createVerificationRequest(user);
    }
}
