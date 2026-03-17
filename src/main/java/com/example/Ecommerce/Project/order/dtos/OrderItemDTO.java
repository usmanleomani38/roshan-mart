package com.example.Ecommerce.Project.order.dtos;

import com.example.Ecommerce.Project.cartitem.dto.response.ProductsInCart;
import com.example.Ecommerce.Project.orderitem.model.OrderItem;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDTO {

    private Long orderItemId;
    private ProductsInCart product;
    private Integer quantity;
    private Double orderedProductPrice;
    private Double discount;

    public static OrderItemDTO toDTO(OrderItem orderItem) {

        return OrderItemDTO.builder()
                .orderItemId(orderItem.getOrderItemId())
                .product(ProductsInCart.toDTO(orderItem.getProduct()))
                .quantity(orderItem.getQuantity())
                .orderedProductPrice(orderItem.getOrderedProductPrice())
                .discount(orderItem.getDiscount())
                .build();
    }

}

