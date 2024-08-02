package boundary.display.moviegoer;

import boundary.display.Choice;
import boundary.display.NavScreen;
import control.MainApp;

public class FilteredMovieScreen extends NavScreen {

	public FilteredMovieScreen(NavScreen prevScreen, int whitelistIndex) {
		super(prevScreen);
		MainApp.getMetricManager().setMetricSelection(whitelistIndex);
	}

	@Override
	protected String[] getOptions() {
		return concatOptions(MainApp.getMetricManager().getSelectedFormattedList(), "Back");
	}

	@Override
	protected String getTitle() {
		return MainApp.getMetricManager().getSelectedMetricName();
	}

	@Override
	protected void handleChoice(Choice choice) {
		if (choice.selection < getNumOptions()) {
			new MovieInfoScreen(this,
					MainApp.getMovieManager()
							.currentMovieIndexFromId(MainApp.getMetricManager().getSelectedMovieId(choice.normal)))
					.run();
		} else {
			goBack();
		}
	}

}
