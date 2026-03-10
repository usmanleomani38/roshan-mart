package com.example.Ecommerce.Project.role.repo;

import com.example.Ecommerce.Project.role.model.AppRole;
import com.example.Ecommerce.Project.role.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo  extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole appRole);
}
