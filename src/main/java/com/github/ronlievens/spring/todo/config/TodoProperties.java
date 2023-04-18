package com.github.ronlievens.spring.todo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("todo")
@Getter
@Setter
public class TodoProperties {

    private String url;
    private String supportEmail;
    private String realm;
    private String publicKey;
    private String privateKey;
}
