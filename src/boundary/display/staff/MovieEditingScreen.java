package boundary.display.staff;

import boundary.display.Choice;
import boundary.display.NavScreen;
import control.MainApp;
import control.movies.MovieManager;

public class MovieEditingScreen extends NavScreen {

	public MovieEditingScreen(NavScreen prevScreen, int selectedMovieIndex) {
		super(prevScreen);
		MainApp.getMovieManager().recordStaffMovieSelection(selectedMovieIndex);
	}

	@Override
	protected String[] getOptions() {
		return new String[] { "Title", "Type", "Synopsis", "Director", "Status", "Age Rating", "Add Cast",
				"Delete Cast", "Back" };
	}

	@Override
	protected String getTitle() {
		return "Editing Options";
	}

	@Override
	protected void preDisplay() {
		System.out.println("Description Preview");
		MainApp.getMovieManager().displayMovieInfo(true);
	}

	@Override
	protected void handleChoice(Choice choice) {
		MovieManager manager = MainApp.getMovieManager();
		switch (choice.selection) {
			case 1:
				manager.changeTitle();
				break;
			case 2:
				manager.changeType();
				break;
			case 3:
				manager.changeSynopsis();
				break;
			case 4:
				manager.changeDirector();
				break;
			case 5:
				manager.changeStatus();
				break;
			case 6:
				manager.changeAgeRating();
				break;
			case 7:
				manager.addCast();
				break;
			case 8:
				manager.deleteCast();
				break;
			case 9:
				goBack();
				break;
			default:
				break;
		}
		if (choice.selection < 9) {
			manager.save();
			run();
		}
	}

}
