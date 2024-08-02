package boundary.display.staff;

import boundary.display.Choice;
import boundary.display.NavScreen;
import control.MainApp;

public class StaffScreen extends NavScreen {

	public StaffScreen(NavScreen prevScreen) {
		super(prevScreen);
	}

	@Override
	protected String[] getOptions() {
		return new String[] { "Configure Movies", "Configure Showtimes", "Configure Ticket Prices",
				"Configure Public Holidays", "Configure Metric Visibility", "Logout" };
	}

	@Override
	protected String getTitle() {
		return "Logged in as " + MainApp.getLoginManager().getDisplayName();
	}

	@Override
	protected void handleChoice(Choice choice) {
		switch (choice.selection) {
			case 1:
				new ConfigureMoviesScreen(this).run();
				break;
			case 2:
				new ConfigureShowtimesScreen(this).run();
				break;
			case 3:
				new ConfigurePricesScreen(this).run();
				break;
			case 4:
				new ConfigureHolidayScreen(this).run();
				break;
			case 5:
				new ConfigureMetricVisibilityScreen(this).run();
				break;
			case 6:
				MainApp.getLoginManager().logOut();
				goBack();
				break;
			default:
				break;
		}
	}

}
