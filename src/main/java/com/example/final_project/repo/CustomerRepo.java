package com.example.final_project.repo;

import com.example.final_project.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer,Integer> {
    Optional<Customer> findByName(String name);

    boolean existsCustomerByName(String name);
}
