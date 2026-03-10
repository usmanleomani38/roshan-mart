package com.example.Ecommerce.Project.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Ecommerce.Project.user.model.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUserName(String userName);
}
