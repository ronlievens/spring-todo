package com.github.ronlievens.spring.todo;

import lombok.val;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodoApplicationStarter {

    public static void main(final String[] args) {
        val application = new SpringApplication(TodoApplicationStarter.class);
        application.run(args);
    }
}
