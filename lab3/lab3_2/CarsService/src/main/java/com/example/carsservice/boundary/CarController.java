package com.example.carsservice.boundary;

import com.example.carsservice.data.Car;
import com.example.carsservice.service.CarManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * API endpoints. Try with Postman or curl
 * $curl -v http://localhost:8080/api/v1/cars
 */
@RestController
@RequestMapping("/api")
public class CarController {
	
	private final CarManagerService carManagerService;
	
	/**
	 * Using constructor Injection instead of @autowired
	 * When using a constructor to set injected properties, you do not have to provide the Autowire annotation.
	 *
	 * @param carManagerService the service that manages cars (must not be null)
	 */
	public CarController(CarManagerService carManagerService) {
		this.carManagerService = Objects.requireNonNull(carManagerService, "carManagerService must not be null");
	}
	
	@PostMapping("/cars")
	public ResponseEntity<Car> createCar(@RequestBody Car car) {
		Car saved = carManagerService.save(car);
		if (saved != null) {
			URI location = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(saved.getCarId())
					.toUri();
			return ResponseEntity.created(location).body(saved); // Return HTTP 201 Created status with the saved car object
		} else {
			return ResponseEntity.badRequest().build(); // Return HTTP 400 Bad Request status if the car could not be saved
		}
	}
	
	
	@GetMapping("/cars")
	public List<Car> getAllCars() {
		return carManagerService.getAllCars();
	}
	
	@GetMapping("/cars/{id}")
	public ResponseEntity<Car> getCarById(@PathVariable Long id) {
		Optional<Car> car = carManagerService.getCarDetails(id);
		if (car.isPresent()) {
			return ResponseEntity.ok(car.get()); // Return HTTP 200 OK status with the car object
		} else {
			return ResponseEntity.notFound().build(); // Return HTTP 404 Not Found status if the car is not found
		}
	}
	
	
}