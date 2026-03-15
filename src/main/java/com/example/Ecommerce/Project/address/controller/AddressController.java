package com.example.Ecommerce.Project.address.controller;

import com.example.Ecommerce.Project.address.dtos.request.AddressDTO;
import com.example.Ecommerce.Project.address.model.Address;
import com.example.Ecommerce.Project.address.service.AddressService;
import com.example.Ecommerce.Project.category.model.Category;
import com.example.Ecommerce.Project.exeptionhandler.ApiResponse;
import com.example.Ecommerce.Project.exeptionhandler.Status;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.DeclareError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/addresses")
    public ResponseEntity<ApiResponse<AddressDTO>> createAddress (@RequestBody AddressDTO dto) {
        AddressDTO addressDTO = addressService.createAddress(dto);
        ApiResponse<AddressDTO> response = ApiResponse.<AddressDTO>builder()
                .status(Status.CREATED)
                .message("Address created successfully")
                .data(addressDTO)
                .build();
        URI location = URI.create("api/address/" + addressDTO.getAddressId());
        return ResponseEntity.created(location).body(response);
    }


        @DeleteMapping("/addresses/address/{addressId}")
        public ResponseEntity<ApiResponse<?>> deleteAddressById (@PathVariable Long addressId) {
            addressService.deleteAddressById(addressId);
            ApiResponse<?> response = ApiResponse.builder()
                    .status(Status.SUCCESS)
                    .message("Address deleted successfully")
                    .build();
            return ResponseEntity.ok(response);
        }

        @GetMapping("/addresses")
        public ResponseEntity<ApiResponse<List<AddressDTO>>> getAllAddresses() {
          var list  = addressService.getAllAddresses();
          boolean isEmpty = list.isEmpty();
          Status status = isEmpty ? Status.NOT_FOUND : Status.SUCCESS;
          String message = list.isEmpty()?
                  "No records found!" : "Addresses fetched Successfully!";

          ApiResponse<List<AddressDTO>> response = ApiResponse.<List<AddressDTO>>builder()
                  .status(status)
                  .message(message)
                  .data(list)
                  .build();
          return ResponseEntity.ok(response);

    }

    @PutMapping("/addresses/address/{addressId}")
    public ResponseEntity<ApiResponse<AddressDTO>> updateAddressById(@PathVariable Long addressId,
                                                            @RequestBody AddressDTO dto) {
        ApiResponse<AddressDTO> response = ApiResponse.<AddressDTO>builder()
                .status(Status.SUCCESS)
                .message("Address Updated Successfully!")
                .data(addressService.updateAddressById(addressId, dto))
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("users/addresses")
    public ResponseEntity<ApiResponse<List<AddressDTO>>> getUserAddresses() {
        var list = addressService.getUserAddresses();

        boolean isEmpty = list.isEmpty();
        Status status = isEmpty ? Status.NOT_FOUND : Status.SUCCESS;
        String message = list.isEmpty()?
                "No records found!" : "Addresses fetched Successfully!";
        ApiResponse<List<AddressDTO>> response = ApiResponse.<List<AddressDTO>>builder()
                .status(status)
                .message(message)
                .data(list)
                .build();
        return ResponseEntity.ok(response);

    }

    @GetMapping("/addresses/address/{addressId}")
    public ResponseEntity<ApiResponse<AddressDTO>> getAddressById (@PathVariable Long addressId) {

        ApiResponse<AddressDTO> response = ApiResponse.<AddressDTO>builder()
                .status(Status.SUCCESS)
                .message("Address fetched successfully")
                .data(addressService.getAddressById(addressId))
                .build();
        return ResponseEntity.ok(response);
    }

}

