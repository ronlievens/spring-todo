package com.github.ronlievens.spring.todo.repository;

import com.github.ronlievens.spring.todo.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Transactional(readOnly = true)
    boolean existsByEmail(String email);

    @Transactional(readOnly = true)
    Optional<User> findByEmail(String email);

    @Modifying
    @Query("UPDATE User u SET u.passHash=:passHash WHERE u.id=:id")
    void updatePassHash(@Param("id") Long id, @Param("passHash") String passHash);
}
