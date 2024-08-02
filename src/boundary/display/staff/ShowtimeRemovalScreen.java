package boundary.display.staff;

import boundary.display.Choice;
import boundary.display.NavScreen;
import control.MainApp;

public class ShowtimeRemovalScreen extends NavScreen {

	private int currentMovieIndex;
	private int locationIndex;

	public ShowtimeRemovalScreen(NavScreen prevScreen, int currentMovieIndex, int locationIndex) {
		super(prevScreen);
		this.currentMovieIndex = currentMovieIndex;
		this.locationIndex = locationIndex;
	}

	@Override
	protected String[] getOptions() {
		return concatOptions(MainApp.getShowtimeManager().getShowtimeRemovalOptions(currentMovieIndex, locationIndex),
				"Back");
	}

	@Override
	protected String getTitle() {
		if (getOptions().length > 1) {
			return "Choose a Showtime";
		} else {
			return "No show in selection";
		}
	}

	@Override
	protected void handleChoice(Choice choice) {
		if (choice.selection == getNumOptions()) {
			goBack();
		} else {
			MainApp.getShowtimeManager().removeShowtime(choice.normal);
			run();
		}
	}

}
