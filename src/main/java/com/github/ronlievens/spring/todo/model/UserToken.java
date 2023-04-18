package com.github.ronlievens.spring.todo.model;

import com.github.ronlievens.spring.todo.model.enumeration.UserTokenType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.OffsetDateTime;

@DynamicUpdate
@DynamicInsert
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "GEBRUIKER_TOKENS")
public class UserToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserTokenType type;
    @Column(nullable = false)
    private String value;

    @Column(nullable = false)
    private OffsetDateTime validUntil;

    @Column(updatable = false)
    private OffsetDateTime createdOn;
    @Column(insertable = false)
    private OffsetDateTime disabledOn;
}
