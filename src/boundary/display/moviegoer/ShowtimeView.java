package boundary.display.moviegoer;

import boundary.display.Choice;
import boundary.display.NavScreen;
import control.MainApp;

public class ShowtimeView extends NavScreen {

	private int selectedMovieID;

	public ShowtimeView(NavScreen prevScreen, int selectedMovieID) {
		super(prevScreen);
		this.selectedMovieID = selectedMovieID;
	}

	@Override
	protected void preDisplay() {
		MainApp.getShowtimeManager().groupShowtimesByDate(selectedMovieID);
	}

	@Override
	protected String[] getOptions() {
		return concatOptions(MainApp.getShowtimeManager().getShowtimeDates(), "Back");
	}

	@Override
	protected String getTitle() {
		return "Choose a date";
	}

	@Override
	protected void handleChoice(Choice choice) {
		if (choice.selection == getNumOptions()) {
			goBack();
		} else {
			new ShowtimeSelection(this, prevScreen, choice.normal).run();
		}
	}

}
