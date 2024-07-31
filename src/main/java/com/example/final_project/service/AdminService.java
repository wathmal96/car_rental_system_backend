package com.example.final_project.service;

import com.example.final_project.dto.AdminNewDTO;
import com.example.final_project.entity.Admin;
import com.example.final_project.repo.AdminRepo;
import com.example.final_project.util.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final AdminRepo adminRepo;

    private final PasswordEncoder passwordEncoder;
    private final Converter converter;

    public AdminService(AdminRepo adminRepo, PasswordEncoder passwordEncoder, Converter converter) {
        this.adminRepo = adminRepo;
        this.passwordEncoder = passwordEncoder;
        this.converter = converter;
    }

    public AdminNewDTO addAdmin(AdminNewDTO adminNewDTO) {
        adminNewDTO.setPassword(passwordEncoder.encode(adminNewDTO.getPassword()));
        adminNewDTO.setName("admin_" + adminNewDTO.getName());

        if (!adminRepo.existsById(1)) {
            Admin save = adminRepo.save(converter.adminNewDTOToEntity(adminNewDTO));
            return converter.entityToAdminNewDTO(save);
        }
        return null;
    }
}
