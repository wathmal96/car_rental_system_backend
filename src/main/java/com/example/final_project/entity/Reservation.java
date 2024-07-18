package com.example.final_project.entity;

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
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate reservationStartDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate reservationEndDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate orderDate;
    private boolean isApproved;


}
