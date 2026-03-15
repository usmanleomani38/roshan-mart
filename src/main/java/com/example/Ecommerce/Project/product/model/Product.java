package com.example.Ecommerce.Project.product.model;

import com.example.Ecommerce.Project.audit.AuditMetaData;
import com.example.Ecommerce.Project.cartitem.model.CartItem;
import com.example.Ecommerce.Project.category.model.Category;
import com.example.Ecommerce.Project.orderitem.model.OrderItem;
import com.example.Ecommerce.Project.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class Product extends AuditMetaData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private String image;
    private String description;
    private double discount;
    private double price;
    private int quantity;
    private double specialPrice;
//    @CreationTimestamp
//    @Column(updatable = false, nullable = false)
//    private LocalDateTime createdAt;
//    @UpdateTimestamp
//    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @ToString.Exclude
    private Category category;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    @ToString.Exclude
    private User user;


    @OneToMany(mappedBy = "product" ,cascade = {CascadeType.MERGE, CascadeType.PERSIST},
              fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<CartItem> products = new ArrayList<>();

    @OneToMany(mappedBy = "product" ,cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<OrderItem>  orderItems = new ArrayList<>();


}



