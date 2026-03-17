package com.example.Ecommerce.Project.order.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDTO {

    private Long addressId;
    private String paymentMethod;
    private String paymentGatewayName;
    private String paymentGatewayId;
    private String paymentGatewayStatus;
    private String paymentResponseMessage;

}
