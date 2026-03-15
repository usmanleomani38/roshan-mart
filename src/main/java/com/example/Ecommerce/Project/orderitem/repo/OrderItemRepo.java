package com.example.Ecommerce.Project.orderitem.repo;

import com.example.Ecommerce.Project.orderitem.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {
}
