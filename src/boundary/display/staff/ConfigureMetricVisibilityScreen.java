package boundary.display.staff;

import boundary.display.Choice;
import boundary.display.NavScreen;
import control.MainApp;

public class ConfigureMetricVisibilityScreen extends NavScreen {

	public ConfigureMetricVisibilityScreen(NavScreen prevScreen) {
		super(prevScreen);
	}

	@Override
	protected String[] getOptions() {
		return concatOptions(MainApp.getMetricManager().getFullMetricInfo(), "Back");
	}

	@Override
	protected String getTitle() {
		return "Select to toggle visibility";
	}

	@Override
	protected void handleChoice(Choice choice) {
		if (choice.selection == getNumOptions()) {
			goBack();
		} else {
			MainApp.getMetricManager().toggleMetric(choice.normal);
			run();
		}
	}

}
