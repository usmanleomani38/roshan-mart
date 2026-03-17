package com.example.Ecommerce.Project.cart.model;

import com.example.Ecommerce.Project.audit.AuditMetaData;
import com.example.Ecommerce.Project.cartitem.model.CartItem;
import com.example.Ecommerce.Project.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@ToString
public class Cart extends AuditMetaData {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @OneToOne
    @JoinColumn(name= "user_id")
    @ToString.Exclude
    private User user;

    @OneToMany(mappedBy = "cart", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE},
               orphanRemoval = true)
    private List<CartItem> cartItemsList = new ArrayList<>();

    private Double totalPrice = 0.0;
}

