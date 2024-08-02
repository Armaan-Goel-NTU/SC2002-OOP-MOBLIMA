package boundary.display.staff;

import boundary.display.Choice;
import boundary.display.NavScreen;
import control.MainApp;

public class ShowtimeRemovalLocationScreen extends NavScreen {

	private int currentMovieIndex;

	public ShowtimeRemovalLocationScreen(NavScreen prevScreen, int currentMovieIndex) {
		super(prevScreen);
		this.currentMovieIndex = currentMovieIndex;
	}

	@Override
	protected String[] getOptions() {
		return concatOptions(MainApp.getCineplexManager().getLocations(), "Back");
	}

	@Override
	protected String getTitle() {
		return "Choose Location";
	}

	@Override
	protected void handleChoice(Choice choice) {
		if (choice.selection == getNumOptions()) {
			goBack();
		} else {
			new ShowtimeRemovalScreen(this, currentMovieIndex, choice.normal).run();
		}
	}

}
