package com.example.Ecommerce.Project.order.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO {

    private Long paymentId;
    private String paymentMethod;
    private Long paymentGatwayId;
    private String status;
    private String paymentResponseMessage;
    private String paymentGatewayName;


}
