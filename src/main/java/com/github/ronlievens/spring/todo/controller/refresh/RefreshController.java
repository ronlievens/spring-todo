package com.github.ronlievens.spring.todo.controller.refresh;

import com.github.ronlievens.spring.todo.repository.UserRepository;
import com.github.ronlievens.spring.todo.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/refresh")
@Validated
@RestController
public class RefreshController {

    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<RefreshResponseModel> get() throws Exception {
        val userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
        val user = userRepository.findById(userId);

        if (user.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        final String jwtToken = jwtTokenService.generateToken(user.get());
        return ResponseEntity.ok(new RefreshResponseModel(jwtToken));
    }
}
