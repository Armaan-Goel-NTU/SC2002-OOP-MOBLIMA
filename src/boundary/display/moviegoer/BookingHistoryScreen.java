package boundary.display.moviegoer;

import boundary.display.Choice;
import boundary.display.NavScreen;
import control.MainApp;

public class BookingHistoryScreen extends NavScreen {

	public BookingHistoryScreen(NavScreen prevScreen) {
		super(prevScreen);
	}

	@Override
	protected void preDisplay() {
		MainApp.getBookingManager().displayBookings(MainApp.getLoginManager().getEmail(),
				MainApp.getLoginManager().getMobileNumber());
	}

	@Override
	protected String[] getOptions() {
		return new String[] { "Back" };
	}

	@Override
	protected String getTitle() {
		return "Booking History";
	}

	@Override
	protected void handleChoice(Choice choice) {
		goBack();
	}

}
