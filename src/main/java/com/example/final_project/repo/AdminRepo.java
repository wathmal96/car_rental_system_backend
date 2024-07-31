package com.example.final_project.repo;

import com.example.final_project.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepo extends JpaRepository<Admin,Integer> {
    Optional<Admin> findByName(String name);
    boolean existsAdminByName(String name);

}
