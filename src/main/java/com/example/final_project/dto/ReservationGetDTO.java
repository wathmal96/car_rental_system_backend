package com.example.final_project.dto;

import com.example.final_project.entity.Car;
import com.example.final_project.entity.Customer;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReservationGetDTO {
    private int id;
    private CustomerDTO customer;
    private CarWithImagesDTO car;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate reservationStartDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate reservationEndDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate orderDate;
    private boolean isApproved;
}
