package com.example.carsservice;

import com.example.carsservice.boundary.CarController;
import com.example.carsservice.data.Car;
import com.example.carsservice.service.CarManagerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
public class CarsControllerTest {
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private CarManagerService service;
	
	// As resource efficient as possible
	
	Car carA;
	Car carB;
	Car carC;
	List<Car> allCars;
	
	@BeforeEach
	public void setUp() throws Exception {
	}
	
	@Test
	void testCreateCar( ) throws Exception {
		Car car1 = new Car("a", "b");
		
		when(service.save(Mockito.any())).thenReturn(car1);
		
		mvc.perform(post("/api/cars").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(car1)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.model", is("b")))
				.andExpect(jsonPath("$.maker", is("a")));
		
		verify(service, times(1)).save(Mockito.any());
	}
	
	@Test
	void givenManyCars_whenGetCars_thenReturnJsonArray() throws Exception {
		Car car1 = new Car("a", "a2");
		Car car2 = new Car("b", "b2");
		Car car3 = new Car("c", "c2");
		
		List<Car> allCars = Arrays.asList(car1, car2, car3);
		
		when(service.getAllCars()).thenReturn(allCars);
		
		mvc.perform(get("/api/cars").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[0].model", is(car1.getModel())))
				.andExpect(jsonPath("$[1].model", is(car2.getModel())))
				.andExpect(jsonPath("$[2].model", is(car3.getModel())))
				.andExpect(jsonPath("$[0].maker", is(car1.getMaker())))
				.andExpect(jsonPath("$[1].maker", is(car2.getMaker())))
				.andExpect(jsonPath("$[2].maker", is(car3.getMaker())));
		
		verify(service, times(1)).getAllCars();
	}
	
	
}
