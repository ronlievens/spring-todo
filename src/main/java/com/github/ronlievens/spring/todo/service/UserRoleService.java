package com.github.ronlievens.spring.todo.service;

import com.github.ronlievens.spring.todo.model.User;
import com.github.ronlievens.spring.todo.model.enumeration.UserRoleType;
import com.github.ronlievens.spring.todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserRoleService {
    private final UserRepository userRepository;

    @Transactional
    public void grant(final Long memberId, final UserRoleType role) {
        final Optional<User> user = userRepository.findById(memberId);
        if (user.isPresent()) {
            user.get().getRoles().add(role);
            userRepository.save(user.get());
        }
    }

    @Transactional
    public void revoke(final Long memberId, final UserRoleType role) {
        final Optional<User> user = userRepository.findById(memberId);
        if (user.isPresent()) {
            user.get().getRoles().remove(role);
            userRepository.save(user.get());
        }
    }
}
