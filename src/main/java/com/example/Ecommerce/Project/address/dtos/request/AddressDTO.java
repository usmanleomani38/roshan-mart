package com.example.Ecommerce.Project.address.dtos.request;

import com.example.Ecommerce.Project.address.model.Address;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDTO {

    private Long addressId;
    private String street;
    private String buildingName;
    private String state;
    private String country;
    private String pinCode;

    public static Address toEntity(AddressDTO dto)  {
       return Address.builder()
               .street(dto.getStreet())
               .buildingName(dto.getBuildingName())
               .state(dto.getState())
               .country(dto.getCountry())
               .pinCode(dto.getPinCode())
               .build();
    }

    public static AddressDTO toDTO(Address address) {
        return AddressDTO.builder()
                .addressId(address.getAddressId())
                .street(address.getStreet())
                .buildingName(address.getBuildingName())
                .state(address.getState())
                .country(address.getCountry())
                .pinCode(address.getPinCode())
                .build();
    }

}
