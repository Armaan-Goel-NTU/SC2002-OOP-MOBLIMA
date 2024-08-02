package boundary.display.staff;

import boundary.display.Choice;
import boundary.display.NavScreen;
import control.MainApp;

public class ShowtimeRemovalMovieScreen extends NavScreen {

	public ShowtimeRemovalMovieScreen(NavScreen prevScreen) {
		super(prevScreen);
	}

	@Override
	protected String[] getOptions() {
		return concatOptions(MainApp.getMovieManager().getCurrentMovieTitles(), "Back");
	}

	@Override
	protected String getTitle() {
		return "Choose Movie";
	}

	@Override
	protected void handleChoice(Choice choice) {
		if (choice.selection == getOptions().length) {
			goBack();
		} else {
			new ShowtimeRemovalLocationScreen(this, choice.normal).run();
		}

	}

}
