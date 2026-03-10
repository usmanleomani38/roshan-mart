package com.example.Ecommerce.Project.product.dtos.response;

import com.example.Ecommerce.Project.audit.AuditMetaData;
import com.example.Ecommerce.Project.product.model.Product;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponseDTO extends AuditMetaData {

    private Long productId;
    private String productName;
    private double discount;
    private double price;
    private int quantity;
    private String description;
    private double specialPrice;
    private String image;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;



    public static ProductResponseDTO toDTO(Product product) {

        return ProductResponseDTO.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .discount(product.getDiscount())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .specialPrice(product.getSpecialPrice())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

}
