package com.example.Ecommerce.Project.order.model;

import com.example.Ecommerce.Project.address.model.Address;
import com.example.Ecommerce.Project.audit.AuditMetaData;
import com.example.Ecommerce.Project.orderitem.model.OrderItem;
import com.example.Ecommerce.Project.payement.model.Payment;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class Order extends AuditMetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;
    @Email
    @Column(nullable = false)
    private String email;
    private LocalDateTime orderDate;
    private String status;
    private double totalAmount;
    private String paymentId;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            orphanRemoval = true)
    private List<OrderItem> orderItemsList = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

}
