package org.example.authenticationandauthorization.repository;

import org.example.authenticationandauthorization.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long>{

    Optional<Users> findByUsername(String username);



    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);
}
