package com.example.final_project.service;

import com.example.final_project.dto.CarDTO;
import com.example.final_project.dto.CarWithImagesDTO;
import com.example.final_project.dto.ImageWithCarDTO;
import com.example.final_project.entity.Car;
import com.example.final_project.entity.Image;
import com.example.final_project.repo.CarRepo;
import com.example.final_project.repo.ImageRepo;
import com.example.final_project.util.Converter;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    private final CarRepo carRepo;
    private final ImageRepo imageRepo;
    private final Converter converter;

    public CarService(CarRepo carRepo, ImageRepo imageRepo, Converter converter) {
        this.carRepo = carRepo;
        this.imageRepo = imageRepo;
        this.converter = converter;
    }

    public List<CarWithImagesDTO> getAllCar() {
        List<Car> cars = carRepo.findAll();
        return converter.entityListToCarWithImagesDTOList(cars);
//        List<CarWithImagesDTO> carWithImagesDTOS = new ArrayList<>();
//        for (Car car : cars) {
//
//            List<Image> images = car.getImages();
//            List<ImageDTO> imageDTOs = new ArrayList<>();
//            for (Image image : images) {
//                ImageDTO imageDTO = new ImageDTO(image.getId(), image.getFilePath());
//                imageDTOs.add(imageDTO);
//            }
//
//            CarWithImagesDTO carWithImagesDTO = new CarWithImagesDTO(car.getId(), car.getModel(), car.getBrand(), car.getEngineCapacity(), car.getPrice(), imageDTOs);
//            carWithImagesDTOS.add(carWithImagesDTO);
//        }
    }

    public CarDTO saveCar(CarDTO carDTO) {
        Car car = carRepo.save(converter.carDTOToEntity(carDTO));
        return converter.entityToCarDTO(car);
    }

    public CarWithImagesDTO updateCar(int id, CarDTO carDTO) {
        Optional<Car> byId = carRepo.findById(id);
        if (byId.isPresent()) {
            Car car = byId.get();
            car.setModel(carDTO.getModel());
            car.setBrand(carDTO.getBrand());
            car.setEngineCapacity(carDTO.getEngineCapacity());
            car.setPrice(carDTO.getPrice());
            Car save = carRepo.save(car);
            return converter.entityToCarWithImagesDTO(save);

//            List<Image> images = save.getImages();
//            List<ImageDTO> imageDTOs = new ArrayList<>();
//            for (Image image : images) {
//                ImageDTO imageDTO = new ImageDTO(image.getId(), image.getFilePath());
//                imageDTOs.add(imageDTO);
//            }
//            return new CarWithImagesDTO(save.getId(), save.getModel(), save.getBrand(), save.getEngineCapacity(), save.getPrice(), imageDTOs);
        }
        return null;
    }

    public CarWithImagesDTO deleteCar(int id) {
        Optional<Car> byId = carRepo.findById(id);
        if (byId.isPresent()) {
            carRepo.deleteById(id);
            Car car = byId.get();
            return converter.entityToCarWithImagesDTO(car);

//            List<Image> images = car.getImages();
//            List<ImageDTO> imageDTOs = new ArrayList<>();
//            for (Image image : images) {
//                ImageDTO imageDTO = new ImageDTO(image.getId(), image.getFilePath());
//                imageDTOs.add(imageDTO);
//            }
//
//            return new CarWithImagesDTO(car.getId(), car.getModel(), car.getBrand(), car.getEngineCapacity(), car.getPrice(), imageDTOs);

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
//                Hibernate.initialize(car.getImages());
//                entityManager.clear();

//                Car savedCar = savedImage.getCar();
//                ImageWithCarDTO imageWithCarDTO = new ImageWithCarDTO(
//                        savedImage.getId(),
//                        new CarDTO(savedCar.getId(), savedCar.getModel(), savedCar.getBrand(), savedCar.getEngineCapacity(), savedCar.getPrice()),
//                        savedImage.getFilePath()
//                );
                ImageWithCarDTO imageWithCarDTO = converter.entityToImageWithCarDTO(savedImage);
                imageWithCarDTOList.add(imageWithCarDTO);

                newId++; // Increment newId for the next file
            }

            return imageWithCarDTOList;
        }
        return null;
    }

    @Transactional
    public List<ImageWithCarDTO> updateFilePathsByCarId(int id, List<MultipartFile> files) throws IOException, IndexOutOfBoundsException {
        Optional<Car> byId1 = carRepo.findById(id);
        if (byId1.isPresent()) {
            Car car = byId1.get();
            List<ImageWithCarDTO> imageWithCarDTOList = new ArrayList<>();
            List<Image> images = car.getImages();

            for (int i = 0; i < files.size(); i++) {
                MultipartFile file = files.get(i);
                Image image = images.get(i);
                String filePath = image.getFilePath();
                String fileName = filePath.split("\\.")[0];

                String filenameExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
                String newFileName = fileName + "." + filenameExtension;

                Path path = Paths.get("src/main/resources/static/assets/" + newFileName);
                Path pathOld = Paths.get("src/main/resources/static/assets/" + filePath);
                InputStream inputStream = file.getInputStream();
                Files.delete(pathOld);
                Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);

                image.setFilePath(newFileName);

                Image savedImage = imageRepo.save(image);
                ImageWithCarDTO imageWithCarDTO = converter.entityToImageWithCarDTO(savedImage);
//                Hibernate.initialize(car.getImages());
//                entityManager.clear();

//                Car savedCar = savedImage.getCar();
//                ImageWithCarDTO imageWithCarDTO = new ImageWithCarDTO(
//                        savedImage.getId(),
//                        new CarDTO(savedCar.getId(), savedCar.getModel(), savedCar.getBrand(), savedCar.getEngineCapacity(), savedCar.getPrice()),
//                        savedImage.getFilePath()
//                );
                imageWithCarDTOList.add(imageWithCarDTO);

            }

            return imageWithCarDTOList;
        }
        return null;
    }

    public List<CarWithImagesDTO> getCarsWithoutReservationsInRange(LocalDate startDate, LocalDate endDate) {
        List<Car> carsWithoutReservationsInRange = carRepo.findCarsWithoutReservationsInRange(startDate, endDate);
        return converter.entityListToCarWithImagesDTOList(carsWithoutReservationsInRange);

//        List<CarWithImagesDTO> carWithImagesDTOS = new ArrayList<>();
//        for (Car car : carsWithoutReservationsInRange) {
//
//            List<Image> images = car.getImages();
//            List<ImageDTO> imageDTOs = new ArrayList<>();
//            for (Image image : images) {
//                ImageDTO imageDTO = new ImageDTO(image.getId(), image.getFilePath());
//                imageDTOs.add(imageDTO);
//            }
//
//            CarWithImagesDTO carWithImagesDTO = new CarWithImagesDTO(car.getId(), car.getModel(), car.getBrand(), car.getEngineCapacity(), car.getPrice(), imageDTOs);
//            carWithImagesDTOS.add(carWithImagesDTO);
//        }
//        return carWithImagesDTOS;
    }

//    @Transactional
//    public ImageWithCarDTO updateFilePath(int id, MultipartFile file) throws IOException {
//        Optional<Image> byId = imageRepo.findById(id);
//        if (byId.isPresent()) {
//            Image image = byId.get();
//            String filePath = image.getFilePath();
//            String fileName = filePath.split("\\.")[0];
//
//            String filenameExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
//            String newFileName = fileName + "." + filenameExtension;
//
//            Path path = Paths.get("src/main/resources/static/assets/" + newFileName);
//            Path pathOld = Paths.get("src/main/resources/static/assets/" + filePath);
//            InputStream inputStream = file.getInputStream();
//            Files.delete(pathOld);
//            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
//
//            image.setFilePath(newFileName);
//
//            Image savedImage = imageRepo.save(image);
//
//            Car savedCar = savedImage.getCar();
////
////            Hibernate.initialize(savedCar.getImages());
////            entityManager.clear();
//            return new ImageWithCarDTO(
//                    savedImage.getId(),
//                    new CarDTO(savedCar.getId(), savedCar.getModel(), savedCar.getBrand(), savedCar.getEngineCapacity(), savedCar.getPrice()),
//                    savedImage.getFilePath()
//            );
//        }
//        return null;
//    }
//    public CarWithImagesDTO getCarById(int id) {
//        Optional<Car> byId = carRepo.findById(id);
//        Car car;
//        if (byId.isPresent()) {
//            car = byId.get();
//            return new CarWithImagesDTO(car.getId(), car.getModel(), car.getBrand(), car.getEngineCapacity(), car.getPrice());
//        }
//        return null;
//    }
}
