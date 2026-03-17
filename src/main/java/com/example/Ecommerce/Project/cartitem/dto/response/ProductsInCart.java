package com.example.Ecommerce.Project.cartitem.dto.response;

import com.example.Ecommerce.Project.product.model.Product;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductsInCart {

    private Long productId;
    private String productName;
    private String description;
    private double discount;
    private double price;
    private double specialPrice;
    //private Integer totalItems;
    private String image;

    public static ProductsInCart toDTO(Product product) {

        return ProductsInCart.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .discount(product.getDiscount())
                .price(product.getPrice())
                .specialPrice(product.getSpecialPrice())
                .image(product.getImage())
                .build();
    }

}
