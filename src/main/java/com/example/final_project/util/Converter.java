package com.example.final_project.util;

import com.example.final_project.dto.*;
import com.example.final_project.entity.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Converter {
    private final ModelMapper modelMapper;

    public Converter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Car carDTOToEntity(CarDTO carDTO) {
        return modelMapper.map(carDTO, Car.class);
    }
    public CarDTO entityToCarDTO(Car car) {
        return modelMapper.map(car, CarDTO.class);
    }
    public CarWithImagesDTO entityToCarWithImagesDTO(Car car) {
        return modelMapper.map(car, CarWithImagesDTO.class);
    }
    public ImageWithCarDTO entityToImageWithCarDTO(Image image) {
        return modelMapper.map(image, ImageWithCarDTO.class);
    }

    public List<CarWithImagesDTO> entityListToCarWithImagesDTOList(List<Car> cars){
        return modelMapper.map(cars,new TypeToken<List<CarWithImagesDTO>>(){}.getType());
    }

    public CustomerNewDTO entityToCustomerNewDTO(Customer customer) {
        return modelMapper.map(customer, CustomerNewDTO.class);
    }
    public CustomerLoginDTO entityToCustomerLoginDTO(Customer customer) {
        return modelMapper.map(customer, CustomerLoginDTO.class);
    }
    public Customer customerNewDTOToEntity(CustomerNewDTO customerNewDTO) {
        return modelMapper.map(customerNewDTO, Customer.class);
    }
    public Admin adminNewDTOToEntity(AdminNewDTO adminNewDTO) {
        return modelMapper.map(adminNewDTO, Admin.class);
    }
    public AdminNewDTO entityToAdminNewDTO(Admin admin) {
        return modelMapper.map(admin, AdminNewDTO.class);
    }
    public ReservationGetDTO entityToReservationGetDTO(Reservation reservation) {
        return modelMapper.map(reservation, ReservationGetDTO.class);
    }

    public List<ReservationGetDTO> entityListToReservationGetDTOList(List<Reservation> reservations){
        return modelMapper.map(reservations,new TypeToken<List<ReservationGetDTO>>(){}.getType());
    }


//    public VehicleDto vehicleToDto(Vehicle vehicle) {
//        return modelMapper.map(vehicle, VehicleDto.class);
//    }
//
//    public List<VehicleDto> vehicleEntityListToVehicleDTOList(List<Vehicle> vehicles) {
//        return modelMapper.map(vehicles, new TypeToken<List<VehicleDto>>() {
//        }.getType());
//    }
}
