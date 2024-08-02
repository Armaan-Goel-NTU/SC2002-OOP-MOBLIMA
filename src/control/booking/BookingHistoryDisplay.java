package control.booking;

import entities.Booking;

public class BookingHistoryDisplay {

	private BookingManager manager;

	public BookingHistoryDisplay(BookingManager manager) {
		this.manager = manager;
	}

	public void displayBookings(String email, String phoneNumber) {
		Booking[] bookingHistory = manager.getBookings().stream()
				.filter(x -> x.getEmail().equals(email) || x.getPhoneNumber().equals(phoneNumber))
				.toArray(Booking[]::new);

		for (int i = 0; i < bookingHistory.length; i++) {
			System.out.printf("%d. %s%n%n", i + 1, bookingHistory[i].toString());
		}

		if (bookingHistory.length == 0) {
			System.out.println("No Booking History!");
		}
	}
}
