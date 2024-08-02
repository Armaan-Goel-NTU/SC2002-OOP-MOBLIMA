package boundary.display.common;

import boundary.display.Choice;
import boundary.display.NavScreen;
import boundary.display.moviegoer.MovieGoerScreen;
import boundary.display.staff.StaffScreen;
import control.LoginManager;
import control.MainApp;
import enums.AccountType;

public class MainScreen extends NavScreen {

	public MainScreen(NavScreen prevScreen) {
		super(prevScreen);
	}

	@Override
	protected String getTitle() {
		return "Welcome to MOBLIMA!";
	}

	@Override
	protected String[] getOptions() {
		return new String[] { "Guest", "Login", "Exit" };
	}

	@Override
	protected void handleChoice(Choice choice) {
		if (choice.selection == 1) {
			new MovieGoerScreen(this).run();
		} else if (choice.selection == 2) {
			LoginManager loginManager = MainApp.getLoginManager();
			loginManager.tryLoggingIn(false);

			if (loginManager.isLoggedIn()) {
				if (loginManager.getAccountType() == AccountType.STAFF)
					new StaffScreen(this).run();
				else
					new MovieGoerScreen(this).run();
			} else {
				run();
			}
		} else {
			System.exit(0);
		}
	}

}
