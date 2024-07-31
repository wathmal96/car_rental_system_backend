package com.example.final_project.controller;

import com.example.final_project.authentication.JwtService;
import com.example.final_project.dto.AdminLoginDTO;
import com.example.final_project.dto.AdminNewDTO;
import com.example.final_project.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/new")
    public ResponseEntity<Object> addNew(@RequestBody AdminNewDTO adminNewDTO) {
        AdminNewDTO adminNewDTO1 = adminService.addAdmin(adminNewDTO);
        if (adminNewDTO1 == null) {
            return new ResponseEntity<>("only one admin can add",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(adminNewDTO1, HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/authenticate")
    public AdminLoginDTO authenticateAndGetToken(@RequestBody AdminLoginDTO adminLoginDTO) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(adminLoginDTO.getName(), adminLoginDTO.getPassword()));
        if (authenticate.isAuthenticated()) {
            String token = jwtService.generateToken(adminLoginDTO.getName());
            adminLoginDTO.setToken(token);
            return adminLoginDTO;
        } else throw new UsernameNotFoundException("invalid admin");
    }
}
