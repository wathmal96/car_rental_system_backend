package com.example.final_project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate reservationStartDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate reservationEndDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate orderDate;
    private boolean approved;
    private String status;
    private double payment;


}
