package com.github.ronlievens.spring.todo.controller.authenticate.forget;

import com.github.ronlievens.spring.todo.service.UserRequestService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@Slf4j
@RequestMapping("/authenticate/forget")
@Validated
@RestController
public class ForgetController {

    private final UserRequestService userRequestService;

    @Transactional
    @PostMapping
    public void forget(@RequestBody @Valid final ForgetRequestModel model, final HttpServletRequest request) {
        try {
            userRequestService.createResetPasswordRequest(model.email());
        } catch (EmptyResultDataAccessException erdae) {
            log.trace("No user found for email [{}] requested by [{}]!",
                model.email(),
                request.getRemoteAddr());
        }
    }
}
