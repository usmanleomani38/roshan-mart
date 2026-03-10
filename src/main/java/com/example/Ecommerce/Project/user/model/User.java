package com.example.Ecommerce.Project.user.model;

import com.example.Ecommerce.Project.address.model.Address;
import com.example.Ecommerce.Project.audit.AuditMetaData;
import com.example.Ecommerce.Project.cart.model.Cart;
import com.example.Ecommerce.Project.product.model.Product;
import com.example.Ecommerce.Project.role.model.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class User extends AuditMetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank
    @Size(max = 20)
    private String userName;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;


    @ManyToMany(cascade = {CascadeType.MERGE,  CascadeType.PERSIST},
                fetch = FetchType.EAGER)
    @JoinTable(name = "user-role",
              joinColumns = @JoinColumn(name = "user_id"),
              inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = { CascadeType.MERGE, CascadeType.PERSIST},
               orphanRemoval = true)
    private Set<Product> products = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_address",
               joinColumns = @JoinColumn(name = "user-id"),
               inverseJoinColumns = @JoinColumn(name = "address_id"))
    private List<Address> addresses = new ArrayList<>();

    @OneToOne(mappedBy = "user" ,cascade = {CascadeType.MERGE,CascadeType.MERGE},
             orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Cart cart;

}
