package entities.metrics;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import entities.IMovie;

public abstract class TopSalesMetric implements IInternalMetric {

	@Override
	public List<IMovie> filter(List<IMovie> input, int amount) {
		ArrayList<IMovie> applicable = input.stream().filter(x -> x.getFeedback().getSales() > 0)
				.collect(Collectors.toCollection(ArrayList::new));
		applicable.sort((m1, m2) -> Integer.compare(m2.getFeedback().getSales(), m1.getFeedback().getSales()));
		return applicable.stream().limit(amount).collect(Collectors.toCollection(ArrayList::new));
	}

}
