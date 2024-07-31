package com.example.final_project.dto;

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
public class ReservationSetDTO {
    private int customerId;
    private int carId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate reservationStartDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate reservationEndDate;
    private double payment;
}
