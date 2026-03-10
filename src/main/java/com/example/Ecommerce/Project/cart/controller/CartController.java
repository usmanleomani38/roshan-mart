package com.example.Ecommerce.Project.cart.controller;

import com.example.Ecommerce.Project.cart.dto.response.CartDTO;
import com.example.Ecommerce.Project.cart.model.Cart;
import com.example.Ecommerce.Project.cart.service.CartService;
import com.example.Ecommerce.Project.exeptionhandler.ApiResponse;
import com.example.Ecommerce.Project.exeptionhandler.Status;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CartController {

    private final CartService cartService;

    @PostMapping("/carts/products/{productId}/quantity/{quantity}")
    public ResponseEntity<ApiResponse<CartDTO>> addProductToCart(@PathVariable Long productId,
                                                                 @PathVariable Integer quantity) throws JsonProcessingException {

       ApiResponse<CartDTO> response = ApiResponse.<CartDTO>builder()
               .status(Status.SUCCESS)
               .message("Product added successfully!")
               .data(cartService.addProductToCart(productId, quantity))
               .build();
       return ResponseEntity.ok(response);
       }

       @DeleteMapping("carts/{cartId}/products/{productId}")
       public ResponseEntity<ApiResponse<String>>deleteProductFromCart(@PathVariable Long cartId,
                                                                       @PathVariable Long productId) {

        String message = cartService.deleteProductFromCart(cartId, productId);
           ApiResponse<String> response = ApiResponse.<String>builder()
                   .status(Status.SUCCESS)
                   .message(message)
                   .build();
           return ResponseEntity.ok(response);
       }

       @PutMapping("carts/products/{productId}/quantity/{quantity}")
       public ResponseEntity<ApiResponse<?>> updateProductQuantity(@PathVariable Long productId,
                                                                   @PathVariable Integer quantity) {

            cartService.updateProductQuantity(productId, quantity);
            ApiResponse<?> response = ApiResponse.builder()
                   .status(Status.SUCCESS)
                   .message("quantity updated successfully")
                   .build();
           return ResponseEntity.ok(response);
       }

}

