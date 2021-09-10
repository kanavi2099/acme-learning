package com.unosquare.acmelearning.repository;

import com.unosquare.acmelearning.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Boolean existsByUsername(String username);
    Optional<User> findByUsernameAndPassword(String username, String password);
}
