package com.example.Ecommerce.Project.payement.model;


import com.example.Ecommerce.Project.audit.AuditMetaData;
import com.example.Ecommerce.Project.order.model.Order;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class Payment extends AuditMetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;
    private String paymentMethod;

    @OneToOne(mappedBy = "payment", cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            orphanRemoval = true)
    private Order order;
    private Long paymentGatewayId;
    private String paymentGatewayStatus;
    private String paymentGatewayResponseMessage;
    private String paymentGatewayName;


    public Payment(Long paymentGatewayId,
                   Long paymentId,
                   String paymentGatewayStatus,
                   String paymentGatewayResponseMessage,
                   String paymentGatewayName) {
        this.paymentGatewayId = paymentGatewayId;
        this.paymentId = paymentId;
        this.paymentGatewayStatus = paymentGatewayStatus;
        this.paymentGatewayResponseMessage = paymentGatewayResponseMessage;
        this.paymentGatewayName = paymentGatewayName;
    }
}
