package control;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import boundary.display.SelectionScreen;
import entities.Cinema;
import entities.Cineplex;
import entities.Showtime;
import enums.MovieFormat;

public class ShowtimeManager extends SavedManager {
	private List<Showtime> showtimes;

	private List<Showtime> filteredList;

	private Map<LocalDate, List<Showtime>> showtimesByDate;
	private List<LocalDate> dateSet;

	private Map<String, List<Showtime>> showtimesByCineplex;
	private List<String> cineplexSet;

	public ShowtimeManager(List<Showtime> showtimes) {
		this.showtimes = removeOldShowtimes(showtimes);
		save();
	}

	// Showtime managing (adding/removing) methods
	private List<Showtime> removeOldShowtimes(List<Showtime> showtimeList) {
		for (int i = showtimeList.size() - 1; i >= 0; i--) {
			if (showtimeList.get(i).getShowDateTime().isBefore(MainApp.getDateHelper().now())) {
				showtimeList.remove(i);
			}
		}
		return showtimeList;
	}

	public String[] getShowtimeRemovalOptions(int currentMovieIndex, int locationIndex) {
		int movieId = MainApp.getMovieManager().movieIdFromCurrentIndex(currentMovieIndex);
		String location = MainApp.getCineplexManager().getLocations()[locationIndex];

		filteredList = showtimes.stream().filter(x -> x.getMovieId() == movieId && x.getLocation().equals(location))
				.collect(Collectors.toList());

		String[] options = new String[filteredList.size()];

		for (int i = 0; i < filteredList.size(); i++) {
			options[i] = filteredList.get(i).getShowDateTime().format(MainApp.getDateHelper().getTimeFormat()) +
					" (" +
					filteredList.get(i).getHall().getHallName() +
					"; " +
					filteredList.get(i).getMovieFormat().toString() +
					")";
		}
		return options;
	}

	public void removeShowtime(int filteredIndex) {
		showtimes.remove(showtimes.indexOf(filteredList.get(filteredIndex)));
		System.out.println("Removed Showtime!");
		save();
	}

	private Showtime deepCopy(Showtime showtime) {
		Showtime anotherShowtime = new Showtime();
		anotherShowtime.setMovieId(showtime.getMovieId());
		anotherShowtime.setHall(showtime.getHall());
		anotherShowtime.setLocation(showtime.getLocation());
		anotherShowtime.setMovieFormat(showtime.getMovieFormat());
		anotherShowtime.setShowDateTime(showtime.getShowDateTime());
		return anotherShowtime;
	}

	public void addShowtime() {
		if (MainApp.getMovieManager().getCurrentMovieTitles().length < 1) {
			System.out.println("Add a Current Movie First!");
			return;
		}
		Showtime newShowtime = new Showtime();
		newShowtime.setMovieId(chooseMovie());
		chooseHallAndFormat(newShowtime);

		boolean addedOnce = false;
		while (!addedOnce || MainApp.getInputHelper().getConfirmation("Add more for same day?")) {
			if (!addedOnce) {
				newShowtime.setShowDateTime(chooseDateTime());
			} else {
				Showtime anotherShowtime = deepCopy(newShowtime);
				anotherShowtime.setShowDateTime(chooseTime(newShowtime.getShowDateTime().toLocalDate()));
				newShowtime = anotherShowtime;
			}
			System.out.println(String.format("Added Showtime for %s; %s; %s; %s; at %s",
					MainApp.getMovieManager().movieFromId(newShowtime.getMovieId()).getTitle(),
					newShowtime.getLocation(),
					newShowtime.getHall().getHallName(),
					newShowtime.getMovieFormat().toString(),
					newShowtime.getShowDateTime().format(MainApp.getDateHelper().getTimeFormat())));
			showtimes.add(newShowtime);
			addedOnce = true;
		}
		save();
	}

	private String[] getFormatNames(MovieFormat[] formats) {
		return Arrays.stream(formats).map(Object::toString).toArray(String[]::new);
	}

	private void chooseHallAndFormat(Showtime showtime) {
		int selectionIndex = new SelectionScreen(MainApp.getCineplexManager().getLocations(), "Choose a Cineplex")
				.getChoice().normal;

		Cineplex cineplex = MainApp.getCineplexManager().getCineplex(selectionIndex);
		showtime.setLocation(cineplex.getLocation());

		Cinema[] cinemas = cineplex.getCinemaHalls();
		String[] hallNames = Arrays.stream(cinemas)
				.map(x -> x.getHallName() + "(" + String.join("; ", getFormatNames(x.getSupportedFormats())) + ")")
				.toArray(String[]::new);

		selectionIndex = new SelectionScreen(hallNames, "Choose a Hall").getChoice().normal;

		Cinema selectedCinema = cinemas[selectionIndex];
		showtime.setHall(selectedCinema);

		MovieFormat[] supportedFormats = selectedCinema.getSupportedFormats();
		if (supportedFormats.length > 1) {
			selectionIndex = new SelectionScreen(getFormatNames(supportedFormats), "Choose a Format")
					.getChoice().normal;
			showtime.setMovieFormat(supportedFormats[selectionIndex]);

		} else {
			showtime.setMovieFormat(supportedFormats[0]);
		}
	}

	private int chooseMovie() {
		int selectionIndex = new SelectionScreen(MainApp.getMovieManager().getCurrentMovieTitles(), "Choose a Movie")
				.getChoice().normal;
		return MainApp.getMovieManager().movieIdFromCurrentIndex(selectionIndex);
	}

	private ZonedDateTime chooseDateTime() {
		String dateTimeString = MainApp.getInputHelper()
				.getStringInput("Date and Time in " + DateHelper.DATE_TIME_FORMAT + " format", false);
		try {
			DateHelper dateHelper = MainApp.getDateHelper();
			DateTimeFormatter formatter = dateHelper.getDateTimeFormat();
			LocalDateTime parsedDateTime = LocalDateTime.parse(dateTimeString, formatter);
			ZonedDateTime dateTime = ZonedDateTime.of(parsedDateTime, dateHelper.localTimeZone());
			if (dateTime.isBefore(dateHelper.now())) {
				throw new IllegalArgumentException("Must be a future date!");
			}
			return dateTime;
		} catch (DateTimeParseException e) {
			System.out.println("Please enter a valid date and time string!");
			return chooseDateTime();
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return chooseDateTime();
		}
	}

	private ZonedDateTime chooseTime(LocalDate withDate) {
		String timeString = MainApp.getInputHelper().getStringInput("Time in " + DateHelper.TIME_FORMAT + " format",
				false);
		try {
			DateHelper dateHelper = MainApp.getDateHelper();
			DateTimeFormatter formatter = dateHelper.getTimeFormat();
			LocalTime parsedTime = LocalTime.parse(timeString, formatter);
			ZonedDateTime dateTime = ZonedDateTime.of(withDate.atTime(parsedTime), dateHelper.localTimeZone());
			if (dateTime.isBefore(dateHelper.now())) {
				throw new IllegalArgumentException("Must be a future date!");
			}
			return dateTime;
		} catch (DateTimeParseException e) {
			System.out.println("Please enter a time string!");
			return chooseTime(withDate);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return chooseTime(withDate);
		}

	}

	// Showtime filtering methods
	public void groupShowtimesByDate(int movieId) {
		List<Showtime> showtimesByMovie = showtimes.stream().filter(x -> x.getMovieId() == movieId)
				.collect(Collectors.toList());
		showtimesByDate = showtimesByMovie.stream()
				.collect(Collectors.groupingBy(x -> x.getShowDateTime().toLocalDate()));
		dateSet = new ArrayList<>(showtimesByDate.keySet());
		Collections.sort(dateSet);
	}

	public String[] getShowtimeDates() {
		return dateSet.stream().map(x -> x.toString()).toArray(String[]::new);
	}

	public void selectedDate(int dateChoice) {
		List<Showtime> shows = showtimesByDate.get(dateSet.get(dateChoice));
		showtimesByCineplex = shows.stream().collect(Collectors.groupingBy(x -> x.getLocation()));
		cineplexSet = new ArrayList<>(showtimesByCineplex.keySet());
		Collections.sort(cineplexSet);
	}

	public String[] getCineplexes() {
		return cineplexSet.toArray(String[]::new);
	}

	public String[] getShowTimings(String cineplex) {
		List<Showtime> cineplexShowtimes = showtimesByCineplex.get(cineplex);
		return cineplexShowtimes.stream()
				.map(x -> x.getShowDateTime().format(MainApp.getDateHelper().getTimeFormat()) + " ("
						+ x.getMovieFormat().toString() + ")")
				.toArray(String[]::new);
	}

	public Showtime getBookingShowtime(String cineplex, int showtimeChoice) {
		return showtimesByCineplex.get(cineplex).get(showtimeChoice);
	}

	public void setSeatOccupancy(Showtime showtime, boolean[][] seatOccupancy) {
		showtimes.get(showtimes.indexOf(showtime)).setOccupancy(seatOccupancy);
		save();
	}

	// Showtime display method
	public void displayShowtimes() {
		for (String location : cineplexSet) {
			System.out.println("\n--> " + location);
			String[] timings = getShowTimings(location);
			for (String timing : timings) {
				System.out.println("> " + timing);
			}
		}
	}

	@Override
	public String getSaveFilePath() {
		return MainApp.SHOWTIMES_FILE;
	}

	@Override
	public Object getObjectToSave() {
		return showtimes;
	}
}
