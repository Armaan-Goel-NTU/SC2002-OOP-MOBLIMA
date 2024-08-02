package boundary.display.staff;

import boundary.display.Choice;
import boundary.display.NavScreen;
import control.MainApp;

public class ConfigureMoviesScreen extends NavScreen {
	public ConfigureMoviesScreen(NavScreen prevScreen) {
		super(prevScreen);
	}

	@Override
	protected String[] getOptions() {
		return concatOptions(MainApp.getMovieManager().getAllMovieTitles(), new String[] { "Add Movie", "Back" });
	}

	@Override
	protected String getTitle() {
		return "Movie List - Select to edit";
	}

	@Override
	protected void handleChoice(Choice choice) {
		if (choice.selection == getNumOptions()) {
			goBack();
		} else if (choice.selection == getNumOptions() - 1) {
			MainApp.getMovieManager().addMovie();
			run();
		} else {
			new MovieEditingScreen(this, choice.normal).run();
		}
	}
}
