package entities.metrics;

import java.util.List;

import entities.FeedbackHelper;
import entities.IMovie;

public class Top5Ratings extends TopRatingMetric implements IMetric {

	@Override
	public List<IMovie> filter(List<IMovie> input) {
		return filter(input, 5);
	}

	@Override
	public String format(IMovie movie) {
		return movie.getTitle() + " - " +
				String.format("Rated %.1f/%.1f", movie.getFeedback().getAverageRating(), FeedbackHelper.MAX_RATING);
	}

}
