package com.example.final_project.service;

import com.example.final_project.dto.CustomerDTO;
import com.example.final_project.entity.Admin;
import com.example.final_project.entity.Customer;
import com.example.final_project.repo.AdminRepo;
import com.example.final_project.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public String addCustomer(CustomerDTO customerDTO){
        customerDTO.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
        customerDTO.setName("customer_"+customerDTO.getName());
        customerRepo.save(new Customer(null,customerDTO.getName(),customerDTO.getPassword(),customerDTO.getAddress(),customerDTO.getContact(),customerDTO.getEMail(),customerDTO.getRoles(),null));

        return "user added";
    }
    public CustomerDTO getById(int id){
        Optional<Customer> byId = customerRepo.findById(id);
        if (byId.isPresent()){
            Customer customer = byId.get();
            return new CustomerDTO(customer.getId(),customer.getName(),customer.getPassword(),customer.getAddress(),customer.getContact(),customer.getEMail(),customer.getContact());
        }
        return null;
    }
}
