package control.booking;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import control.MainApp;
import control.PriceManager;
import control.SavedManager;
import entities.Booking;
import entities.Showtime;
import entities.rules.IBookingRule;
import enums.MovieGoer;
import enums.SessionTime;

public class BookingManager extends SavedManager {
	private Map<MovieGoer, Double> showtimePrices;

	private BookingHistoryDisplay historyDisplay;
	private HallDisplay hallDisplay;
	private TicketBooking ticketBooking;

	public BookingManager(List<Booking> bookings, IBookingRule[] rules) {
		this.historyDisplay = new BookingHistoryDisplay(this);
		this.hallDisplay = new HallDisplay(this);
		this.ticketBooking = new TicketBooking(this, bookings, rules);
	}

	public List<Booking> getBookings() {
		return ticketBooking.getBookings();
	}

	public void displayBookings(String email, String phoneNumber) {
		historyDisplay.displayBookings(email, phoneNumber);
	}

	private void loadShowtimePrices(Showtime showtime) {
		PriceManager priceManager = MainApp.getPriceManager();

		SessionTime showtimeSession = SessionTime.fromDateTime(showtime.getShowDateTime());

		double baseShowPrice = priceManager.getBasePrice() +
				priceManager.getAddend(showtime.getMovie().getType()) +
				priceManager.getAddend(showtime.getMovieFormat()) +
				priceManager.getAddend(SessionTime.fromDateTime(showtime.getShowDateTime()));

		showtimePrices = new EnumMap<>(MovieGoer.class);
		for (MovieGoer movieGoer : MovieGoer.values()) {
			double addend = priceManager.getAddend(movieGoer);
			if (addend < 0 && showtimeSession == SessionTime.PUBLIC_HOLIDAY) {
				addend = 0;
			}
			double movieGoerPrice = baseShowPrice + addend;
			showtimePrices.put(movieGoer, movieGoerPrice);
		}
	}

	public Map<MovieGoer, Double> getShowtimePrices() {
		return showtimePrices;
	}

	public void displayHallAndPrices(Showtime showtime) {
		loadShowtimePrices(showtime);
		hallDisplay.displayHallAndPrices(showtime);
	}

	public boolean bookTickets(Showtime showtime) {
		return ticketBooking.bookTickets(showtime);
	}

	@Override
	public String getSaveFilePath() {
		return MainApp.BOOKINGS_FILE;
	}

	@Override
	public Object getObjectToSave() {
		return getBookings();
	}
}
