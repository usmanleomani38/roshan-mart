package com.example.Ecommerce.Project.order.dtos;

import com.example.Ecommerce.Project.order.model.Order;
import com.example.Ecommerce.Project.orderitem.model.OrderItem;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private Integer totalItem;
    private LocalDateTime orderDate;
    private PaymentDTO payment;
    private Double totalAmount;
    private String status;
    private Long addressId;

    public static OrderDTO toDTO(Order savedOrder) {

        List<OrderItemDTO> orderItemDTOList = new ArrayList<>();
        for(OrderItem item : savedOrder.getOrderItemsList())
            orderItemDTOList.add(OrderItemDTO.toDTO(item));


        return OrderDTO.builder()
                .orderId(savedOrder.getOrderId())
                .email(savedOrder.getEmail())
                .orderItems(orderItemDTOList)
                .totalItem(orderItemDTOList.size())
                .orderDate(savedOrder.getOrderDate())
                .payment(PaymentDTO.toDTO(savedOrder.getPayment()))
                .totalAmount(savedOrder.getTotalAmount())
                .status(savedOrder.getStatus())
                .addressId(savedOrder.getAddress().getAddressId())
                .build();
    }
}
