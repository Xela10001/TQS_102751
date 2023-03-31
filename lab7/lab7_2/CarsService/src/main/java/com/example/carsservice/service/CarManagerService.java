package com.example.carsservice.service;

import com.example.carsservice.data.Car;
import com.example.carsservice.data.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarManagerService {
	
	@Autowired
	private CarRepository carRepository;
	
	public Car save(Car car) {
		carRepository.save(car);
		return car;
	}
	
	public List<Car> getAllCars() {
		return carRepository.findAll();
	}
	
	public Optional<Car> getCarDetails(Long id) {
		return Optional.of(carRepository.findByCarId(id));
	}
}
