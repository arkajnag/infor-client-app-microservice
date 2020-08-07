package io.demoClient.model;

import java.util.List;

public class CarDTO {
	
	private List<Car> cars;

	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}

	public CarDTO(List<Car> cars) {
		this.cars = cars;
	}

	public CarDTO() {
		
	}

	@Override
	public String toString() {
		return "CarDTO [cars=" + cars + "]";
	}
}
