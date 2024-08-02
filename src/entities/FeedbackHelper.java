package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FeedbackHelper implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final double MIN_RATING = 1;
	public static final double MAX_RATING = 10;
	private int sales;
	private List<Review> reviews;

	public FeedbackHelper() {
		sales = 0;
		reviews = new ArrayList<>();
	}

	public int getSales() {
		return sales;
	}

	public void setRecentAuthor(String author) {
		reviews.get(reviews.size() - 1).setAuthor(author);
	}

	public void discardRecent() {
		reviews.remove(reviews.size() - 1);
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public boolean reviewedByAuthor(String author) {
		return reviews.stream().map(Review::getAuthor).toList().contains(author);
	}

	public double getAverageRating() {
		return reviews.stream().mapToDouble(Review::getRating).sum() / reviews.size();
	}

	public void addReview(double rating, String review, String author) {
		reviews.add(new Review(rating, review, author));
	}

	public void incrementSales(int amount) {
		sales += amount;
	}
}
