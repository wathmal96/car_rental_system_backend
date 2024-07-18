package com.example.final_project.controller;

import com.example.final_project.dto.CarDTO;
import com.example.final_project.dto.CarWithImagesDTO;
import com.example.final_project.dto.ImageWithCarDTO;
import com.example.final_project.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping
    public ResponseEntity<String> healthCheck(){
        return new ResponseEntity<>("server up and running..!!",HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:5173")
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

    //    @GetMapping("/get_by_id/{id}")
//    public ResponseEntity<CarWithImagesDTO> getCarById(@PathVariable int id){
//        CarWithImagesDTO carById = carService.getCarById(id);
//        return new ResponseEntity<>(carById,HttpStatus.OK);
//    }
    @PreAuthorize("hasAuthority('admin')")
    @PutMapping("/update/{id}")
    public ResponseEntity<CarWithImagesDTO> updateCar(@PathVariable int id, @RequestBody CarDTO carDTO) {
        CarWithImagesDTO carWithImagesDTO1 = carService.updateCar(id, carDTO);
        return new ResponseEntity<>(carWithImagesDTO1, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('admin')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CarWithImagesDTO> deleteCar(@PathVariable int id) {
        CarWithImagesDTO carWithImagesDTO = carService.deleteCar(id);
        return new ResponseEntity<>(carWithImagesDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/upload_file_path_by_car_id/{id}")
    public ResponseEntity<List<ImageWithCarDTO>> uploadFilePath(@PathVariable int id, @RequestParam("image") List<MultipartFile> files) {
        try {
            List<ImageWithCarDTO> imageWithCarDTOs = carService.uploadFilePathsByCarId(id, files);
            return new ResponseEntity<>(imageWithCarDTOs,HttpStatus.CREATED);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
