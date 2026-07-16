package org.example.authenticationandauthorization.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Builder
@Entity
@Getter
@Setter
public class  Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pidx;

    private String transactionId;

    private BigDecimal amount;

    private String status;

    private String purchaseOrderId;

    private String purchaseOrderName;

    private LocalDateTime paidAt;

}
