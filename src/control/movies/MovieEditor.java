package control.movies;

import java.util.List;
import java.util.ArrayList;

import boundary.display.SelectionScreen;
import control.MainApp;
import entities.IMovie;
import entities.Movie;
import enums.MovieAgeRating;
import enums.MovieStatus;
import enums.MovieType;

public class MovieEditor {

	private MovieManager manager;
	private List<Movie> movies;

	public MovieEditor(MovieManager manager, List<Movie> movies) {
		this.manager = manager;
		this.movies = movies;
	}

	public List<IMovie> getMovies() {
		return new ArrayList<>(movies);
	}

	public void addMovie() {
		manager.recordStaffMovieSelection(movies.size());
		movies.add(new Movie(manager.getSelectedIndex()));
		changeTitle();
		changeType();
		changeSynopsis();
		changeDirector();
		changeStatus();
		changeAgeRating();
		addCast();
		manager.save();
	}

	public void changeTitle() {
		movies.get(manager.getSelectedIndex()).setTitle(MainApp.getInputHelper().getStringInput("Title", false));
	}

	public void changeSynopsis() {
		movies.get(manager.getSelectedIndex()).setSynopsis(MainApp.getInputHelper().getStringInput("Synopsis", false));
	}

	public void changeDirector() {
		movies.get(manager.getSelectedIndex()).setDirector(MainApp.getInputHelper().getStringInput("Director", false));
	}

	public void changeStatus() {
		int selectionIndex = new SelectionScreen(MovieStatus.valueStrings(), "Select Status").getChoice().normal;
		movies.get(manager.getSelectedIndex()).setStatus(MovieStatus.values()[selectionIndex]);
	}

	public void changeAgeRating() {
		int selectionIndex = new SelectionScreen(MovieAgeRating.valueStrings(), "Select Age Rating").getChoice().normal;
		movies.get(manager.getSelectedIndex()).setAgeRating(MovieAgeRating.values()[selectionIndex]);
	}

	public void changeType() {
		int selectionIndex = new SelectionScreen(MovieType.valueStrings(), "Select Movie Type").getChoice().normal;
		movies.get(manager.getSelectedIndex()).setType(MovieType.values()[selectionIndex]);
	}

	public void addCast() {
		List<String> cast = movies.get(manager.getSelectedIndex()).getCast();
		boolean addedOnce = false;
		while (!addedOnce || cast.size() < 2 || MainApp.getInputHelper().getConfirmation("Add More?")) {
			cast.add(MainApp.getInputHelper().getStringInput("Cast Member Name", false));
			addedOnce = true;
		}
		movies.get(manager.getSelectedIndex()).setCast(cast);
	}

	public void deleteCast() {
		movies.get(manager.getSelectedIndex()).setCast(new ArrayList<>());
		System.out.println("Add Cast Members");
		addCast();
	}
}
