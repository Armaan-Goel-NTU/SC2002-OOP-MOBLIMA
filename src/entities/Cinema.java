package entities;

import java.io.Serializable;

import enums.MovieFormat;
import enums.Seat;

public class Cinema implements Serializable {
	private static final long serialVersionUID = 1L;
	private final String hallName;
	private final String code;
	private final MovieFormat[] supportedFormats;
	private final Seat[][] seatLayout;

	public Cinema(String code, String hallName, Seat[][] seatLayout, MovieFormat[] supportedFormats) {
		this.code = code;
		this.hallName = hallName;
		this.seatLayout = seatLayout;
		this.supportedFormats = supportedFormats;
	}

	public String getCode() {
		return code;
	}

	public String getHallName() {
		return hallName;
	}

	public MovieFormat[] getSupportedFormats() {
		return supportedFormats;
	}

	public int getNumRows() {
		return seatLayout.length;
	}

	public int getNumColumns() {
		return seatLayout[0].length;
	}

	public Seat[][] getSeatLayout() {
		return seatLayout;
	}

	public Seat getSeat(int row, int column) {
		return seatLayout[row][column];
	}

	public int getCapacity() {
		int capacity = 0;
		for (int i = 0; i < seatLayout.length; i++) {
			for (int j = 0; j < seatLayout[i].length; j++) {
				if (seatLayout[i][j] != Seat.AISLE) {
					capacity++;
				}
			}
		}
		return capacity;
	}

	public int getEmptySeatsInRow(int row) {
		int numEmpty = 0;
		Seat[] rowSeats = seatLayout[row];
		for (int i = 0; i < rowSeats.length; i++) {
			if (rowSeats[i] == Seat.AISLE) {
				numEmpty++;
			}
		}
		return numEmpty;
	}

	public int nthNonEmptySeat(int row, int n) {
		int numNonEmpty = -1;
		Seat[] rowSeats = seatLayout[row];
		for (int j = 0; j < rowSeats.length; j++) {
			if (seatLayout[row][j] != Seat.AISLE)
				numNonEmpty += 1;
			if (numNonEmpty == n) {
				return j;
			}
		}
		return -1;
	}
}
