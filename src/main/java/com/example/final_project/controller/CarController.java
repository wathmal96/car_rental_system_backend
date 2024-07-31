package com.example.final_project.controller;

import com.example.final_project.dto.CarDTO;
import com.example.final_project.dto.CarWithImagesDTO;
import com.example.final_project.dto.ImageWithCarDTO;
import com.example.final_project.service.CarService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/car")
@CrossOrigin(origins = "http://localhost:5173")
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<CarWithImagesDTO>> getAllCar() {
        List<CarWithImagesDTO> allCar = carService.getAllCar();
        return new ResponseEntity<>(allCar, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/save")
    public ResponseEntity<CarDTO> saveCar(@RequestBody CarDTO carDTO) {
        CarDTO carDTO1 = carService.saveCar(carDTO);
        return new ResponseEntity<>(carDTO1, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('admin')")
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateCar(@PathVariable int id, @RequestBody CarDTO carDTO) {
        CarWithImagesDTO carWithImagesDTO = carService.updateCar(id, carDTO);
        if (carWithImagesDTO == null)
            return new ResponseEntity<>("no such car to update", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(carWithImagesDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('admin')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteCar(@PathVariable int id) {
        CarWithImagesDTO carWithImagesDTO = carService.deleteCar(id);
        if (carWithImagesDTO == null) {
            return new ResponseEntity<>("no such car to delete", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(carWithImagesDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/upload_file_path_by_car_id/{id}")
    public ResponseEntity<Object> uploadFilePath(@PathVariable int id, @RequestParam("image") List<MultipartFile> files) {
        try {
            List<ImageWithCarDTO> imageWithCarDTOs = carService.uploadFilePathsByCarId(id, files);
            if (imageWithCarDTOs == null)
                return new ResponseEntity<>("no such car to upload images", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(imageWithCarDTOs, HttpStatus.CREATED);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasAuthority('admin')")
    @PutMapping("/update_file_path_by_car_id/{id}")
    public ResponseEntity<Object> updateFilePath(@PathVariable int id, @RequestParam("image") List<MultipartFile> files) {
        try {
            List<ImageWithCarDTO> imageWithCarDTOs = carService.updateFilePathsByCarId(id, files);
            if (imageWithCarDTOs == null)
                return new ResponseEntity<>("no such car to update images", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(imageWithCarDTOs, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IndexOutOfBoundsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/available")
    public List<CarWithImagesDTO> getCarsWithoutReservationsInRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return carService.getCarsWithoutReservationsInRange(startDate, endDate);
    }
//    @PreAuthorize("hasAuthority('admin')")
//    @PutMapping("/update_file_path/{id}")
//    public ResponseEntity<ImageWithCarDTO> uploadFilePath(@PathVariable int id, @RequestParam("image") MultipartFile file) {
//        try {
//            ImageWithCarDTO imageWithCarDTO = carService.updateFilePath(id, file);
//            return new ResponseEntity<>(imageWithCarDTO,HttpStatus.CREATED);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
    //    @GetMapping("/get_by_id/{id}")
//    public ResponseEntity<CarWithImagesDTO> getCarById(@PathVariable int id){
//        CarWithImagesDTO carById = carService.getCarById(id);
//        return new ResponseEntity<>(carById,HttpStatus.OK);
//    }
}
