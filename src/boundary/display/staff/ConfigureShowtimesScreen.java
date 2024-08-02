package boundary.display.staff;

import boundary.display.Choice;
import boundary.display.NavScreen;
import control.MainApp;

public class ConfigureShowtimesScreen extends NavScreen {

	public ConfigureShowtimesScreen(NavScreen prevScreen) {
		super(prevScreen);
	}

	@Override
	protected String[] getOptions() {
		return new String[] { "Add Showtime", "Remove Showtime", "Back" };
	}

	@Override
	protected String getTitle() {
		return "Manage Showtimes";
	}

	@Override
	protected void handleChoice(Choice choice) {
		if (choice.selection == 1) {
			MainApp.getShowtimeManager().addShowtime();
			run();
		} else if (choice.selection == 2) {
			new ShowtimeRemovalMovieScreen(this).run();
		} else {
			goBack();
		}
	}

}
