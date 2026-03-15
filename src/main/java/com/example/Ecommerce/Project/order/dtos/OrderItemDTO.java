package com.example.Ecommerce.Project.order.dtos;

import com.example.Ecommerce.Project.product.dtos.request.ProductDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDTO {

    private Long orderItemId;
    private ProductDTO product;
    private Integer quantity;
    private Double orderedProductPrice;
    private Double discount;

}
