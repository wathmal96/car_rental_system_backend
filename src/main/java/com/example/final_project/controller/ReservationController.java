package com.example.final_project.controller;

import com.example.final_project.dto.*;
import com.example.final_project.email.EmailDetails;
import com.example.final_project.email.EmailService;
import com.example.final_project.service.CustomerService;
import com.example.final_project.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    private ReservationService reservationService;
    private EmailService emailService;
    private CustomerService customerService;
    public ReservationController(ReservationService reservationService, EmailService emailService, CustomerService customerService) {
        this.reservationService = reservationService;
        this.emailService = emailService;
        this.customerService = customerService;
    }

    @PreAuthorize("hasAuthority('customer')")
    @PostMapping("/reserve")
    public ResponseEntity<ReservationGetDTO> saveReservation(@RequestBody ReservationSetDTO reservationSetDTO){
        ReservationGetDTO reservationGetDTO = reservationService.saveReservation(reservationSetDTO);
        return new ResponseEntity<>(reservationGetDTO, HttpStatus.CREATED);
    }
    @PreAuthorize("hasAuthority('admin')")
    @PutMapping("/approve/{id}")
    public ResponseEntity<ReservationGetDTO> approveReservation(@PathVariable int id){
        ReservationGetDTO reservationGetDTO = reservationService.approveReservation(id);

        String eMail = reservationGetDTO.getCustomer().getEMail();

        emailService.sendSimpleMail(new EmailDetails(eMail,"Mail sent Successfully","Reservation Approval"));

        return new ResponseEntity<>(reservationGetDTO,HttpStatus.OK);
    }

    @GetMapping("/get_by_customer/{id}")
    public ResponseEntity<List<ReservationGetDTO>> viewReservationsOfCustomer(@PathVariable int id){
        List<ReservationGetDTO> reservationGetDTOS = reservationService.viewReservationsOfCustomer(id);
        return new ResponseEntity<>(reservationGetDTOS,HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/get_all")
    public ResponseEntity<List<ReservationGetDTO>> viewAllReservations(){
        List<ReservationGetDTO> reservationGetDTOS = reservationService.viewAllReservations();
        return new ResponseEntity<>(reservationGetDTOS,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/get_all/{date}")
    public ResponseEntity<List<ReservationGetDTO>> viewReservationsByDate(@PathVariable LocalDate date){
        List<ReservationGetDTO> reservationGetDTOS = reservationService.viewReservationsByDate(date);
        return new ResponseEntity<>(reservationGetDTOS,HttpStatus.OK);
    }
}
