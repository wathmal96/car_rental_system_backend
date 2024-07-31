package com.example.final_project.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String model;
    private String brand;
    private String engineCapacity;
    private double price;
    @JsonManagedReference
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<Reservation> reservations;
    @JsonManagedReference
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<Image> images;

//    public Car(Integer id, String model, String brand, String engineCapacity, double price) {
//        this.id = id;
//        this.model = model;
//        this.brand = brand;
//        this.engineCapacity = engineCapacity;
//        this.price = price;
//    }
}
