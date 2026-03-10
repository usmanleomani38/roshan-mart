package com.example.Ecommerce.Project.cartitem.dto.response;

import com.example.Ecommerce.Project.product.model.Product;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductsInCart {

    private Long productId;
    private String productName;
    private String description;
    private double discount;
    private double price;
    private double specialPrice;
    private Integer totalItems;
    private int quantity;
    private String image;

    public  ProductsInCart toDTO(Product product) {


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
