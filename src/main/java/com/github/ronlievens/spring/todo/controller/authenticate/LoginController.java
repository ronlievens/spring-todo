package com.github.ronlievens.spring.todo.controller.authenticate;

import com.github.ronlievens.spring.todo.repository.UserRepository;
import com.github.ronlievens.spring.todo.service.JwtTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@Slf4j
@RequestMapping("/authenticate")
@Validated
@RestController
public class LoginController {

    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional(readOnly = true)
    @PostMapping
    public ResponseEntity<LoginResponseModel> login(@RequestBody @Valid final LoginRequestModel model) throws Exception {
        val user = userRepository.findByEmail(model.email());

        if (user.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (!passwordEncoder.matches(model.password(), user.get().getPassHash())) {
            return ResponseEntity.badRequest().build();
        }

        final String jwtToken = jwtTokenService.generateToken(user.get());
        return ResponseEntity.ok(new LoginResponseModel(jwtToken));
    }
}

