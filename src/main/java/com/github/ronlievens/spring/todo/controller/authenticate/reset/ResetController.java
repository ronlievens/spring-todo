package com.github.ronlievens.spring.todo.controller.authenticate.reset;

import com.github.ronlievens.spring.todo.service.UserRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@Slf4j
@RequestMapping("/authenticate/reset")
@Validated
@RestController
public class ResetController {

    private final UserRequestService userRequestService;

    @Transactional
    @PutMapping(value = "/{token}")
    public void resetPassword(@PathVariable final String token, @RequestBody @Valid final ResetRequestModel model) {
        userRequestService.changePassword(token, model.newPassword());
    }
}
