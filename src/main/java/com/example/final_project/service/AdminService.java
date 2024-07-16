package com.example.final_project.service;

import com.example.final_project.dto.AdminDTO;
import com.example.final_project.entity.Admin;
import com.example.final_project.repo.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AdminRepo adminRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public String addAdmin(AdminDTO adminDTO){
        adminDTO.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
        adminDTO.setName("admin_"+adminDTO.getName());
        adminRepo.save(new Admin(null,adminDTO.getName(),adminDTO.getPassword(),adminDTO.getEMail(),adminDTO.getRoles()));

        return "user added";
    }
}
