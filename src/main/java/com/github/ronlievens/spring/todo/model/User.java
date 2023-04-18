package com.github.ronlievens.spring.todo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.ronlievens.spring.todo.model.enumeration.GenderType;
import com.github.ronlievens.spring.todo.model.enumeration.LanguageType;
import com.github.ronlievens.spring.todo.model.enumeration.UserRoleType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Set;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.EAGER;
import static org.apache.logging.log4j.util.Strings.isNotBlank;

@DynamicUpdate
@DynamicInsert
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "GEBRUIKERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String infix;
    private String lastName;
    @Column(nullable = false)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String passHash;

    @JsonIgnore
    @Enumerated(STRING)
    private LanguageType languageCode;
    @JsonIgnore
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    @JsonIgnore
    @Enumerated(value = STRING)
    private GenderType gender;

    @JsonIgnore
    @Column(insertable = false, updatable = false)
    private OffsetDateTime createdOn;
    @JsonIgnore
    @Column(insertable = false)
    private OffsetDateTime disabledOn;

    @JsonIgnore
    @ElementCollection(fetch = EAGER)
    @CollectionTable(name = "GEBRUIKER_ROLLEN", joinColumns = @JoinColumn(name = "USER_ID"))
    @Column(name = "AUTHORITY")
    @Enumerated(STRING)
    private Set<UserRoleType> roles;

    @JsonIgnore
    @Transient
    private String password;

    // --[ CUSTOM GETTERS AND SETTERS ]---------------------------------------------------------------------------------
    @JsonIgnore
    public String getFullName() {
        var fullName = new StringBuilder();
        if (isNotBlank(firstName)) {
            fullName.append(firstName);
            fullName.append(" ");
        }
        if (isNotBlank(infix)) {
            fullName.append(infix);
            fullName.append(" ");
        }
        if (isNotBlank(lastName)) {
            fullName.append(lastName);
        }
        return fullName.toString();
    }
}
