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

import java.util.Collections;
import java.util.List;

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

    @DeleteMapping("carts/products/{productId}")
    public ResponseEntity<ApiResponse<String>>deleteProductFromCartByUserId(
            @PathVariable Long productId) {

        String message = cartService.deleteProductFromCartByUserId( productId);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .status(Status.SUCCESS)
                .message(message)
                .build();
        return ResponseEntity.ok(response);
    }


    @PutMapping("carts/products/{productId}/quantity/{quantity}")
       public ResponseEntity<ApiResponse<?>> updateProductQuantity(@PathVariable Long productId,
                                                                   @PathVariable Integer quantity) {

            ApiResponse<?> response = ApiResponse.builder()
                    .status(Status.SUCCESS)
                    .message("quantity updated successfully")
                    .data(cartService.updateProductQuantity(productId, quantity))
                    .build();
           return ResponseEntity.ok(response);
       }

       @GetMapping("/carts")
       public ResponseEntity<ApiResponse<List<CartDTO>>> getAllCarts() {

           List<CartDTO> dtos =  cartService.getAllCarts();

           boolean isEmpty = dtos.isEmpty();
           Status status = isEmpty ? Status.NO_CONTENT : Status.SUCCESS;
           String message = dtos.isEmpty() ?
                   "No carts found!" : "Carts fetched Successfully!";

           ApiResponse<List<CartDTO>> response = ApiResponse.<List<CartDTO>>builder()
                   .status(Status.SUCCESS)
                   .message(message)
                   .data(dtos)
                   .build();
           return ResponseEntity.ok(response);
    }

    @GetMapping("/carts/users/cart")
    ResponseEntity<ApiResponse<CartDTO>> findCartById() {

        CartDTO cart  = cartService.getCart();
        boolean isEmpty = cart == null;
        Status status = isEmpty ? Status.NOT_FOUND : Status.SUCCESS;
        String message = cart == null?
                "No cart found!" : "Cart fetched Successfully!";

        ApiResponse<CartDTO> response = ApiResponse.<CartDTO>builder()
                .status(status)
                .message(message)
                .data(cart)
                .build();
        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/carts/user")
    public ResponseEntity<ApiResponse<String>>deleteCartByUser() {

        String message = cartService.deleteCartByUser();
        ApiResponse<String> response = ApiResponse.<String>builder()
                .status(Status.SUCCESS)
                .message(message)
                .build();
        return ResponseEntity.ok(response);
    }





}

