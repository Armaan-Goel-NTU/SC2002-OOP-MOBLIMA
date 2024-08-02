package boundary.display;

import control.MainApp;

public abstract class BaseNumberedScreen {
	private String[] options;

	protected abstract String[] getOptions();

	protected abstract String getTitle();

	protected final void show() {
		// Call and save options once when showing
		options = getOptions();
		String title = getTitle();
		new NumberedMenu(options, title).display();
	}

	// Returns cached options
	protected final String[] getCachedOptions() {
		return options;
	}

	protected final int getNumOptions() {
		return options.length;
	}

	public final Choice getChoice(String choiceLabel) {
		int selection = MainApp.getInputHelper().getIntInput(choiceLabel, NumberedMenu.BASE, getNumOptions());
		return new Choice(selection, options);
	}

	public final Choice getChoice() {
		return getChoice("Option");
	}
}
