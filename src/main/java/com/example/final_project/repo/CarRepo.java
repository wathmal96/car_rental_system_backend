package com.example.final_project.repo;

import com.example.final_project.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CarRepo extends JpaRepository<Car, Integer> {
    @Query("SELECT c FROM Car c WHERE c.id NOT IN (SELECT r.car.id FROM Reservation r WHERE r.reservationStartDate <= :endDate AND r.reservationEndDate >= :startDate)")
    List<Car> findCarsWithoutReservationsInRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
