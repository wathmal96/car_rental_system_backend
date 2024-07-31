package com.example.final_project.controller;

import com.example.final_project.dto.ReservationGetDTO;
import com.example.final_project.dto.ReservationSetDTO;
import com.example.final_project.email.EmailDetails;
import com.example.final_project.email.EmailService;
import com.example.final_project.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
@CrossOrigin(origins = "http://localhost:5173")
public class ReservationController {
    private final ReservationService reservationService;
    private final EmailService emailService;

    //    private final CustomerService customerService;
    public ReservationController(ReservationService reservationService, EmailService emailService) {
        this.reservationService = reservationService;
        this.emailService = emailService;
    }

    @PreAuthorize("hasAuthority('CUSTOMER_ID_' + #reservationSetDTO.customerId)")
    @PostMapping("/reserve")
    public ResponseEntity<Object> saveReservation(@RequestBody ReservationSetDTO reservationSetDTO) {
        ReservationGetDTO reservationGetDTO = reservationService.saveReservation(reservationSetDTO);
        if(reservationGetDTO==null)
            return new ResponseEntity<>("no such car or customer found",HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(reservationGetDTO, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('admin')")
    @PutMapping("/approve/{id}")
    public ResponseEntity<Object> approveReservation(@PathVariable int id) {
        ReservationGetDTO reservationGetDTO = reservationService.approveReservation(id);
        if (reservationGetDTO == null)
            return new ResponseEntity<>("no such reservation",HttpStatus.BAD_REQUEST);
        String eMail = reservationGetDTO.getCustomer().getEMail();
        emailService.sendSimpleMail(new EmailDetails(eMail, "Mail sent Successfully", "Reservation Approval"));
        return new ResponseEntity<>(reservationGetDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('admin')")
    @PutMapping("/status/{id}/{status}")
    public ResponseEntity<Object> updateStatus(@PathVariable int id, @PathVariable String status) {
        ReservationGetDTO reservationGetDTO = reservationService.updateStatus(id, status);
        if (reservationGetDTO==null)
            return  new ResponseEntity<>("no such reservation or invalid status",HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(reservationGetDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('CUSTOMER_ID_' + #id)")
    @GetMapping("/get_by_customer/{id}/status/{status}")
    public ResponseEntity<List<ReservationGetDTO>> viewReservationsOfCustomerByStatus(@PathVariable("id") int id, @PathVariable("status") String status) {
        List<ReservationGetDTO> reservationGetDTOS = reservationService.viewReservationsOfCustomerByStatus(id, status);
        return new ResponseEntity<>(reservationGetDTOS, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/get_all")
    public ResponseEntity<List<ReservationGetDTO>> viewAllReservations() {
        List<ReservationGetDTO> reservationGetDTOS = reservationService.viewAllReservations();
        return new ResponseEntity<>(reservationGetDTOS, HttpStatus.OK);
    }

//    @PreAuthorize("hasAuthority('admin')")
//    @GetMapping("/get_all_by_date_range")
//    public ResponseEntity<List<ReservationGetDTO>> viewReservationsByDateRange(
//            @RequestParam("date1") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date1,
//            @RequestParam("date2") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date2) {
//
//        List<ReservationGetDTO> reservationGetDTOS = reservationService.viewReservationsByDateRange(date1, date2);
//        return new ResponseEntity<>(reservationGetDTOS, HttpStatus.OK);
//    }
//    @PreAuthorize("hasAuthority('CUSTOMER_ID_' + #id)")
//    @GetMapping("/get_by_customer/{id}")
//    public ResponseEntity<List<ReservationGetDTO>> viewReservationsOfCustomer(@PathVariable int id) {
//        List<ReservationGetDTO> reservationGetDTOS = reservationService.viewReservationsOfCustomer(id);
//        return new ResponseEntity<>(reservationGetDTOS, HttpStatus.OK);
//    }
//    @PreAuthorize("hasAuthority('admin')")
//    @GetMapping("/get_new_requests")
//    public ResponseEntity<List<ReservationGetDTO>> viewNewRequests() {
//        List<ReservationGetDTO> reservationGetDTOS = reservationService.viewNewRequests();
//        return new ResponseEntity<>(reservationGetDTOS, HttpStatus.OK);
//    }
//    @PreAuthorize("hasAuthority('admin')")
//    @GetMapping("/get_all_by_date/{date}")
//    public ResponseEntity<List<ReservationGetDTO>> viewReservationsByDate(@PathVariable LocalDate date) {
//        List<ReservationGetDTO> reservationGetDTOS = reservationService.viewReservationsByDate(date);
//        return new ResponseEntity<>(reservationGetDTOS, HttpStatus.OK);
//    }
//
//    @PreAuthorize("hasAuthority('admin')")
//    @GetMapping("/get_all_by_start_date/{date}")
//    public ResponseEntity<List<ReservationGetDTO>> viewReservationsByStartDate(@PathVariable LocalDate date) {
//        List<ReservationGetDTO> reservationGetDTOS = reservationService.viewReservationsByStartDate(date);
//        return new ResponseEntity<>(reservationGetDTOS, HttpStatus.OK);
//    }
//
//    @PreAuthorize("hasAuthority('admin')")
//    @GetMapping("/get_all_by_end_date/{date}")
//    public ResponseEntity<List<ReservationGetDTO>> viewReservationsByEndDate(@PathVariable LocalDate date) {
//        List<ReservationGetDTO> reservationGetDTOS = reservationService.viewReservationsByEndDate(date);
//        return new ResponseEntity<>(reservationGetDTOS, HttpStatus.OK);
//    }
}
