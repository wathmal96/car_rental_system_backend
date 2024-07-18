package com.example.final_project.controller;

import com.example.final_project.authentication.JwtService;
import com.example.final_project.dto.CustomerDTO;
import com.example.final_project.entity.Admin;
import com.example.final_project.entity.Customer;
import com.example.final_project.service.AdminService;
import com.example.final_project.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @PostMapping("/new")
    public String addNew(@RequestBody CustomerDTO customerDTO) {
        return customerService.addCustomer(customerDTO);
    }

    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody CustomerDTO customerDTO) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(customerDTO.getName(), customerDTO.getPassword()));
        if (authenticate.isAuthenticated()) {
            return jwtService.generateToken(customerDTO.getName());
        } else throw new UsernameNotFoundException("invalid customer");
    }
}
