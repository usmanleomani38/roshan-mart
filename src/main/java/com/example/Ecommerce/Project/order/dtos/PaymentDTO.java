package com.example.Ecommerce.Project.order.dtos;

import com.example.Ecommerce.Project.payment.model.Payment;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO {

    private Long paymentId;
    private String paymentMethod;
    private String paymentGatewayId;
    private String status;
    private String paymentResponseMessage;
    private String paymentGatewayName;


    public static PaymentDTO toDTO(Payment payment) {
        return PaymentDTO.builder()
                .paymentId(payment.getPaymentId())
                .paymentMethod(payment.getPaymentMethod())
                .paymentGatewayId(String.valueOf(payment.getPaymentId()))
                .status(payment.getPaymentGatewayStatus())
                .paymentResponseMessage(payment.getPaymentGatewayResponseMessage())
                .paymentGatewayName(payment.getPaymentGatewayName())
                .build();
    }


}
