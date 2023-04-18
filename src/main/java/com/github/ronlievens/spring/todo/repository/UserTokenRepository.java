package com.github.ronlievens.spring.todo.repository;

import com.github.ronlievens.spring.todo.model.UserToken;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Optional;

public interface UserTokenRepository extends CrudRepository<UserToken, Long> {

    @Transactional(readOnly = true)
    @Query("SELECT ut FROM UserToken ut WHERE ut.value = :value AND :now < ut.validUntil AND (ut.disabledOn is null OR :now<ut.disabledOn)")
    Optional<UserToken> lookup(@Param("value") String value,
                               @Param("now") OffsetDateTime now);
}
