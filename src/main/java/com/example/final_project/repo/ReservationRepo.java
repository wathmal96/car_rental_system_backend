package com.example.final_project.repo;

import com.example.final_project.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationRepo extends JpaRepository<Reservation,Integer> {
    List<Reservation> findByCustomerId(int id);
//    List<Reservation> findByReservationStartDateLessThanEqualAndReservationEndDateGreaterThanEqual(LocalDate date);

}
