package com.example.final_project.repo;

import com.example.final_project.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepo extends JpaRepository<Reservation, Integer> {
    List<Reservation> findAllByOrderByReservationStartDateAsc();
    List<Reservation> findByCustomerIdAndStatus(int id, String status);

//    List<Reservation> findByCustomerId(int id);
//    List<Reservation> findByReservationStartDateLessThanEqualAndReservationEndDateGreaterThanEqual(LocalDate date1, LocalDate date2);
//    List<Reservation> findByReservationStartDate(LocalDate date);


//    @Query("SELECT r FROM Reservation r WHERE :date BETWEEN r.reservationStartDate AND r.reservationEndDate")
//    List<Reservation> findReservationsContainingDate(@Param("date") LocalDate date);

//    List<Reservation> findByApprovedIs(boolean isApproved);
//
//    List<Reservation> findByReservationEndDate(LocalDate date);

//    @Query("SELECT r FROM Reservation r WHERE r.reservationEndDate < :startDate OR r.reservationStartDate > :endDate")
//    List<Reservation> findByDateRangeNotIntersect(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
