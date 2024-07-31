package com.example.final_project.controller;

import com.example.final_project.authentication.JwtService;
import com.example.final_project.dto.CustomerLoginDTO;
import com.example.final_project.dto.CustomerNewDTO;
import com.example.final_project.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
//@CrossOrigin(origins = "http://10.10.6.17:8081")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/new")
    public ResponseEntity<Object> addNew(@RequestBody CustomerNewDTO customerNewDTO) {
        CustomerNewDTO customerNewDTO1 = customerService.addCustomer(customerNewDTO);

        if (customerNewDTO1 == null) {
            return new ResponseEntity<>("user name already exists",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(customerNewDTO1, HttpStatus.CREATED);
    }

    @PostMapping("/authenticate")
    public CustomerLoginDTO authenticateAndGetToken(@RequestBody CustomerLoginDTO customerLoginDTO) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(customerLoginDTO.getName(), customerLoginDTO.getPassword()));
        if (authenticate.isAuthenticated()) {
            String token = jwtService.generateToken(customerLoginDTO.getName());
            CustomerLoginDTO customerLoginDTO1 = customerService.loginCustomer(customerLoginDTO.getName());
            customerLoginDTO1.setToken(token);
            return customerLoginDTO1;
        } else throw new UsernameNotFoundException("invalid customer");
    }
}
