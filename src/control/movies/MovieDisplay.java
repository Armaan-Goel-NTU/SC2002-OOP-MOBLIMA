package control.movies;

import java.util.ArrayList;
import java.util.List;

import entities.FeedbackHelper;
import entities.IMovie;
import entities.Review;
import enums.MovieType;

public class MovieDisplay {

	private MovieManager manager;

	public MovieDisplay(MovieManager manager) {
		this.manager = manager;
	}

	public void displayMovieInfo(boolean forceShowType, int selectedMovieIndex) {
		IMovie movie = manager.getAllMovies().get(selectedMovieIndex);
		System.out.println(movie.getTitle() + "\n"
				+ ((forceShowType || movie.getType() != MovieType.NORMAL)
						? ("> " + movie.getType().toString() + "!!!" + "\n")
						: "")
				+ "\n"
				+ "Status: " + movie.getStatus().toString() + "\n"
				+ "Age Rating: " + movie.getAgeRating().toString() + "\n"
				+ "\n"
				+ "Director: " + movie.getDirector() + "\n"
				+ "Cast: " + String.join(", ", movie.getCast()) + "\n"
				+ "\n"
				+ "Synopsis: " + movie.getSynopsis() + "\n");
	}

	private String getRatingString(FeedbackHelper feedback) {
		if (feedback.getReviews().size() < 2) {
			return "NA";
		} else {
			return String.format("%.1f", feedback.getAverageRating());
		}
	}

	private List<String> getReviewStrings(FeedbackHelper feedback) {
		List<String> reviewStrings = new ArrayList<>();
		List<Review> reviews = feedback.getReviews();
		for (int i = 0; i < reviews.size(); i++) {
			if (!reviews.get(i).getContent().isBlank()) {
				reviewStrings.add(String.format("%.1f/%.1f", reviews.get(i).getRating(), FeedbackHelper.MAX_RATING)
						+ " - "
						+ reviews.get(i).getContent());
			}
		}
		return reviewStrings;
	}

	public void displayFeedback(int selectedMovieIndex) {
		FeedbackHelper feedback = manager.getAllMovies().get(selectedMovieIndex).getFeedback();
		System.out.println("Overall Rating: " + getRatingString(feedback));
		System.out.print("Audience Reviews: ");
		List<String> reviews = getReviewStrings(feedback);
		if (!reviews.isEmpty()) {
			for (String review : reviews) {
				System.out.println("\n" + review);
			}
		} else {
			System.out.println("Nothing so far!");
		}
		System.out.println();
	}
}
