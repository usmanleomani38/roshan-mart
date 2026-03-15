package com.example.Ecommerce.Project.payement.repo;

import com.example.Ecommerce.Project.payement.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<Payment, Long> {
}
