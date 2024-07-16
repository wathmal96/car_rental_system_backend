package com.example.final_project.service;

import com.example.final_project.dto.CarDTO;
import com.example.final_project.dto.CarWithImagesDTO;
import com.example.final_project.dto.ImageDTO;
import com.example.final_project.dto.ImageWithCarDTO;
import com.example.final_project.entity.Car;
import com.example.final_project.entity.Image;
import com.example.final_project.repo.CarRepo;
import com.example.final_project.repo.ImageRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    private final CarRepo carRepo;
    private final ImageRepo imageRepo;
    @PersistenceContext
    private EntityManager entityManager;

    public CarService(CarRepo carRepo, ImageRepo imageRepo) {
        this.carRepo = carRepo;
        this.imageRepo = imageRepo;
    }

    public List<CarWithImagesDTO> getAllCar() {
        List<Car> cars = carRepo.findAll();
        List<CarWithImagesDTO> carWithImagesDTOS = new ArrayList<>();
        for (Car car : cars) {

            List<Image> images = car.getImages();
            List<ImageDTO> imageDTOs = new ArrayList<>();
            for (Image image : images) {
                ImageDTO imageDTO = new ImageDTO(image.getId(), image.getFilePath());
                imageDTOs.add(imageDTO);
            }

            CarWithImagesDTO carWithImagesDTO = new CarWithImagesDTO(car.getId(), car.getModel(), car.getBrand(), car.getEngineCapacity(), car.getPrice(), imageDTOs);
            carWithImagesDTOS.add(carWithImagesDTO);
        }
        return carWithImagesDTOS;
    }

    public CarDTO saveCar(CarDTO carDTO) {
        Car car = carRepo.save(new Car(null, carDTO.getModel(), carDTO.getBrand(), carDTO.getEngineCapacity(), carDTO.getPrice(), null, null));
        return new CarDTO(car.getId(), car.getModel(), car.getBrand(), car.getEngineCapacity(), car.getPrice());
    }

//    public CarWithImagesDTO getCarById(int id) {
//        Optional<Car> byId = carRepo.findById(id);
//        Car car;
//        if (byId.isPresent()) {
//            car = byId.get();
//            return new CarWithImagesDTO(car.getId(), car.getModel(), car.getBrand(), car.getEngineCapacity(), car.getPrice());
//        }
//        return null;
//    }

    public CarWithImagesDTO updateCar(int id, CarDTO carDTO) {
        Optional<Car> byId = carRepo.findById(id);
        if (byId.isPresent()) {
            Car car = byId.get();
            car.setModel(carDTO.getModel());
            car.setBrand(car.getBrand());
            car.setEngineCapacity(carDTO.getEngineCapacity());
            car.setPrice(car.getPrice());

            Car save = carRepo.save(car);
            List<Image> images = save.getImages();
            List<ImageDTO> imageDTOs = new ArrayList<>();
            for (Image image : images) {
                ImageDTO imageDTO = new ImageDTO(image.getId(), image.getFilePath());
                imageDTOs.add(imageDTO);
            }
            return new CarWithImagesDTO(save.getId(), save.getModel(), save.getBrand(), save.getEngineCapacity(), save.getPrice(), imageDTOs);
        }
        return null;
    }

    public CarWithImagesDTO deleteCar(int id) {
        Optional<Car> byId = carRepo.findById(id);
        if (byId.isPresent()) {
            carRepo.deleteById(id);
            Car car = byId.get();

            List<Image> images = car.getImages();
            List<ImageDTO> imageDTOs = new ArrayList<>();
            for (Image image : images) {
                ImageDTO imageDTO = new ImageDTO(image.getId(), image.getFilePath());
                imageDTOs.add(imageDTO);
            }

            return new CarWithImagesDTO(car.getId(), car.getModel(), car.getBrand(), car.getEngineCapacity(), car.getPrice(), imageDTOs);
        }
        return null;
    }

    @Transactional
    public List<ImageWithCarDTO> uploadFilePathsByCarId(int id, List<MultipartFile> files) throws IOException {
        Optional<Car> byId1 = carRepo.findById(id);
        if (byId1.isPresent()) {
            Car car = byId1.get();
            List<ImageWithCarDTO> imageWithCarDTOList = new ArrayList<>();

            Integer lastId = imageRepo.findLastId();
            int newId = (lastId != null) ? lastId + 1 : 1;

            for (MultipartFile file : files) {
                String filenameExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
                String newFileName = newId + "." + filenameExtension;

                Path path = Paths.get("src/main/resources/static/assets/" + newFileName);
                InputStream inputStream = file.getInputStream();
                Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);

                Image savedImage = imageRepo.save(new Image(null, car, newFileName));
                Hibernate.initialize(car.getImages());
                entityManager.clear();

                Car savedCar = savedImage.getCar();
                ImageWithCarDTO imageWithCarDTO = new ImageWithCarDTO(
                        savedImage.getId(),
                        new CarDTO(savedCar.getId(), savedCar.getModel(), savedCar.getBrand(), savedCar.getEngineCapacity(), savedCar.getPrice()),
                        savedImage.getFilePath()
                );
                imageWithCarDTOList.add(imageWithCarDTO);

                newId++; // Increment newId for the next file
            }

            return imageWithCarDTOList;
        }
        return null;
    }

}
