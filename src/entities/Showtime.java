package entities;

import java.io.Serializable;
import java.time.ZonedDateTime;

import control.MainApp;
import enums.MovieFormat;
import enums.Seat;

public class Showtime implements Serializable {
	private static final long serialVersionUID = 1L;
	private int movieId;
	private MovieFormat format;
	private Cinema hall;
	private String location;
	private ZonedDateTime showDateTime;
	private boolean[][] occupied;

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public MovieFormat getMovieFormat() {
		return format;
	}

	public void setMovieFormat(MovieFormat format) {
		this.format = format;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Cinema getHall() {
		return hall;
	}

	public void setHall(Cinema hall) {
		this.hall = hall;
		occupied = new boolean[hall.getNumRows()][hall.getNumColumns()];
	}

	public ZonedDateTime getShowDateTime() {
		return showDateTime;
	}

	public void setShowDateTime(ZonedDateTime showDateTime) {
		this.showDateTime = showDateTime;
	}

	public Seat getSeat(int row, int column) {
		return hall.getSeatLayout()[row][column];
	}

	public Seat getRowType(int row) {
		for (int i = 0; i < occupied[0].length; i++) {
			Seat seat = getSeat(row, i);
			if (seat != Seat.AISLE) {
				return seat;
			}
		}
		return Seat.REGULAR;
	}

	public boolean[][] getOccupancy() {
		return occupied;
	}

	public boolean isOccupied(int row, int column) {
		return occupied[row][column];
	}

	public int getNumOccupied() {
		int numOccupied = 0;
		for (int i = 0; i < occupied.length; i++) {
			for (int j = 0; j < occupied[i].length; j++) {
				if (isOccupied(i, j)) {
					numOccupied++;
				}
			}
		}
		return numOccupied;
	}

	public void setOccupancy(boolean[][] occupied) {
		this.occupied = occupied;
	}

	public IMovie getMovie() {
		return MainApp.getMovieManager().movieFromId(movieId);
	}

}
