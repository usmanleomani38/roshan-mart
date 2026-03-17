package com.example.Ecommerce.Project.payment.repo;

import com.example.Ecommerce.Project.payment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<Payment, Long> {
}
