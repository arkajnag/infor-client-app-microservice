package io.demoClient.model;

import java.util.List;

public class CarBookingDTO {
	
	private List<CarBooking> bookings;

	public List<CarBooking> getBookings() {
		return bookings;
	}

	public void setBookings(List<CarBooking> bookings) {
		this.bookings = bookings;
	}

	public CarBookingDTO(List<CarBooking> bookings) {
		this.bookings = bookings;
	}

	public CarBookingDTO() {
		
	}

	@Override
	public String toString() {
		return "CarBookingDTO [bookings=" + bookings + "]";
	}
	
}
