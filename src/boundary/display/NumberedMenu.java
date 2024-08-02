package boundary.display;

public class NumberedMenu {
	public static final int BASE = 1;

	private String[] options;
	private String title;

	public NumberedMenu(String[] options, String title) {
		this.options = options;
		this.title = title;
	}

	public void display() {
		System.out.println("\n" + title);
		for (int i = 0; i < options.length; i++) {
			System.out.printf("%d. %s%n", i + BASE, options[i]);
		}
	}

}
