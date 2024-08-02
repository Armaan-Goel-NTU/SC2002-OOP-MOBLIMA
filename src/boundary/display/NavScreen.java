package boundary.display;

import java.util.Arrays;

public abstract class NavScreen extends BaseNumberedScreen {
	protected abstract void handleChoice(Choice choice);

	protected NavScreen prevScreen;

	protected NavScreen(NavScreen prevScreen) {
		super();
		this.prevScreen = prevScreen;
	}

	protected void preDisplay() {
	}

	public void run() {
		preDisplay();
		show();
		handleChoice(getChoice());
	}

	protected final void goBack() {
		if (prevScreen == null) {
			System.exit(0);
		} else {
			prevScreen.run();
		}
	}

	protected final String[] concatOptions(String[] a, String[] b) {
		String[] both = Arrays.copyOf(a, a.length + b.length);
		System.arraycopy(b, 0, both, a.length, b.length);
		return both;
	}

	protected final String[] concatOptions(String[] a, String b) {
		String[] both = Arrays.copyOf(a, a.length + 1);
		both[both.length - 1] = b;
		return both;
	}
}
