package boundary.display;

public class Choice {
	public final int normal;
	public final int selection;
	public final String option;

	public Choice(int selection, String[] options) {
		this.selection = selection;
		this.normal = selection - NumberedMenu.BASE;
		this.option = options[normal];
	}

}
