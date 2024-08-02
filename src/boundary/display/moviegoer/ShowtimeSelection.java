package boundary.display.moviegoer;

import boundary.display.Choice;
import boundary.display.NavScreen;
import boundary.display.SelectionScreen;
import control.MainApp;

public class ShowtimeSelection extends NavScreen {

	private NavScreen movieInfoScreen;
	private int dateChoice;

	public ShowtimeSelection(NavScreen prevScreen, NavScreen movieInfoScreen, int dateChoice) {
		super(prevScreen);
		this.movieInfoScreen = movieInfoScreen;
		this.dateChoice = dateChoice;
	}

	@Override
	protected void preDisplay() {
		MainApp.getShowtimeManager().selectedDate(dateChoice);
		System.out.println("Showtime Listing");
		MainApp.getShowtimeManager().displayShowtimes();
	}

	@Override
	protected void handleChoice(Choice choice) {
		if (choice.selection == 1) {
			Choice locationChoice = new SelectionScreen(MainApp.getShowtimeManager().getCineplexes(), "Select Location")
					.getChoice();
			Choice showtimeChoice = new SelectionScreen(
					MainApp.getShowtimeManager().getShowTimings(locationChoice.option), "Select Showtime").getChoice();
			new TicketBookingScreen(this, movieInfoScreen, locationChoice.option, showtimeChoice.normal).run();
		} else {
			goBack();
		}

	}

	@Override
	protected String[] getOptions() {
		return new String[] { "Select showtime", "Back" };
	}

	@Override
	protected String getTitle() {
		return "";
	}

}
