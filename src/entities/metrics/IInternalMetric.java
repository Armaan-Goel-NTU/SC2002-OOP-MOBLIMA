package entities.metrics;

import java.util.List;

import entities.IMovie;

public interface IInternalMetric {
	public List<IMovie> filter(List<IMovie> input, int amount);
}
