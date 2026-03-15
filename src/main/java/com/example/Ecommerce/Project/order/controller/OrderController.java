package com.example.Ecommerce.Project.order.controller;

import com.example.Ecommerce.Project.exeptionhandler.ApiResponse;
import com.example.Ecommerce.Project.order.dtos.OrderDTO;
import com.example.Ecommerce.Project.order.dtos.OrderRequestDTO;
import com.example.Ecommerce.Project.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.RequiredTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.Ecommerce.Project.appcontants.commonutils.Methods.getCurrentUserEmail;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("order/users/payments/{paymentMethods}")
    public OrderDTO orderProducts (@PathVariable String paymentMethod,
                                                                @RequestBody OrderRequestDTO dto) {
        OrderDTO order =  orderService.placeOrder(
                getCurrentUserEmail(),
                dto.getAddressId(),
                paymentMethod,
                dto.getPaymentGatewayName(),
                dto.getPaymentGatewayId(),
                dto.getPaymentGatewaystatus(),
                dto.getPayementResponseMessage()
        );
        return null;
    }
}
