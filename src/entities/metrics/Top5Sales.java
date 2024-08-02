package entities.metrics;

import java.util.List;

import entities.IMovie;

public class Top5Sales extends TopSalesMetric implements IMetric {

	@Override
	public List<IMovie> filter(List<IMovie> input) {
		return filter(input, 5);
	}

	@Override
	public String format(IMovie movie) {
		return movie.getTitle() + " - " + movie.getFeedback().getSales() + " sold";
	}

}
