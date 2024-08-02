package boundary.display.staff;

import boundary.display.Choice;
import boundary.display.NavScreen;
import control.MainApp;

public class ConfigurePricesScreen extends NavScreen {

	public ConfigurePricesScreen(NavScreen prevScreen) {
		super(prevScreen);
	}

	@Override
	protected String[] getOptions() {
		return new String[] { "Set Base Price", "Set Movie Goer Addends", "Set Movie Format Addends",
				"Set Movie Type Addends", "Set Session Type Addends", "Set Seat Type Addends", "Back" };
	}

	@Override
	protected String getTitle() {
		return "Pricing Configuration";
	}

	@Override
	protected void handleChoice(Choice choice) {
		switch (choice.selection) {
			case 1:
				MainApp.getPriceManager().setBasePrice();
				break;
			case 2:
				MainApp.getPriceManager().setMovieGoerAddends();
				break;
			case 3:
				MainApp.getPriceManager().setMovieFormatAddends();
				break;
			case 4:
				MainApp.getPriceManager().setMovieTypeAddends();
				break;
			case 5:
				MainApp.getPriceManager().setSessionTypeAddends();
				break;
			case 6:
				MainApp.getPriceManager().setSeatTypeAddends();
				break;
			case 7:
				goBack();
				break;
			default:
				break;
		}

		if (choice.selection < 7) {
			run();
		}
	}

}
