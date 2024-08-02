package boundary.display.moviegoer;

import boundary.display.Choice;
import boundary.display.NavScreen;
import control.MainApp;

public class MovieInfoScreen extends NavScreen {

	private int selectedCurrentMovieIndex;

	public MovieInfoScreen(NavScreen prevScreen, int selectedCurrentMovieIndex) {
		super(prevScreen);
		MainApp.getMovieManager().recordCurrentMovieSelection(selectedCurrentMovieIndex);
		this.selectedCurrentMovieIndex = selectedCurrentMovieIndex;
	}

	@Override
	protected void preDisplay() {
		MainApp.getMovieManager().displayMovieInfo(false);
		MainApp.getMovieManager().displayFeedback();
	}

	@Override
	protected String[] getOptions() {
		String[] options = new String[] {};
		if (MainApp.getMovieManager().canReview()) {
			options = concatOptions(options, "Give Review");
		}
		if (MainApp.getMovieManager().canViewShowtimes()) {
			options = concatOptions(options, "View Showtimes");
		}
		return concatOptions(options, "Back");
	}

	@Override
	protected String getTitle() {
		return "Movie Info";
	}

	@Override
	protected void handleChoice(Choice choice) {
		if (choice.option.equals("Give Review")) {
			MainApp.getMovieManager().addReview();
			run();
		} else if (choice.option.equals("View Showtimes")) {
			new ShowtimeView(this, MainApp.getMovieManager().movieIdFromCurrentIndex(selectedCurrentMovieIndex)).run();
		} else {
			goBack();
		}

	}

}
