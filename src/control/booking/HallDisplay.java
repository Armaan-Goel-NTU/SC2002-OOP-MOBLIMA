package control.booking;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Map.Entry;

import control.MainApp;
import entities.Showtime;
import enums.MovieGoer;
import enums.Seat;
import enums.SessionTime;

public class HallDisplay {

	private BookingManager manager;

	public HallDisplay(BookingManager manager) {
		this.manager = manager;
	}

	private String rowIndexToRowName(int rowIndex) {
		StringBuilder rowBuilder = new StringBuilder();
		rowIndex++;
		do {
			rowIndex--;
			int remainder = rowIndex % 26;
			rowBuilder.insert(0, (char) ('A' + remainder));
			rowIndex /= 26;
		} while (rowIndex > 0);
		return rowBuilder.toString();
	}

	private void displayRowType(Seat seat) {
		System.out.println(
				"    ------------------------------------------------------%s------------------------------------------------------"
						.formatted(seat.toString()));
	}

	private void displayMovieGoerPricing() {
		System.out.print("Ticket Pricing: ");
		for (Entry<MovieGoer, Double> entry : manager.getShowtimePrices().entrySet()) {
			System.out.printf("%s -> %.2f; ", entry.getKey().toString(), entry.getValue());
		}
		System.out.println();
	}

	private void displaySeatPricing() {
		System.out.print("Seat Class Charges: ");
		for (Seat seat : Seat.values()) {
			if (seat != Seat.AISLE) {
				System.out.printf("%s -> +$%.2f; ", seat.toString(), MainApp.getPriceManager().getAddend(seat));
			}
		}
		System.out.println();
	}

	private void displayShowDescription(Showtime showtime) {
		System.out.printf("Show description: %s; %s; %s;%n", showtime.getMovie().getType().toString(),
				showtime.getMovieFormat().toString(), SessionTime.fromDateTime(showtime.getShowDateTime()).toString());
	}

	private String padZeroes(int number, int fixedLength) {
		DecimalFormat df = new DecimalFormat(String.join("", Collections.nCopies(fixedLength, "0")));
		return df.format(number);
	}

	public void displayHallAndPrices(Showtime showtime) {
		System.out.println(
				"\n______________________________________________________SCREEN THIS WAY______________________________________________________\n");
		Seat currentRowType = null;
		for (int i = 0; i < showtime.getHall().getNumRows(); i++) {
			if (showtime.getRowType(i) != currentRowType) {
				displayRowType(showtime.getRowType(i));
			}
			currentRowType = showtime.getRowType(i);
			int numAisles = 0;
			for (int j = 0; j < showtime.getHall().getNumColumns(); j++) {
				Seat curSeat = showtime.getSeat(i, j);
				if (showtime.isOccupied(i, j)) {
					System.out.print("_");
				} else if (curSeat == Seat.AISLE) {
					numAisles++;
				} else {
					int fixedLength = (int) Math.ceil(Math.log10(showtime.getHall().getNumColumns()));
					System.out.print(rowIndexToRowName(i) + padZeroes(j - numAisles + 1, fixedLength));
				}
				System.out.print("\t");
			}
			System.out.println("\n");
		}
		System.out.println(
				(showtime.getHall().getCapacity() - showtime.getNumOccupied()) + " seats available; _ means taken\n");
		displayShowDescription(showtime);
		displayMovieGoerPricing();
		displaySeatPricing();
	}

}
