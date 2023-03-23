package com.example.carsservice;

import com.example.carsservice.data.Car;
import com.example.carsservice.data.CarRepository;
import com.example.carsservice.service.CarManagerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CarsServiceTest {
	@Mock( lenient = true)
	private CarRepository carRepository;
	
	@InjectMocks
	private CarManagerService carManagerService;
	
	Car carA;
	Car carB;
	Car carC;
	
	@BeforeEach
	public void setUp() {
		carA = new Car("a", "a2");
		carB = new Car("b", "b2");
		carC = new Car("c", "c2");
		List<Car> allCars = Arrays.asList(carA, carB, carC);
		
		Mockito.when(carRepository.findByCarId(carA.getCarId())).thenReturn(carA);
		Mockito.when(carRepository.findByCarId(carB.getCarId())).thenReturn(carB);
		Mockito.when(carRepository.findByCarId(-1L)).thenReturn(null);
		Mockito.when(carRepository.findById(carC.getCarId())).thenReturn(Optional.of(carC));
		Mockito.when(carRepository.findAll()).thenReturn(allCars);
		Mockito.when(carRepository.findById(-2L)).thenReturn(Optional.empty());
		Mockito.when(carRepository.findByCarId(carA.getCarId())).thenReturn(carA);
	}
	
	@Test
	void testGetAllCars() {
		List<Car> cars = carManagerService.getAllCars();
		
		assertThat(cars).contains(carA, carB, carC);
	}
	
	@Test
	void testGetCarDetails() {
		Optional<Car> car = carManagerService.getCarDetails(carA.getCarId());
		
		assertThat(car).isEqualTo(Optional.of(carA));
	}
}
