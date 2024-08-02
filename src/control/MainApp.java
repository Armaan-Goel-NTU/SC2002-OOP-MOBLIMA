package control;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import boundary.display.common.MainScreen;
import control.booking.BookingManager;
import control.movies.MovieManager;
import entities.Booking;
import entities.Cineplex;
import entities.Movie;
import entities.PriceData;
import entities.Showtime;
import entities.rules.AlternateSeatsRule;
import entities.rules.IBookingRule;
import entities.rules.OccupiedRule;
import enums.MetricType;

public class MainApp {
	public static final String HOLIDAYS_FILE = "data/holidays.dat";
	public static final String MOVIES_FILE = "data/movies.dat";
	public static final String SHOWTIMES_FILE = "data/showtimes.dat";
	public static final String METRIC_WHITELIST_FILE = "data/metricWhitelist.dat";
	public static final String BOOKINGS_FILE = "data/bookings.dat";
	public static final String PRICES_FILE = "data/prices.dat";
	public static final String CINEPLEXES_FILE = "data/cineplexes.dat";
	public static final String STAFF_ACCOUNTS_FILE = "data/staffAccounts.txt";

	private static HolidayManager holidayManager;
	private static MovieManager movieManager;
	private static ShowtimeManager showtimeManager;
	private static CineplexManager cineplexManager;
	private static LoginManager loginManager;
	private static MetricManager metricManager;
	private static BookingManager bookingManager;
	private static PriceManager priceManager;

	private static InputHelper inputHelper;
	private static DateHelper dateHelper;

	private static Serializer serializer;

	private static void initializeLoginManager() {
		boolean successful = false;
		try {
			List<String> staffLines = new TextFileReader().readFile(STAFF_ACCOUNTS_FILE);
			HashMap<String, String> staffAccounts = new HashMap<>();
			for (String staffLine : staffLines) {
				if (!staffLine.isBlank()) {
					String[] tokens = staffLine.split("\\|");
					if (tokens.length != 2) {
						throw new IllegalArgumentException("Ill-formatted line '" + staffLine + "'");
					}
					staffAccounts.put(tokens[0].trim(), tokens[1].trim());
				}
			}
			loginManager = new LoginManager(staffAccounts);
			successful = true;
		} catch (FileNotFoundException e) {
			System.out.println("Could not find staffAccounts.txt");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		} finally {
			if (!successful)
				System.exit(0);
		}
	}

	private static void initializeAllManagers() {
		holidayManager = new HolidayManager(serializer.<LocalDate>deserializeList(HOLIDAYS_FILE, true));
		movieManager = new MovieManager(serializer.<Movie>deserializeList(MOVIES_FILE, true));
		showtimeManager = new ShowtimeManager(serializer.<Showtime>deserializeList(SHOWTIMES_FILE, true));
		metricManager = new MetricManager(serializer.<MetricType>deserializeList(METRIC_WHITELIST_FILE, true));
		bookingManager = new BookingManager(serializer.<Booking>deserializeList(BOOKINGS_FILE, true),
				new IBookingRule[] {
						new OccupiedRule(),
						new AlternateSeatsRule()
				});

		priceManager = new PriceManager((PriceData) serializer.deserializeFile(PRICES_FILE, false));
		cineplexManager = new CineplexManager(serializer.<Cineplex>deserializeList(CINEPLEXES_FILE, false));

		initializeLoginManager();
	}

	private static void initializeAllHelpers() {
		inputHelper = new InputHelper(new Scanner(System.in));
		dateHelper = new DateHelper();
	}

	public static void main(String[] args) {
		serializer = new Serializer();
		initializeAllHelpers();
		initializeAllManagers();

		new MainScreen(null).run();
	}

	public static HolidayManager getHolidayManager() {
		return holidayManager;
	}

	public static MovieManager getMovieManager() {
		return movieManager;
	}

	public static ShowtimeManager getShowtimeManager() {
		return showtimeManager;
	}

	public static CineplexManager getCineplexManager() {
		return cineplexManager;
	}

	public static LoginManager getLoginManager() {
		return loginManager;
	}

	public static MetricManager getMetricManager() {
		return metricManager;
	}

	public static BookingManager getBookingManager() {
		return bookingManager;
	}

	public static PriceManager getPriceManager() {
		return priceManager;
	}

	public static InputHelper getInputHelper() {
		return inputHelper;
	}

	public static DateHelper getDateHelper() {
		return dateHelper;
	}

	public static void saveObject(String fileName, Object o) {
		serializer.serializeObject(fileName, o);
	}
}
