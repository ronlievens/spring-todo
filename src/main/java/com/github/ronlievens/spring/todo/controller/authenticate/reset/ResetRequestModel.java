package com.github.ronlievens.spring.todo.controller.authenticate.reset;

import jakarta.validation.constraints.Size;

public record ResetRequestModel(

    @Size(min = 8, max = 255)
    String newPassword
) {
}
