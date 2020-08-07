package io.demoClient.model;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;


@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Car {

    
    private Integer carPlateNumber;
    private String carModelName;
    private LocalDateTime carAvailableStartDate;
    private LocalDateTime carAvailableEndDate;
    private Double carRentalPricePerHour;
	public Integer getCarPlateNumber() {
		return carPlateNumber;
	}
	public void setCarPlateNumber(Integer carPlateNumber) {
		this.carPlateNumber = carPlateNumber;
	}
	public String getCarModelName() {
		return carModelName;
	}
	public void setCarModelName(String carModelName) {
		this.carModelName = carModelName;
	}
	public LocalDateTime getCarAvailableStartDate() {
		return carAvailableStartDate;
	}
	public void setCarAvailableStartDate(LocalDateTime carAvailableStartDate) {
		this.carAvailableStartDate = carAvailableStartDate;
	}
	public LocalDateTime getCarAvailableEndDate() {
		return carAvailableEndDate;
	}
	public void setCarAvailableEndDate(LocalDateTime carAvailableEndDate) {
		this.carAvailableEndDate = carAvailableEndDate;
	}
	public Double getCarRentalPricePerHour() {
		return carRentalPricePerHour;
	}
	public void setCarRentalPricePerHour(Double carRentalPricePerHour) {
		this.carRentalPricePerHour = carRentalPricePerHour;
	}
	public Car(Integer carPlateNumber, String carModelName, LocalDateTime carAvailableStartDate,
			LocalDateTime carAvailableEndDate, Double carRentalPricePerHour) {
		this.carPlateNumber = carPlateNumber;
		this.carModelName = carModelName;
		this.carAvailableStartDate = carAvailableStartDate;
		this.carAvailableEndDate = carAvailableEndDate;
		this.carRentalPricePerHour = carRentalPricePerHour;
	}
	public Car() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "Car [carPlateNumber=" + carPlateNumber + ", carModelName=" + carModelName + ", carAvailableStartDate="
				+ carAvailableStartDate + ", carAvailableEndDate=" + carAvailableEndDate + ", carRentalPricePerHour="
				+ carRentalPricePerHour + "]";
	}
    
    
}
