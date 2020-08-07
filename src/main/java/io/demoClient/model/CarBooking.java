package io.demoClient.model;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CarBooking {

	private Integer bookingId;
	private Integer carPlateNumber;
	private Integer userId;
	private LocalDateTime bookingStartDate;
	private LocalDateTime bookingEndDate;
	private LocalDateTime bookingTime;
	private Double bookingAmount;

	public Integer getBookingId() {
		return bookingId;
	}

	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}

	public Integer getCarPlateNumber() {
		return carPlateNumber;
	}

	public void setCarPlateNumber(Integer carPlateNumber) {
		this.carPlateNumber = carPlateNumber;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public LocalDateTime getBookingStartDate() {
		return bookingStartDate;
	}

	public void setBookingStartDate(LocalDateTime bookingStartDate) {
		this.bookingStartDate = bookingStartDate;
	}

	public LocalDateTime getBookingEndDate() {
		return bookingEndDate;
	}

	public void setBookingEndDate(LocalDateTime bookingEndDate) {
		this.bookingEndDate = bookingEndDate;
	}

	public LocalDateTime getBookingTime() {
		return bookingTime;
	}

	public void setBookingTime(LocalDateTime bookingTime) {
		this.bookingTime = bookingTime;
	}

	public Double getBookingAmount() {
		return bookingAmount;
	}

	public void setBookingAmount(Double bookingAmount) {
		this.bookingAmount = bookingAmount;
	}

	public CarBooking(Integer bookingId, Integer carPlateNumber, Integer userId, LocalDateTime bookingStartDate,
			LocalDateTime bookingEndDate, LocalDateTime bookingTime, Double bookingAmount) {
		this.bookingId = bookingId;
		this.carPlateNumber = carPlateNumber;
		this.userId = userId;
		this.bookingStartDate = bookingStartDate;
		this.bookingEndDate = bookingEndDate;
		this.bookingTime = bookingTime;
		this.bookingAmount = bookingAmount;
	}

	public CarBooking() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "CarBooking [bookingId=" + bookingId + ", carPlateNumber=" + carPlateNumber + ", userId=" + userId
				+ ", bookingStartDate=" + bookingStartDate + ", bookingEndDate=" + bookingEndDate + ", bookingTime="
				+ bookingTime + ", bookingAmount=" + bookingAmount + "]";
	}

}
