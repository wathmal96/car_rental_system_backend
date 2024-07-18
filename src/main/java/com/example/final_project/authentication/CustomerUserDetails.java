package com.example.final_project.authentication;

import com.example.final_project.entity.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

public class CustomerUserDetails implements UserDetails {
    private final String userName;
    private final String passsword;
    private final List<GrantedAuthority> authorities;

    public CustomerUserDetails(Customer customer) {
        userName = customer.getName();
        passsword = customer.getPassword();

        // Create authorities from roles
        List<GrantedAuthority> roleAuthorities = Arrays.stream(customer.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // Add customerId as an authority
        GrantedAuthority customerIdAuthority = new SimpleGrantedAuthority("CUSTOMER_ID_" + customer.getId());

        // Combine roleAuthorities and customerIdAuthority
        authorities = new ArrayList<>(roleAuthorities);
        authorities.add(customerIdAuthority);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return passsword;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
