package com.example.carsservice;

import com.example.carsservice.data.Car;
import com.example.carsservice.data.CarRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = CarsServiceApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

// switch AutoConfigureTestDatabase with TestPropertySource to use a real database
// @TestPropertySource(locations = "classpath:com/example/carsservice/application-integrationtest.properties")
public class CarsIntegrationTest {
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private CarRepository repository;
	
	@AfterEach
	public void resetDb() {
		repository.deleteAll();
	}
	
	@Test
	void whenValidInput_thenCreateCar() throws Exception {
		Car car1 = new Car("a", "a1");
		mvc.perform(post("/api/cars").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(car1)));
		
		List<Car> found = repository.findAll();
		assertThat(found).extracting(Car::getMaker).containsOnly("a");
	}
	
	@Test
	void givenCars_whenGetCars_thenStatus200() throws Exception {
		createTestCar("a", "a2");
		createTestCar("b", "b2");
		
		mvc.perform(get("/api/cars").contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
				.andExpect(jsonPath("$[0].model", is("a")))
				.andExpect(jsonPath("$[1].model", is("b")));
	}
	
	private void createTestCar(String model, String maker) {
		Car car = new Car(maker, model);
		repository.saveAndFlush(car);
	}
	
}
