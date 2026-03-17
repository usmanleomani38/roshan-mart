package com.example.Ecommerce.Project.order.controller;

import com.example.Ecommerce.Project.exeptionhandler.ApiResponse;
import com.example.Ecommerce.Project.exeptionhandler.Status;
import com.example.Ecommerce.Project.order.dtos.OrderDTO;
import com.example.Ecommerce.Project.order.dtos.OrderRequestDTO;
import com.example.Ecommerce.Project.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.Ecommerce.Project.appcontants.commonutils.Methods.getCurrentUserEmail;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("order/users/payments/{paymentMethod}")
    public ResponseEntity<ApiResponse<OrderDTO>> orderProducts (@PathVariable String paymentMethod,
                                                                @RequestBody OrderRequestDTO dto) {
        OrderDTO orderDTO =  orderService.placeOrder(
                getCurrentUserEmail(),
                dto.getAddressId(),
                paymentMethod,
                dto.getPaymentGatewayName(),
                dto.getPaymentGatewayId(),
                dto.getPaymentGatewayStatus(),
                dto.getPaymentResponseMessage()
        );

        ApiResponse<OrderDTO> response = ApiResponse.<OrderDTO>builder()
                .status(Status.SUCCESS)
                .message("Order placed Successfully!")
                .data(orderDTO)
                .build();
        return ResponseEntity.ok(response);
    }
}
