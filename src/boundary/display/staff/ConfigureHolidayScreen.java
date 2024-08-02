package boundary.display.staff;

import boundary.display.Choice;
import boundary.display.NavScreen;
import control.MainApp;

public class ConfigureHolidayScreen extends NavScreen {

	public ConfigureHolidayScreen(NavScreen prevScreen) {
		super(prevScreen);
	}

	@Override
	protected String[] getOptions() {
		return concatOptions(MainApp.getHolidayManager().getHolidaysStringList(),
				new String[] { "Add New Holiday", "Back" });
	}

	@Override
	protected String getTitle() {
		return "Public Holidays - Select to remove";
	}

	@Override
	protected void handleChoice(Choice choice) {
		if (choice.selection == getNumOptions()) {
			goBack();
		} else if (choice.selection == getNumOptions() - 1) {
			MainApp.getHolidayManager().addHoliday();
			run();
		} else {
			MainApp.getHolidayManager().removeHoliday(choice.normal);
			run();
		}
	}

}
