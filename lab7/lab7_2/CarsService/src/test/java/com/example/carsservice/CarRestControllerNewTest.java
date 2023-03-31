package com.example.carsservice;

import com.example.carsservice.boundary.CarController;
import com.example.carsservice.data.Car;
import com.example.carsservice.service.CarManagerService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;

@WebMvcTest(CarController.class)
public class CarRestControllerNewTest {
	@Autowired
	MockMvc mockMvc;
	
	
	@MockBean
	CarManagerService service;
	
	@BeforeEach
	public void setUp() {
		RestAssuredMockMvc.mockMvc(mockMvc);
	}
	
	@Test
	void testCreateCar( ) {
		Car car1 = new Car("a", "b");
		
		when(service.save(Mockito.any())).thenReturn(car1);
		
		RestAssuredMockMvc.given().header("Content-type", "application/json")
				.and()
				.body(car1)
				.when()
				.post("/api/cars")
				.then()
				.statusCode(201);
		
		verify(service, times(1)).save(Mockito.any());
	}
}
