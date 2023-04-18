package com.github.ronlievens.spring.todo.controller.signup;

import com.github.ronlievens.spring.todo.model.User;
import com.github.ronlievens.spring.todo.model.enumeration.LanguageType;
import com.github.ronlievens.spring.todo.service.UserRequestService;
import com.github.ronlievens.spring.todo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

import static org.springframework.web.servlet.support.RequestContextUtils.getLocale;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/signup")
@Validated
@RestController
class SignupController {

    private final UserService userService;
    private final UserRequestService userRequestService;

    @Transactional
    @PostMapping
    public void post(@Valid @RequestBody final SignupModel model, final HttpServletRequest request) {
        val newUser = User.builder()
            .email(model.email())
            .firstName(model.firstName())
            .infix(model.infix())
            .lastName(model.lastName())
            .languageCode(LanguageType.of(getLocale(request)))
            .password(model.password())
            .roles(new HashSet<>())
            .build();
        userService.create(newUser);
    }

    @Transactional
    @PutMapping(value = "/{token}")
    public void resetPassword(@PathVariable final String token) {
        userRequestService.verification(token);
    }
}
