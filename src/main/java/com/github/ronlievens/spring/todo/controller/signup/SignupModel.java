package com.github.ronlievens.spring.todo.controller.signup;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

record SignupModel(
    @Email
    @NotBlank
    String email,
    @NotBlank
    @Size(max = 255)
    String firstName,
    @Size(max = 15)
    String infix,
    @NotBlank
    @Size(max = 255)
    String lastName,
    @NotBlank
    @Size(min = 8, max = 255)
    String password) {
}
