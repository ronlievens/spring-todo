package com.github.ronlievens.spring.todo.controller.account;


import com.github.ronlievens.spring.todo.model.User;
import com.github.ronlievens.spring.todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/account")
@Validated
@RestController
public class AccountController {

    private final UserRepository userRepository;

    @GetMapping
    public Optional<User> get() {
        val userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
        return userRepository.findById(userId);
    }

    @GetMapping("/{id}")
    public Optional<User> getById(@PathVariable("id") final Long userId) {
        return userRepository.findById(userId);
    }
}
