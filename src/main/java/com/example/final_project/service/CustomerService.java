package com.example.final_project.service;

import com.example.final_project.dto.CustomerLoginDTO;
import com.example.final_project.dto.CustomerNewDTO;
import com.example.final_project.entity.Customer;
import com.example.final_project.repo.CustomerRepo;
import com.example.final_project.util.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    private CustomerRepo customerRepo;
    private PasswordEncoder passwordEncoder;
    private Converter converter;

    public CustomerService(CustomerRepo customerRepo, PasswordEncoder passwordEncoder, Converter converter) {
        this.customerRepo = customerRepo;
        this.passwordEncoder = passwordEncoder;
        this.converter = converter;
    }

    public CustomerNewDTO addCustomer(CustomerNewDTO customerNewDTO) {
        customerNewDTO.setPassword(passwordEncoder.encode(customerNewDTO.getPassword()));
        customerNewDTO.setName("customer_" + customerNewDTO.getName());
        if (!customerRepo.existsCustomerByName(customerNewDTO.getName())) {
            Customer save = customerRepo.save(converter.customerNewDTOToEntity(customerNewDTO));
            return converter.entityToCustomerNewDTO(save);
        }
        return null;
    }

    public CustomerLoginDTO loginCustomer(String name) {
        Optional<Customer> byName = customerRepo.findByName("customer_" + name);
        if (byName.isPresent()) {
            Customer customer = byName.get();
            return converter.entityToCustomerLoginDTO(customer);
        }
        return null;
    }

//    public CustomerNewDTO getById(int id) {
//        Optional<Customer> byId = customerRepo.findById(id);
//        if (byId.isPresent()) {
//            Customer customer = byId.get();
//            return new CustomerNewDTO(customer.getId(), customer.getName(), customer.getPassword(), customer.getAddress(), customer.getContact(), customer.getEMail(), customer.getContact());
//        }
//        return null;
//    }
}
