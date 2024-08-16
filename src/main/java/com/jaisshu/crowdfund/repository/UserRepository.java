package com.jaisshu.crowdfund.repository;

import com.jaisshu.crowdfund.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query(
            value = "SELECT * FROM users u WHERE u.email = ?1 and u.password = ?2",
            nativeQuery = true)
    Optional<User> getLoggedInUser(String email, String password);

    Optional<User> findByUserId(UUID userId);
}