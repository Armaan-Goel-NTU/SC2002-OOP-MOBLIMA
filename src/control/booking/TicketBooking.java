package control.booking;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import control.MainApp;
import entities.Booking;
import entities.IMovie;
import entities.Showtime;
import entities.rules.IBookingRule;
import entities.rules.RuleResult;
import enums.MovieGoer;
import enums.Seat;

public class TicketBooking {
	private SeatInput seatInput;
	private BookingManager manager;
	private List<Booking> bookings;
	private IBookingRule[] rules;

	public TicketBooking(BookingManager manager, List<Booking> bookings, IBookingRule[] rules) {
		this.manager = manager;
		this.bookings = bookings;
		this.rules = rules;
		this.seatInput = new SeatInput();
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public boolean bookTickets(Showtime showtime) {
		int tempBooked = 0;
		double currentTotal = 0;

		// Get ticket input for each type of Movie Goer
		for (MovieGoer movieGoer : MovieGoer.values()) {
			double movieGoerPrice = manager.getShowtimePrices().get(movieGoer);
			int numTickets = MainApp.getInputHelper().getIntInput("Number of " + movieGoer.toString() + " Tickets", 0,
					showtime.getHall().getCapacity() - showtime.getNumOccupied() - tempBooked);
			double subTotal = numTickets * movieGoerPrice;
			System.out.printf("%d * %.2f = %.2f%n", numTickets, movieGoerPrice, subTotal);
			tempBooked += numTickets;
			currentTotal += subTotal;
		}
		if (tempBooked == 0) {
			System.out.println("Must book at least 1 ticket!");
			return false;
		}

		// Seat Selection
		System.out.println("Select Seats:");
		ArrayList<String> selectedSeats = new ArrayList<>();
		boolean[][] tempOccupied = Arrays.stream(showtime.getOccupancy()).map(boolean[]::clone)
				.toArray(boolean[][]::new);
		boolean[][] currentlyBooked = new boolean[showtime.getHall().getNumRows()][showtime.getHall().getNumColumns()];

		int i = 0;
		while (i < tempBooked) {
			seatInput.ask(showtime, i + 1);
			int selectedRow = seatInput.getSelectedRow();
			int selectedColumn = seatInput.getSelectedColumn();

			boolean passedAll = true;
			for (IBookingRule rule : rules) {
				RuleResult result = rule.checkRule(showtime.getHall().getSeatLayout(), tempOccupied, currentlyBooked,
						selectedRow, selectedColumn);
				if (!result.getPassed()) {
					System.out.println(result.getReason());
					passedAll = false;
					break;
				}
			}

			if (passedAll) {
				Seat selectedSeat = showtime.getHall().getSeatLayout()[selectedRow][selectedColumn];
				double seatCharge = MainApp.getPriceManager().getAddend(selectedSeat);
				currentTotal += seatCharge;
				System.out.printf(String.format("%s Seat (%s); +$%.2f; Total is $%.2f%n", selectedSeat.toString(),
						seatInput.getSelectedSeat(), seatCharge, currentTotal));
				tempOccupied[selectedRow][selectedColumn] = true;
				currentlyBooked[selectedRow][selectedColumn] = true;
				selectedSeats.add(seatInput.getSelectedSeat());
				i++;
			}
		}

		// Summary and confirmation
		System.out.println("Selected Seats: " + String.join("; ", selectedSeats));
		System.out.println(String.format("Total: $%.2f", currentTotal));

		if (MainApp.getInputHelper().getConfirmation("Confirm Booking?")) {
			while (!MainApp.getLoginManager().isLoggedIn()) {
				MainApp.getLoginManager().tryLoggingIn(true);
			}
			showtime.setOccupancy(tempOccupied);
			MainApp.getShowtimeManager().setSeatOccupancy(showtime, tempOccupied);

			IMovie movie = MainApp.getMovieManager().movieFromId(showtime.getMovieId());
			MainApp.getMovieManager().incrementSales(movie, tempBooked);

			String showtimeInfo = String.format("%s (%s)%nDate: %s%nTime: %s%nVenue: %s%nSeat(s): %s%nPaid $ %.2f",
					movie.getTitle(),
					showtime.getMovieFormat().toString(),
					showtime.getShowDateTime().format(MainApp.getDateHelper().getAbbreviatedDateFormat()),
					showtime.getShowDateTime().format(MainApp.getDateHelper().getTimeFormat()),
					showtime.getLocation(),
					String.join("; ", selectedSeats),
					currentTotal);

			System.out.printf("%nBooking Confirmed!%n%s%n", showtimeInfo);
			bookings.add(new Booking(showtime.getHall().getCode(), showtimeInfo));
			manager.save();
			return true;
		}
		return false;
	}
}
