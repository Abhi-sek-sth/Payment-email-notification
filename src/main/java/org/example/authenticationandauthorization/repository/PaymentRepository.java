package org.example.authenticationandauthorization.repository;

import org.example.authenticationandauthorization.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    Optional<Payment> findByPidx(String pidx);
}
