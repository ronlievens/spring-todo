package com.github.ronlievens.spring.todo.controller.authenticate;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestModel(
    @Email
    String email,
    @NotBlank
    String password) {
}
