package com.example.final_project.authentication;

import com.example.final_project.entity.Admin;
import com.example.final_project.entity.Customer;
import com.example.final_project.repo.AdminRepo;
import com.example.final_project.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CombinedUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Admin> adminOptional = adminRepo.findByName("admin_"+username);
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            admin.setName(username);
            return new AdminUserDetails(admin);
        }

        Optional<Customer> customerOptional = customerRepo.findByName("customer_"+username);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            customer.setName(username);
            return new CustomerUserDetails(customer);
        }

        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
