package com.example.final_project.repo;

import com.example.final_project.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ImageRepo extends JpaRepository<Image,Integer> {
    @Query(value = "SELECT id FROM Image ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Integer findLastId();
}
