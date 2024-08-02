package control;

import java.util.List;
import java.util.Arrays;

import entities.IMovie;
import entities.metrics.IMetric;
import enums.MetricType;

public class MetricManager extends SavedManager {
	private List<MetricType> whitelist;
	private int selectedIndex;
	private List<IMovie> filteredList;

	public MetricManager(List<MetricType> whitelist) {
		this.whitelist = whitelist;
	}

	// Metric whitelist and selection managing methods
	public void toggleMetric(int selectedMetricIndex) {
		MetricType type = MetricType.values()[selectedMetricIndex];
		if (whitelist.contains(type)) {
			whitelist.remove(type);
		} else {
			whitelist.add(type);
		}
		save();
	}

	private IMetric getSelectedMetric() {
		return whitelist.get(selectedIndex).getMetric();
	}

	public void setMetricSelection(int whitelistIndex) {
		this.selectedIndex = whitelistIndex;
		this.filteredList = whitelist.get(selectedIndex).getMetric()
				.filter(MainApp.getMovieManager().getCurrentlyPurchasable());
	}

	public int getSelectedMovieId(int filteredIndex) {
		return filteredList.get(filteredIndex).getId();
	}

	// Metric-Related display methods
	public String getSelectedMetricName() {
		return whitelist.get(selectedIndex).toString();
	}

	public String[] getWhitelistedMetricNames() {
		return whitelist.stream().map(Object::toString).toArray(String[]::new);
	}

	private String[] getFormattedList(IMetric metric) {
		return metric.filter(MainApp.getMovieManager().getCurrentlyPurchasable()).stream().map(metric::format)
				.toArray(String[]::new);
	}

	public String[] getSelectedFormattedList() {
		return getFormattedList(getSelectedMetric());
	}

	private String getVisibilityStatus(MetricType metricType) {
		if (whitelist.contains(metricType)) {
			return "Visible";
		}
		return "Not Visible";
	}

	private String metricPreview(IMetric metric) {
		return String.join("\n", getFormattedList(metric)) + "\n";
	}

	public String[] getFullMetricInfo() {
		MetricType[] allTypes = MetricType.values();
		return Arrays.stream(allTypes)
				.map(x -> x.toString() + " - " + getVisibilityStatus(x) + "\n" + metricPreview(x.getMetric()))
				.toArray(String[]::new);
	}

	@Override
	public String getSaveFilePath() {
		return MainApp.METRIC_WHITELIST_FILE;
	}

	@Override
	public Object getObjectToSave() {
		return whitelist;
	}
}
