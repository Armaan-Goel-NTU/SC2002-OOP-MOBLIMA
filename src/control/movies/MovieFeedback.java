package control.movies;

import java.util.ArrayList;

import control.MainApp;
import entities.FeedbackHelper;
import entities.IMovie;
import enums.MovieStatus;

public class MovieFeedback {

	private MovieManager manager;
	private ArrayList<IMovie> guestReviewed;

	public MovieFeedback(MovieManager manager) {
		this.manager = manager;
		this.guestReviewed = new ArrayList<>();
	}

	public boolean canViewShowtimes() {
		MovieStatus status = manager.getAllMovies().get(manager.getSelectedIndex()).getStatus();
		return status == MovieStatus.PREVIEW || status == MovieStatus.NOW_SHOWING;
	}

	public boolean canReview() {
		IMovie selectedMovie = manager.getAllMovies().get(manager.getSelectedIndex());
		return (manager.getAllMovies().get(manager.getSelectedIndex()).getStatus() == MovieStatus.NOW_SHOWING) &&
				((!MainApp.getLoginManager().isLoggedIn() && !guestReviewed.contains(selectedMovie)) ||
						(MainApp.getLoginManager().isLoggedIn() && !selectedMovie.getFeedback()
								.reviewedByAuthor(MainApp.getLoginManager().getEmail())));
	}

	public void storeAllGuestReviews(String userEmail) {
		for (IMovie movie : guestReviewed) {
			if (movie.getFeedback().reviewedByAuthor(userEmail)) {
				movie.getFeedback().discardRecent();
			} else {
				movie.getFeedback().setRecentAuthor(userEmail);
			}
		}
		manager.save();
	}

	public void resetGuestReviews() {
		this.guestReviewed = new ArrayList<>();
	}

	public void addReview() {
		double rating = MainApp.getInputHelper().getDoubleInput("Rating (1-10)", FeedbackHelper.MIN_RATING,
				FeedbackHelper.MAX_RATING);
		String review = MainApp.getInputHelper().getStringInput("Comments (Optional)", true);
		String author = "";
		if (MainApp.getLoginManager().isLoggedIn()) {
			author = MainApp.getLoginManager().getEmail();
		} else {
			guestReviewed.add(manager.getAllMovies().get(manager.getSelectedIndex()));
		}
		manager.getAllMovies().get(manager.getSelectedIndex()).getFeedback().addReview(rating, review, author);
		manager.save();
	}

	public void incrementSales(IMovie movie, int amount) {
		manager.getAllMovies().get(manager.getAllMovies().indexOf(movie)).getFeedback().incrementSales(amount);
		manager.save();
	}
}
