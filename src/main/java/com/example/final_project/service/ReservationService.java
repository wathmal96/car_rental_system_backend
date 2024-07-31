package com.example.final_project.service;

import com.example.final_project.dto.ReservationGetDTO;
import com.example.final_project.dto.ReservationSetDTO;
import com.example.final_project.entity.Car;
import com.example.final_project.entity.Customer;
import com.example.final_project.entity.Reservation;
import com.example.final_project.repo.CarRepo;
import com.example.final_project.repo.CustomerRepo;
import com.example.final_project.repo.ReservationRepo;
import com.example.final_project.util.Converter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationRepo reservationRepo;
    private final CustomerRepo customerRepo;
    private final CarRepo carRepo;
    private final Converter converter;

    public ReservationService(ReservationRepo reservationRepo, CustomerRepo customerRepo, CarRepo carRepo, Converter converter) {
        this.reservationRepo = reservationRepo;
        this.customerRepo = customerRepo;
        this.carRepo = carRepo;
        this.converter = converter;
    }

    public ReservationGetDTO saveReservation(ReservationSetDTO reservationSetDTO) {
        Optional<Customer> byId = customerRepo.findById(reservationSetDTO.getCustomerId());
        Optional<Car> byId1 = carRepo.findById(reservationSetDTO.getCarId());
        if (byId.isPresent() && byId1.isPresent()) {
            Customer customer = byId.get();
            Car car = byId1.get();
            long daysBetween = ChronoUnit.DAYS.between(reservationSetDTO.getReservationStartDate(), reservationSetDTO.getReservationEndDate());
            Reservation save = reservationRepo.save(new Reservation(null, customer, car, reservationSetDTO.getReservationStartDate(), reservationSetDTO.getReservationEndDate(), LocalDate.now(), false, "unburrowed",daysBetween* car.getPrice()));
            return converter.entityToReservationGetDTO(save);

//            Customer customer1 = save.getCustomer();

//            CustomerNewDTO customerNewDTO = new CustomerNewDTO(customer1.getId(), customer1.getName(), customer1.getPassword(), customer1.getAddress(), customer1.getContact(), customer1.getEMail(), customer1.getRoles());

//            CustomerNewDTO customerNewDTO = converter.entityToCustomerNewDTO(customer);
//
//            Car car1 = save.getCar();
//            List<Image> images = car1.getImages();
//
//            List<ImageDTO> imageDTOs = new ArrayList<>();
//            for (Image image : images) {
//                ImageDTO imageDTO = new ImageDTO(image.getId(), image.getFilePath());
//                imageDTOs.add(imageDTO);
//            }
//
//            CarWithImagesDTO carWithImagesDTO = new CarWithImagesDTO(car1.getId(), car1.getModel(), car1.getBrand(), car1.getEngineCapacity(), car1.getPrice(), imageDTOs);
//
//            return new ReservationGetDTO(save.getId(), customerNewDTO, carWithImagesDTO, save.getReservationStartDate(), reservationSetDTO.getReservationEndDate(), save.getOrderDate(), save.isApproved(), save.getStatus());
        }
        return null;
    }

    public ReservationGetDTO approveReservation(int id) {
        Optional<Reservation> byId = reservationRepo.findById(id);
        if (byId.isPresent()) {
            Reservation reservation = byId.get();
            reservation.setApproved(true);
            Reservation save = reservationRepo.save(reservation);
            return converter.entityToReservationGetDTO(save);

//            Customer customer1 = save.getCustomer();
//            CustomerNewDTO customerNewDTO = new CustomerNewDTO(customer1.getId(), customer1.getName(), customer1.getPassword(), customer1.getAddress(), customer1.getContact(), customer1.getEMail(), customer1.getRoles());
//
//
//            Car car1 = save.getCar();
//            List<Image> images = car1.getImages();
//
//            List<ImageDTO> imageDTOs = new ArrayList<>();
//            for (Image image : images) {
//                ImageDTO imageDTO = new ImageDTO(image.getId(), image.getFilePath());
//                imageDTOs.add(imageDTO);
//            }
//
//            CarWithImagesDTO carWithImagesDTO = new CarWithImagesDTO(car1.getId(), car1.getModel(), car1.getBrand(), car1.getEngineCapacity(), car1.getPrice(), imageDTOs);
//
//
//            return new ReservationGetDTO(save.getId(), customerNewDTO, carWithImagesDTO, save.getReservationStartDate(), save.getReservationEndDate(), save.getOrderDate(), save.isApproved(), save.getStatus());
        }
        return null;
    }


    public ReservationGetDTO updateStatus(int id, String status) {

        if (!status.equals("burrowed") && !status.equals("handover"))
            return null;
        Optional<Reservation> byId = reservationRepo.findById(id);
        if (byId.isPresent()) {
            Reservation reservation = byId.get();
            switch (status) {
                case "burrowed":
                    if (reservation.isApproved())
                        reservation.setStatus(status);
                    else return null;
                    break;
                case "handover":
                    if (reservation.getStatus().equals("burrowed"))
                        reservation.setStatus(status);
                    else return null;
            }
            Reservation save = reservationRepo.save(reservation);
            return converter.entityToReservationGetDTO(save);
//                Customer customer1 = save.getCustomer();
//                CustomerNewDTO customerNewDTO = new CustomerNewDTO(customer1.getId(), customer1.getName(), customer1.getPassword(), customer1.getAddress(), customer1.getContact(), customer1.getEMail(), customer1.getRoles());
//
//
//                Car car1 = save.getCar();
//                List<Image> images = car1.getImages();
//
//                List<ImageDTO> imageDTOs = new ArrayList<>();
//                for (Image image : images) {
//                    ImageDTO imageDTO = new ImageDTO(image.getId(), image.getFilePath());
//                    imageDTOs.add(imageDTO);
//                }
//
//                CarWithImagesDTO carWithImagesDTO = new CarWithImagesDTO(car1.getId(), car1.getModel(), car1.getBrand(), car1.getEngineCapacity(), car1.getPrice(), imageDTOs);
//
//
//                return new ReservationGetDTO(save.getId(), customerNewDTO, carWithImagesDTO, save.getReservationStartDate(), save.getReservationEndDate(), save.getOrderDate(), save.isApproved(), save.getStatus());
        }
        return null;
    }


    public List<ReservationGetDTO> viewReservationsOfCustomerByStatus(int id, String status) {
        List<Reservation> byCustomerId = reservationRepo.findByCustomerIdAndStatus(id, status);
        return converter.entityListToReservationGetDTOList(byCustomerId);
//        List<ReservationGetDTO> reservationGetDTOS = new ArrayList<>();
//        for (Reservation reservation1 : byCustomerId) {
//            Customer customer1 = reservation1.getCustomer();
//            CustomerNewDTO customerNewDTO = new CustomerNewDTO(customer1.getId(), customer1.getName(), customer1.getPassword(), customer1.getAddress(), customer1.getContact(), customer1.getEMail(), customer1.getRoles());
//
//
//            Car car1 = reservation1.getCar();
//            List<Image> images = car1.getImages();
//
//            List<ImageDTO> imageDTOs = new ArrayList<>();
//            for (Image image : images) {
//                ImageDTO imageDTO = new ImageDTO(image.getId(), image.getFilePath());
//                imageDTOs.add(imageDTO);
//            }
//
//            CarWithImagesDTO carWithImagesDTO = new CarWithImagesDTO(car1.getId(), car1.getModel(), car1.getBrand(), car1.getEngineCapacity(), car1.getPrice(), imageDTOs);
//
//
//            reservationGetDTOS.add(new ReservationGetDTO(reservation1.getId(), customerNewDTO, carWithImagesDTO, reservation1.getReservationStartDate(), reservation1.getReservationEndDate(), reservation1.getOrderDate(), reservation1.isApproved(), reservation1.getStatus()));
//
//        }
//        return reservationGetDTOS;
    }

    public List<ReservationGetDTO> viewAllReservations() {
        List<Reservation> all = reservationRepo.findAllByOrderByReservationStartDateAsc();
        return converter.entityListToReservationGetDTOList(all);

//        List<ReservationGetDTO> reservationGetDTOS = new ArrayList<>();
//        for (Reservation reservation : all) {
//
//            Customer customer1 = reservation.getCustomer();
//            CustomerNewDTO customerNewDTO = new CustomerNewDTO(customer1.getId(), customer1.getName(), customer1.getPassword(), customer1.getAddress(), customer1.getContact(), customer1.getEMail(), customer1.getRoles());
//
//
//            Car car1 = reservation.getCar();
//            List<Image> images = car1.getImages();
//
//            List<ImageDTO> imageDTOs = new ArrayList<>();
//            for (Image image : images) {
//                ImageDTO imageDTO = new ImageDTO(image.getId(), image.getFilePath());
//                imageDTOs.add(imageDTO);
//            }
//
//            CarWithImagesDTO carWithImagesDTO = new CarWithImagesDTO(car1.getId(), car1.getModel(), car1.getBrand(), car1.getEngineCapacity(), car1.getPrice(), imageDTOs);
//
//
//            reservationGetDTOS.add(new ReservationGetDTO(reservation.getId(), customerNewDTO, carWithImagesDTO, reservation.getReservationStartDate(), reservation.getReservationEndDate(), reservation.getOrderDate(), reservation.isApproved(), reservation.getStatus()));
//        }
//        return reservationGetDTOS;
    }

//    public List<ReservationGetDTO> viewNewRequests() {
//        List<Reservation> all = reservationRepo.findByApprovedIs(false);
//        List<ReservationGetDTO> reservationGetDTOS = new ArrayList<>();
//        for (Reservation reservation : all) {
//
//            Customer customer1 = reservation.getCustomer();
//            CustomerNewDTO customerNewDTO = new CustomerNewDTO(customer1.getId(), customer1.getName(), customer1.getPassword(), customer1.getAddress(), customer1.getContact(), customer1.getEMail(), customer1.getRoles());
//
//
//            Car car1 = reservation.getCar();
//            List<Image> images = car1.getImages();
//
//            List<ImageDTO> imageDTOs = new ArrayList<>();
//            for (Image image : images) {
//                ImageDTO imageDTO = new ImageDTO(image.getId(), image.getFilePath());
//                imageDTOs.add(imageDTO);
//            }
//
//            CarWithImagesDTO carWithImagesDTO = new CarWithImagesDTO(car1.getId(), car1.getModel(), car1.getBrand(), car1.getEngineCapacity(), car1.getPrice(), imageDTOs);
//
//
//            reservationGetDTOS.add(new ReservationGetDTO(reservation.getId(), customerNewDTO, carWithImagesDTO, reservation.getReservationStartDate(), reservation.getReservationEndDate(), reservation.getOrderDate(), reservation.isApproved(), reservation.getStatus()));
//        }
//        return reservationGetDTOS;
//    }
//
//    public List<ReservationGetDTO> viewReservationsByDate(LocalDate date) {
//        List<Reservation> byReservationDate = reservationRepo.findByReservationStartDateLessThanEqualAndReservationEndDateGreaterThanEqual(date, date);
//        if (byReservationDate != null) {
//            List<ReservationGetDTO> reservationGetDTOS = new ArrayList<>();
//            for (Reservation reservation : byReservationDate) {
//
//                Customer customer1 = reservation.getCustomer();
//                CustomerNewDTO customerNewDTO = new CustomerNewDTO(customer1.getId(), customer1.getName(), customer1.getPassword(), customer1.getAddress(), customer1.getContact(), customer1.getEMail(), customer1.getRoles());
//
//
//                Car car1 = reservation.getCar();
//                List<Image> images = car1.getImages();
//
//                List<ImageDTO> imageDTOs = new ArrayList<>();
//                for (Image image : images) {
//                    ImageDTO imageDTO = new ImageDTO(image.getId(), image.getFilePath());
//                    imageDTOs.add(imageDTO);
//                }
//
//                CarWithImagesDTO carWithImagesDTO = new CarWithImagesDTO(car1.getId(), car1.getModel(), car1.getBrand(), car1.getEngineCapacity(), car1.getPrice(), imageDTOs);
//
//                reservationGetDTOS.add(new ReservationGetDTO(reservation.getId(), customerNewDTO, carWithImagesDTO, reservation.getReservationStartDate(), reservation.getReservationEndDate(), reservation.getOrderDate(), reservation.isApproved(), reservation.getStatus()));
//            }
//            return reservationGetDTOS;
//        }
//        return null;
//    }
//
//    public List<ReservationGetDTO> viewReservationsByStartDate(LocalDate date) {
//        List<Reservation> byReservationDate = reservationRepo.findByReservationStartDate(date);
//        if (byReservationDate != null) {
//            List<ReservationGetDTO> reservationGetDTOS = new ArrayList<>();
//            for (Reservation reservation : byReservationDate) {
//
//                Customer customer1 = reservation.getCustomer();
//                CustomerNewDTO customerNewDTO = new CustomerNewDTO(customer1.getId(), customer1.getName(), customer1.getPassword(), customer1.getAddress(), customer1.getContact(), customer1.getEMail(), customer1.getRoles());
//
//
//                Car car1 = reservation.getCar();
//                List<Image> images = car1.getImages();
//
//                List<ImageDTO> imageDTOs = new ArrayList<>();
//                for (Image image : images) {
//                    ImageDTO imageDTO = new ImageDTO(image.getId(), image.getFilePath());
//                    imageDTOs.add(imageDTO);
//                }
//
//                CarWithImagesDTO carWithImagesDTO = new CarWithImagesDTO(car1.getId(), car1.getModel(), car1.getBrand(), car1.getEngineCapacity(), car1.getPrice(), imageDTOs);
//
//                reservationGetDTOS.add(new ReservationGetDTO(reservation.getId(), customerNewDTO, carWithImagesDTO, reservation.getReservationStartDate(), reservation.getReservationEndDate(), reservation.getOrderDate(), reservation.isApproved(), reservation.getStatus()));
//            }
//            return reservationGetDTOS;
//        }
//        return null;
//    }
//
//    public List<ReservationGetDTO> viewReservationsByEndDate(LocalDate date) {
//        List<Reservation> byReservationDate = reservationRepo.findByReservationEndDate(date);
//        if (byReservationDate != null) {
//            List<ReservationGetDTO> reservationGetDTOS = new ArrayList<>();
//            for (Reservation reservation : byReservationDate) {
//
//                Customer customer1 = reservation.getCustomer();
//                CustomerNewDTO customerNewDTO = new CustomerNewDTO(customer1.getId(), customer1.getName(), customer1.getPassword(), customer1.getAddress(), customer1.getContact(), customer1.getEMail(), customer1.getRoles());
//
//                Car car1 = reservation.getCar();
//                List<Image> images = car1.getImages();
//
//                List<ImageDTO> imageDTOs = new ArrayList<>();
//                for (Image image : images) {
//                    ImageDTO imageDTO = new ImageDTO(image.getId(), image.getFilePath());
//                    imageDTOs.add(imageDTO);
//                }
//
//                CarWithImagesDTO carWithImagesDTO = new CarWithImagesDTO(car1.getId(), car1.getModel(), car1.getBrand(), car1.getEngineCapacity(), car1.getPrice(), imageDTOs);
//
//                reservationGetDTOS.add(new ReservationGetDTO(reservation.getId(), customerNewDTO, carWithImagesDTO, reservation.getReservationStartDate(), reservation.getReservationEndDate(), reservation.getOrderDate(), reservation.isApproved(), reservation.getStatus()));
//            }
//            return reservationGetDTOS;
//        }
//        return null;
//    }

//    public List<ReservationGetDTO> viewReservationsByDateRange(LocalDate date1,LocalDate date2) {
//        List<Reservation> byReservationDate = reservationRepo.findByDateRangeNotIntersect(date1,date2);
//        if (byReservationDate != null) {
//            List<ReservationGetDTO> reservationGetDTOS = new ArrayList<>();
//            for (Reservation reservation : byReservationDate) {
//
//                Customer customer1 = reservation.getCustomer();
//                CustomerNewDTO customerNewDTO = new CustomerNewDTO(customer1.getId(),customer1.getName(),customer1.getPassword(),customer1.getAddress(),customer1.getContact(),customer1.getEMail(),customer1.getRoles());
//
//                Car car1 = reservation.getCar();
//                List<Image> images = car1.getImages();
//
//                List<ImageDTO> imageDTOs = new ArrayList<>();
//                for (Image image : images) {
//                    ImageDTO imageDTO = new ImageDTO(image.getId(), image.getFilePath());
//                    imageDTOs.add(imageDTO);
//                }
//
//                CarWithImagesDTO carWithImagesDTO = new CarWithImagesDTO(car1.getId(), car1.getModel(), car1.getBrand(), car1.getEngineCapacity(), car1.getPrice(), imageDTOs);
//
//                reservationGetDTOS.add(new ReservationGetDTO(reservation.getId(), customerNewDTO, carWithImagesDTO, reservation.getReservationStartDate(), reservation.getReservationEndDate(), reservation.getOrderDate(), reservation.isApproved()));
//            }
//            return reservationGetDTOS;
//        }
//        return null;
//    }


//    public List<ReservationGetDTO> viewReservationsOfCustomer(int id) {
//        List<Reservation> byCustomerId = reservationRepo.findByCustomerId(id);
//        List<ReservationGetDTO> reservationGetDTOS = new ArrayList<>();
//        for (Reservation reservation1 : byCustomerId) {
//            Customer customer1 = reservation1.getCustomer();
//            CustomerNewDTO customerNewDTO = new CustomerNewDTO(customer1.getId(), customer1.getName(), customer1.getPassword(), customer1.getAddress(), customer1.getContact(), customer1.getEMail(), customer1.getRoles());
//
//
//            Car car1 = reservation1.getCar();
//            List<Image> images = car1.getImages();
//
//            List<ImageDTO> imageDTOs = new ArrayList<>();
//            for (Image image : images) {
//                ImageDTO imageDTO = new ImageDTO(image.getId(), image.getFilePath());
//                imageDTOs.add(imageDTO);
//            }
//
//            CarWithImagesDTO carWithImagesDTO = new CarWithImagesDTO(car1.getId(), car1.getModel(), car1.getBrand(), car1.getEngineCapacity(), car1.getPrice(), imageDTOs);
//
//
//            reservationGetDTOS.add(new ReservationGetDTO(reservation1.getId(), customerNewDTO, carWithImagesDTO, reservation1.getReservationStartDate(), reservation1.getReservationEndDate(), reservation1.getOrderDate(), reservation1.isApproved(), reservation1.getStatus()));
//
//        }
//        return reservationGetDTOS;
//    }
}
