package boundary.display.moviegoer;

import boundary.display.Choice;
import boundary.display.NavScreen;
import control.MainApp;

public class TicketBookingScreen extends NavScreen {

	private NavScreen movieInfoScreen;
	private String locationChoice;
	private int showtimeChoice;

	public TicketBookingScreen(NavScreen prevScreen, NavScreen movieInfoScreen, String locationChoice,
			int showtimeChoice) {
		super(prevScreen);
		this.movieInfoScreen = movieInfoScreen;
		this.locationChoice = locationChoice;
		this.showtimeChoice = showtimeChoice;
	}

	@Override
	protected void preDisplay() {
		System.out.println("Hall Layout");
		MainApp.getBookingManager()
				.displayHallAndPrices(MainApp.getShowtimeManager().getBookingShowtime(locationChoice, showtimeChoice));
	}

	@Override
	protected void handleChoice(Choice choice) {
		if (choice.selection == 1) {
			if (MainApp.getBookingManager()
					.bookTickets(MainApp.getShowtimeManager().getBookingShowtime(locationChoice, showtimeChoice))) {
				movieInfoScreen.run();
			} else {
				goBack();
			}
		} else {
			goBack();
		}
	}

	@Override
	protected String[] getOptions() {
		return new String[] { "Buy Tickets", "Back" };
	}

	@Override
	protected String getTitle() {
		return "";
	}

}
