package com.example.Ecommerce.Project.cart.dto.response;

import com.example.Ecommerce.Project.cart.model.Cart;
import com.example.Ecommerce.Project.cartitem.model.CartItem;
import com.example.Ecommerce.Project.product.dtos.request.ProductDTO;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {

    private Long cartId;
    private double totalPrice;
    private Integer totalItems;
    private List<ProductDTO> product;


    public static CartDTO toDTO(Cart cart, List<CartItem> items) {

        Integer itemQuantity = null;
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (CartItem item : items) {
            itemQuantity = item.getQuantity();
            ProductDTO dto = ProductDTO.toDTO(item.getProduct(),itemQuantity);
            productDTOS.add(dto);
        }
        return CartDTO.builder()
                .cartId(cart.getCartId())
                .totalPrice(cart.getTotalPrice())
                .totalItems(productDTOS.size())
                .product(productDTOS)
                .build();
    }
}
