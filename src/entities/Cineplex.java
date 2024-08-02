package entities;

import java.io.Serializable;

public class Cineplex implements Serializable {
	private static final long serialVersionUID = 1L;
	private final String location;
	private final Cinema[] cinemaHalls;

	public Cineplex(String location, Cinema[] cinemaHalls) {
		this.location = location;
		this.cinemaHalls = cinemaHalls;
	}

	public String getLocation() {
		return location;
	}

	public Cinema[] getCinemaHalls() {
		return cinemaHalls;
	}
}
