package org.example.authenticationandauthorization.repository;

import org.example.authenticationandauthorization.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByRoleName(String name);
}
