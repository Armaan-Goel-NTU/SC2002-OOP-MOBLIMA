package entities.metrics;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import entities.IMovie;

public abstract class TopRatingMetric implements IInternalMetric {

	@Override
	public List<IMovie> filter(List<IMovie> input, int amount) {
		ArrayList<IMovie> applicableMovies = input.stream().filter(x -> x.getFeedback().getReviews().size() > 1)
				.collect(Collectors.toCollection(ArrayList::new));
		applicableMovies.sort(
				(m1, m2) -> Double.compare(m2.getFeedback().getAverageRating(), m1.getFeedback().getAverageRating()));
		return applicableMovies.stream().limit(amount).collect(Collectors.toCollection(ArrayList::new));
	}

}
