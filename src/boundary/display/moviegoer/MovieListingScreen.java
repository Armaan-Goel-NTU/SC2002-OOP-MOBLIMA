package boundary.display.moviegoer;

import boundary.display.Choice;
import boundary.display.NavScreen;
import control.MainApp;

public class MovieListingScreen extends NavScreen {

	public MovieListingScreen(NavScreen prevScreen) {
		super(prevScreen);
	}

	@Override
	protected String[] getOptions() {
		return concatOptions(concatOptions(MainApp.getMovieManager().getCurrentMovieTitles(),
				MainApp.getMetricManager().getWhitelistedMetricNames()), "Back");
	}

	@Override
	protected String getTitle() {
		return "Movie Listings";
	}

	@Override
	protected void handleChoice(Choice choice) {
		int numCurrentMovies = MainApp.getMovieManager().getCurrentMovieTitles().length;
		if (choice.selection == getNumOptions()) {
			goBack();
		} else if (choice.selection <= numCurrentMovies) {
			new MovieInfoScreen(this, choice.normal).run();
		} else {
			int x = choice.normal - numCurrentMovies;
			new FilteredMovieScreen(this, x).run();
		}
	}

}
