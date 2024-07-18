package com.example.final_project.dto;

import com.example.final_project.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CarWithImagesDTO {
    private int id;
    private String model;
    private String brand;
    private String engineCapacity;
    private double price;
    private List<ImageDTO> images;
}
