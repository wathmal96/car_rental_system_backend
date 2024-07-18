package com.example.final_project.service;

import com.example.final_project.dto.AdminnNewDTO;
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
    public AdminnNewDTO addAdmin(AdminnNewDTO adminnNewDTO){
        adminnNewDTO.setPassword(passwordEncoder.encode(adminnNewDTO.getPassword()));
        adminnNewDTO.setName("admin_"+ adminnNewDTO.getName());

//        adminRepo.findByEMail( adminnNewDTO.getEMail()).isPresent();
//        if (adminRepo.findByEMail( adminnNewDTO.getEMail()).isPresent()){
//            return null;
//        }
        Admin save = adminRepo.save(new Admin(null, adminnNewDTO.getName(), adminnNewDTO.getPassword(), adminnNewDTO.getEMail(), adminnNewDTO.getRoles()));
        return new AdminnNewDTO(save.getId(),save.getName(),save.getPassword(),save.getEMail(),save.getRoles());
    }
}
