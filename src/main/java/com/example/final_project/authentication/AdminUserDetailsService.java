package com.example.final_project.authenticatiom;

import com.example.final_project.entity.Admin;
import com.example.final_project.repo.AdminRepo;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdminUserDetailsService implements UserDetailsService {
    private AdminRepo adminRepo;
    public AdminUserDetailsService(AdminRepo adminRepo) {
        this.adminRepo = adminRepo;
    }
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<Admin> admin = adminRepo.findByName(name);
        return admin.map(AdminUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not Found" + name));
    }
}

