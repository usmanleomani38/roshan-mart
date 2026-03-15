package com.example.Ecommerce.Project.address.model;

import com.example.Ecommerce.Project.audit.AuditMetaData;
import com.example.Ecommerce.Project.order.model.Order;
import com.example.Ecommerce.Project.user.model.User;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.ssl.SslBundleKey;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
public class Address extends AuditMetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;
    private String street;
    private String buildingName;
    private String state;
    private String country;
    private String pinCode;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name= "user_id")
    private User user;

    @OneToMany(mappedBy = "address")
    private List<Order> orders = new ArrayList<>();

}
