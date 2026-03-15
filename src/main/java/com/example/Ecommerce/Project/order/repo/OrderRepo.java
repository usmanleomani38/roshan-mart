package com.example.Ecommerce.Project.order.repo;

import com.example.Ecommerce.Project.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long> {
}
