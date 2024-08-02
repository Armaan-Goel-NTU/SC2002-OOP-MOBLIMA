package boundary.display;

public class SelectionScreen extends BaseNumberedScreen {

	private String[] options;
	private String title;

	public SelectionScreen(String[] options, String title) {
		super();
		this.options = options;
		this.title = title;
		show();
	}

	@Override
	protected String[] getOptions() {
		return options;
	}

	@Override
	protected String getTitle() {
		return title;
	}

}
