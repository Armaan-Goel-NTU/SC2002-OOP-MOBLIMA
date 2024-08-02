package enums;

import java.util.Arrays;

import entities.metrics.IMetric;
import entities.metrics.Top5Ratings;
import entities.metrics.Top5Sales;

public enum MetricType {
	TOP_5_SALES("Top 5 by Ticket Sales", new Top5Sales()),
	TOP_5_RATING("Top 5 by Reviewers' Ratings", new Top5Ratings());

	private final String stringValue;
	private final IMetric metric;

	private MetricType(String name, IMetric metric) {
		this.stringValue = name;
		this.metric = metric;
	}

	@Override
	public String toString() {
		return stringValue;
	}

	public IMetric getMetric() {
		return metric;
	}

	public static String[] valueStrings() {
		return Arrays.stream(values()).map(Object::toString).toArray(String[]::new);
	}
}
