package com.example.Ecommerce.Project.product.dtos.request;

import com.example.Ecommerce.Project.product.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {

    private String productName;
    private Double discount;
    private String description;
    private Double price;
    private Integer quantity;
    private String image;

    public Product toEntity() {
        return Product.builder()
                .productName(this.getProductName())
                .discount(this.getDiscount())
                .description(this.getDescription())
                .price(this.getPrice())
                .quantity(this.getQuantity())
                .build();
    }

}
