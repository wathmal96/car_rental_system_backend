package com.example.final_project.service;

import com.example.final_project.dto.*;
import com.example.final_project.entity.Car;
import com.example.final_project.entity.Customer;
import com.example.final_project.entity.Image;
import com.example.final_project.entity.Reservation;
import com.example.final_project.repo.CarRepo;
import com.example.final_project.repo.CustomerRepo;
import com.example.final_project.repo.ReservationRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationRepo reservationRepo;
    private final CustomerRepo customerRepo;
    private final CarRepo carRepo;

    public ReservationService(ReservationRepo reservationRepo, CustomerRepo customerRepo, CarRepo carRepo) {
        this.reservationRepo = reservationRepo;
        this.customerRepo = customerRepo;
        this.carRepo = carRepo;
    }

    public ReservationGetDTO saveReservation(ReservationSetDTO reservationSetDTO) {
        Optional<Customer> byId = customerRepo.findById(reservationSetDTO.getCustomerId());
        Optional<Car> byId1 = carRepo.findById(reservationSetDTO.getCarId());
        if (byId.isPresent() && byId1.isPresent()) {
            Customer customer = byId.get();
            Car car = byId1.get();
            Reservation save = reservationRepo.save(new Reservation(null, customer, car, reservationSetDTO.getReservationStartDate(), reservationSetDTO.getReservationEndDate(), reservationSetDTO.getOrderDate(), false));

            Customer customer1 = save.getCustomer();
            CustomerDTO customerDTO = new CustomerDTO(customer1.getId(),customer1.getName(),customer1.getPassword(),customer1.getAddress(),customer1.getContact(),customer1.getEMail(),customer1.getRoles());

            Car car1 = save.getCar();
            List<Image> images = car1.getImages();

            List<ImageDTO> imageDTOs = new ArrayList<>();
            for (Image image : images) {
                ImageDTO imageDTO = new ImageDTO(image.getId(), image.getFilePath());
                imageDTOs.add(imageDTO);
            }

            CarWithImagesDTO carWithImagesDTO = new CarWithImagesDTO(car1.getId(), car1.getModel(), car1.getBrand(), car1.getEngineCapacity(), car1.getPrice(), imageDTOs);

            return new ReservationGetDTO(save.getId(), customerDTO,carWithImagesDTO, save.getReservationStartDate(), reservationSetDTO.getReservationEndDate(), save.getOrderDate(), save.isApproved());
        }
        return null;
    }

    public ReservationGetDTO approveReservation(int id) {
        Optional<Reservation> byId = reservationRepo.findById(id);
        if (byId.isPresent()) {
            Reservation reservation = byId.get();
            reservation.setApproved(true);
            Reservation save = reservationRepo.save(reservation);
            Customer customer1 = save.getCustomer();
            CustomerDTO customerDTO = new CustomerDTO(customer1.getId(),customer1.getName(),customer1.getPassword(),customer1.getAddress(),customer1.getContact(),customer1.getEMail(),customer1.getRoles());


            Car car1 = save.getCar();
            List<Image> images = car1.getImages();

            List<ImageDTO> imageDTOs = new ArrayList<>();
            for (Image image : images) {
                ImageDTO imageDTO = new ImageDTO(image.getId(), image.getFilePath());
                imageDTOs.add(imageDTO);
            }

            CarWithImagesDTO carWithImagesDTO = new CarWithImagesDTO(car1.getId(), car1.getModel(), car1.getBrand(), car1.getEngineCapacity(), car1.getPrice(), imageDTOs);


            return new ReservationGetDTO(save.getId(), customerDTO, carWithImagesDTO, save.getReservationStartDate(), save.getReservationEndDate(), save.getOrderDate(), save.isApproved());
        }
        return null;
    }

    public List<ReservationGetDTO> viewReservationsOfCustomer(int id) {
        List<Reservation> byCustomerId = reservationRepo.findByCustomerId(id);
        List<ReservationGetDTO> reservationGetDTOS = new ArrayList<>();
        for (Reservation reservation1 : byCustomerId) {
                Customer customer1 = reservation1.getCustomer();
                CustomerDTO customerDTO = new CustomerDTO(customer1.getId(),customer1.getName(),customer1.getPassword(),customer1.getAddress(),customer1.getContact(),customer1.getEMail(),customer1.getRoles());


            Car car1 = reservation1.getCar();
            List<Image> images = car1.getImages();

            List<ImageDTO> imageDTOs = new ArrayList<>();
            for (Image image : images) {
                ImageDTO imageDTO = new ImageDTO(image.getId(), image.getFilePath());
                imageDTOs.add(imageDTO);
            }

            CarWithImagesDTO carWithImagesDTO = new CarWithImagesDTO(car1.getId(), car1.getModel(), car1.getBrand(), car1.getEngineCapacity(), car1.getPrice(), imageDTOs);


            reservationGetDTOS.add(new ReservationGetDTO(reservation1.getId(), customerDTO, carWithImagesDTO, reservation1.getReservationStartDate(), reservation1.getReservationEndDate(), reservation1.getOrderDate(), reservation1.isApproved()));

        }
        return reservationGetDTOS;
    }

    public List<ReservationGetDTO> viewAllReservations() {
        List<Reservation> all = reservationRepo.findAll();
        List<ReservationGetDTO> reservationGetDTOS = new ArrayList<>();
        for (Reservation reservation : all) {

            Customer customer1 = reservation.getCustomer();
            CustomerDTO customerDTO = new CustomerDTO(customer1.getId(),customer1.getName(),customer1.getPassword(),customer1.getAddress(),customer1.getContact(),customer1.getEMail(),customer1.getRoles());


            Car car1 = reservation.getCar();
            List<Image> images = car1.getImages();

            List<ImageDTO> imageDTOs = new ArrayList<>();
            for (Image image : images) {
                ImageDTO imageDTO = new ImageDTO(image.getId(), image.getFilePath());
                imageDTOs.add(imageDTO);
            }

            CarWithImagesDTO carWithImagesDTO = new CarWithImagesDTO(car1.getId(), car1.getModel(), car1.getBrand(), car1.getEngineCapacity(), car1.getPrice(), imageDTOs);


            reservationGetDTOS.add(new ReservationGetDTO(reservation.getId(), customerDTO, carWithImagesDTO, reservation.getReservationStartDate(), reservation.getReservationEndDate(), reservation.getOrderDate(), reservation.isApproved()));
        }
        return reservationGetDTOS;
    }

//    public List<ReservationGetDTO> viewReservationsByDate(LocalDate date) {
//        List<Reservation> byReservationDate = reservationRepo.findByReservationStartDateLessThanEqualAndReservationEndDateGreaterThanEqual(date);
//        if (byReservationDate != null) {
//            List<ReservationGetDTO> reservationGetDTOS = new ArrayList<>();
//            for (Reservation reservation : byReservationDate) {
//                reservationGetDTOS.add(new ReservationGetDTO(reservation.getId(), reservation.getCustomer(), reservation.getCar(), reservation.getReservationStartDate(), reservation.getReservationEndDate(), reservation.getOrderDate(), reservation.isApproved()));
//            }
//            return reservationGetDTOS;
//        }
//        return null;
//    }

}
