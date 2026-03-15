package com.example.Ecommerce.Project.address.service;

import com.example.Ecommerce.Project.address.dtos.request.AddressDTO;
import com.example.Ecommerce.Project.address.model.Address;
import com.example.Ecommerce.Project.address.repo.AddressRepo;
import com.example.Ecommerce.Project.exeptionhandler.customexceptions.ResourceNotFoundException;
import com.example.Ecommerce.Project.user.model.User;
import com.example.Ecommerce.Project.user.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.Ecommerce.Project.appcontants.commonutils.Methods.getCurrentUser;
import static com.example.Ecommerce.Project.appcontants.commonutils.Methods.getCurrentUserId;

@Component
@RequiredArgsConstructor
public class AddressService {
    
    private final AddressRepo addressRepo;
    private final UserRepo userRepo;

    public AddressDTO createAddress(AddressDTO dto) {

        User user = getCurrentUser();
        Address address = AddressDTO.toEntity(dto);
        address.setUser(user);
        user.getAddresses().add(address);
        address = addressRepo.save(address);
        return AddressDTO.toDTO(address);

    }

    public void deleteAddressById(Long addressId) {

        Address address = addressRepo.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found!"));

        User user = address.getUser();
        if (user != null) {
            user.getAddresses().remove(address);
            userRepo.save(user);
        }
    }

    public List<AddressDTO> getAllAddresses() {

        List<Address> addresses = addressRepo.findAll();
        List<AddressDTO> addressDTOS = new ArrayList<>();
        if(addresses.isEmpty())
            return Collections.emptyList();
        else {
            for (Address address : addresses)
                addressDTOS.add(AddressDTO.toDTO(address));
        }
        return addressDTOS;

    }

    public AddressDTO updateAddressById(Long addressId, AddressDTO dto) {

        Address address = addressRepo.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));

        if(dto.getStreet() != null) {
            address.setStreet(dto.getStreet());
        }
        if(dto.getBuildingName() != null) {
            address.setBuildingName(dto.getBuildingName());
        }

        if(dto.getState() != null) {
            address.setState(dto.getState());
        }
        if(dto.getCountry() != null) {
            address.setCountry(dto.getCountry());
        }
        if(dto.getPinCode() != null) {
            address.setPinCode(dto.getPinCode());
        }

        Address updatedAddress = addressRepo.save(address);


        User user = updatedAddress.getUser();
        for (Address ad : user.getAddresses()) {
            if(ad.getAddressId().equals(updatedAddress.getAddressId()))
                user.getAddresses().remove(ad);
        }
        user.getAddresses().add(updatedAddress);
        userRepo.save(user);
        return AddressDTO.toDTO(updatedAddress);

//        user.getAddresses()
//                .removeIf(a-> a.getAddressId().equals(updatedAddress.getAddressId()))
//        user.getAddresses().add(updatedAddress);

    }

    public List<AddressDTO> getUserAddresses() {

        List<AddressDTO> addressDTOS = new ArrayList<>();
        List<Address> addresses = addressRepo.findByUserUserId(getCurrentUserId());
        if(addresses.isEmpty())
            return Collections.emptyList();
        for (Address address : addresses)
            addressDTOS.add(AddressDTO.toDTO(address));
        return addressDTOS;
    }

    public AddressDTO getAddressById(Long addressId) {

        Address address = addressRepo.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not foun!"));
     return AddressDTO.toDTO(address);
    }

}

