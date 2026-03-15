package com.example.Ecommerce.Project.order.dtos;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {

    private Long orderId;
    private String email;
    private List<OrderItemDTO> orderItems;
    private LocalDateTime orderDate;
    private PaymentDTO payment;
    private Double totalAmount;
    private String status;
    private Long addressId;

}
