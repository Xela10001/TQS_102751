package com.example.carsservice;

import com.example.carsservice.data.Car;
import com.example.carsservice.data.CarRepository;
import com.example.carsservice.service.CarManagerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CarsRepositoryTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@MockBean
	private CarManagerService carManagerService;
	
	@Autowired
	private CarRepository carRepository;

	@BeforeEach
	void contextLoads() {
		/*
		Car carA = new Car("a", "a2");
		Car carB = new Car("b", "b2");
		Car carC = new Car("c", "c2");
		*/
	}
	
	@Test
	void testGetAllCars() {
	
	}
	
	@Test
	void testRepositoryFindAll() {
		Car carA = new Car("a", "a2");
		Car carB = new Car("b", "b2");
		entityManager.persistAndFlush(carA);
		entityManager.persistAndFlush(carB);
		
		List<Car> cars = carRepository.findAll();
		assertThat(cars).containsExactly(carA, carB);
	}

}
