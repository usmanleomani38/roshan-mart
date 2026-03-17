package com.example.Ecommerce.Project.orderitem.model;

import com.example.Ecommerce.Project.audit.AuditMetaData;
import com.example.Ecommerce.Project.order.model.Order;
import com.example.Ecommerce.Project.product.model.Product;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class OrderItem extends AuditMetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;
    private Double discount;
    private Double orderedProductPrice;
    private Integer quantity;


    @ManyToOne
    @JoinColumn(name = "order_id")
    @ToString.Exclude
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
