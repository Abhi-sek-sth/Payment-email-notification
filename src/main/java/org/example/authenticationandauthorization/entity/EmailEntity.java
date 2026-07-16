//package org.example.authenticationandauthorization.entity;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import lombok.Getter;
//import lombok.Setter;
//import org.hibernate.annotations.CreationTimestamp;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Getter
//@Setter
//public class EmailEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//   private Long id;
//   private String email;
//   private String otp;
//    private LocalDateTime expiresAt;
//
//    private boolean verified;
//
//    @CreationTimestamp
//    private LocalDateTime createdAt;
//
//}
