package com.github.ronlievens.spring.todo.controller.authenticate.forget;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ForgetRequestModel(
    @NotBlank
    @Email
    String email
) {
}
