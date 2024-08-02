package entities.metrics;

import java.util.List;

import entities.IMovie;

public interface IMetric extends IInternalMetric {
	public List<IMovie> filter(List<IMovie> input);

	public String format(IMovie input);
}
