package com.example.carsservice.data;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Car {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long carId;
	private String maker;
	private String model;
	
	public Car() {
	
	}
	
	public Car(String maker, String model) {
		this.maker = maker;
		this.model = model;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		
		Car car = (Car) o;
		
		if (!Objects.equals(carId, car.carId)) return false;
		if (!Objects.equals(maker, car.maker)) return false;
		return Objects.equals(model, car.model);
	}
	
	@Override
	public int hashCode() {
		int result = carId != null ? carId.hashCode() : 0;
		result = 31 * result + (maker != null ? maker.hashCode() : 0);
		result = 31 * result + (model != null ? model.hashCode() : 0);
		return result;
	}
	
	public Long getCarId() {
		return carId;
	}
	
	public void setCarId(Long carId) {
		this.carId = carId;
	}
	
	public String getMaker() {
		return maker;
	}
	
	public void setMaker(String maker) {
		this.maker = maker;
	}
	
	public String getModel() {
		return model;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	
	@Override
	public String toString() {
		return "Car{" +
				"carId=" + carId +
				", maker='" + maker + '\'' +
				", model='" + model + '\'' +
				'}';
	}
}
