package control;

import java.util.List;

import entities.Cineplex;

public class CineplexManager {
	private List<Cineplex> cineplexes;

	public CineplexManager(List<Cineplex> cineplexes) {
		this.cineplexes = cineplexes;
	}

	public String[] getLocations() {
		return cineplexes.stream().map(x -> x.getLocation()).toArray(String[]::new);
	}

	public Cineplex getCineplex(int index) {
		return cineplexes.get(index);
	}
}
