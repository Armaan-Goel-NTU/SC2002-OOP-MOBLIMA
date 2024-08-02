package boundary.display.moviegoer;

import boundary.display.Choice;
import boundary.display.NavScreen;
import control.MainApp;

public class MovieGoerScreen extends NavScreen {

	public MovieGoerScreen(NavScreen prevScreen) {
		super(prevScreen);
	}

	@Override
	protected String[] getOptions() {
		String[] options = new String[] { "Movie Listings" };
		if (MainApp.getLoginManager().isLoggedIn()) {
			options = concatOptions(options, "Booking History");
			options = concatOptions(options, "Logout");
		} else {
			options = concatOptions(options, "Log in");
			options = concatOptions(options, "Back");
		}
		return options;
	}

	@Override
	protected String getTitle() {
		return "Welcome "
				+ (MainApp.getLoginManager().isLoggedIn() ? MainApp.getLoginManager().getDisplayName() : "Guest");
	}

	@Override
	protected void handleChoice(Choice choice) {
		if (choice.selection == 1) {
			new MovieListingScreen(this).run();
		} else if (choice.selection == getNumOptions()) {
			MainApp.getLoginManager().logOut();
			MainApp.getMovieManager().resetGuestReviews();
			goBack();
		} else {
			if (MainApp.getLoginManager().isLoggedIn()) {
				new BookingHistoryScreen(this).run();
			} else {
				MainApp.getLoginManager().tryLoggingIn(true);
				run();
			}
		}
	}

}
