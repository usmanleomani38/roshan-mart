package com.example.Ecommerce.Project.address.repo;

import com.example.Ecommerce.Project.address.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepo extends JpaRepository<Address,Long> {
    List<Address> findByUserUserId(Long userId);

}