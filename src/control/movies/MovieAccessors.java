package control.movies;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

import entities.IMovie;
import enums.MovieStatus;

public class MovieAccessors {

	private MovieManager manager;

	public MovieAccessors(MovieManager manager) {
		this.manager = manager;
	}

	private ArrayList<IMovie> getMovies(MovieStatus[] statusList) {
		return manager.getAllMovies().stream().filter(x -> Arrays.stream(statusList).anyMatch(x.getStatus()::equals))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	private String[] getTitles(List<? extends IMovie> movieList) {
		return movieList.stream().map(IMovie::getTitle).toArray(String[]::new);
	}

	public String[] getAllMovieTitles() {
		return getTitles(manager.getAllMovies());
	}

	public List<IMovie> getCurrentMovies() {
		return getMovies(new MovieStatus[] { MovieStatus.COMING_SOON, MovieStatus.PREVIEW, MovieStatus.NOW_SHOWING });
	}

	public String[] getCurrentMovieTitles() {
		return getTitles(getCurrentMovies());
	}

	public List<IMovie> getCurrentlyPurchasable() {
		return getMovies(new MovieStatus[] { MovieStatus.PREVIEW, MovieStatus.NOW_SHOWING });
	}

	public int movieIdFromCurrentIndex(int currentIndex) {
		return getCurrentMovies().get(currentIndex).getId();
	}

	public int currentMovieIndexFromId(int id) {
		for (int i = 0; i < getCurrentMovies().size(); i++) {
			if (getCurrentMovies().get(i).getId() == id) {
				return i;
			}
		}
		return -1;
	}

	public IMovie movieFromId(int id) {
		return manager.getAllMovies().stream().filter(x -> x.getId() == id).findFirst().orElse(null);
	}
}
