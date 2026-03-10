package com.example.Ecommerce.Project.product.dtos.request;

import com.example.Ecommerce.Project.product.model.Product;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long productId;
    private String productName;
    private String description;
    private double discount;
    private double price;
    private double specialPrice;
    private String image;
    private Integer quantity;

    public static ProductDTO toDTO(Product product, Integer itemQuantity) {
        return ProductDTO.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .discount(product.getDiscount())
                .price(product.getPrice())
                .specialPrice(product.getSpecialPrice())
                .image(product.getImage())
                .quantity(itemQuantity)
                .build();
    }
}
