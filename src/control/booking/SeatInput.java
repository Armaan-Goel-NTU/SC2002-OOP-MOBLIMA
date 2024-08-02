package control.booking;

import control.MainApp;
import entities.Showtime;

public class SeatInput {
	private String selectedSeat;
	private int selectedRow;
	private int selectedColumn;

	private int rowNameToRowIndex(String rowName) {
		int rowNum = 0;
		for (int i = 0; i < rowName.length(); i++) {
			rowNum = rowNum * 26 + (rowName.charAt(i) - ('A' - 1));
		}
		return rowNum - 1;
	}

	public void ask(Showtime showtime, int ticketNum) {
		selectedSeat = MainApp.getInputHelper().getStringInput("Ticket " + ticketNum, false);

		int rows = showtime.getHall().getNumRows();
		int columns = showtime.getHall().getNumColumns();

		int numRowChars = (int) Math.ceil(Math.log(rows) / Math.log(26));
		int numColumnChars = (int) Math.ceil(Math.log10(columns));

		String validRegex = String.format("^[a-zA-Z]{%d}[0-9]{%d}$", numRowChars, numColumnChars);

		try {
			if (selectedSeat.matches(validRegex)) {
				selectedRow = rowNameToRowIndex(selectedSeat.substring(0, numRowChars));
				if (selectedRow >= 0 && selectedRow < rows) {
					selectedColumn = Integer.parseInt(selectedSeat.substring(numRowChars)) - 1;
					if (selectedColumn >= 0
							&& selectedColumn < (columns - showtime.getHall().getEmptySeatsInRow(selectedRow))) {
						selectedColumn = showtime.getHall().nthNonEmptySeat(selectedRow, selectedColumn);
					} else {
						throw new IllegalArgumentException("Invalid Column Number!");
					}
				} else {
					throw new IllegalArgumentException("Invalid Row!");
				}
			} else {
				throw new IllegalArgumentException("Invalid Seat Format!");
			}
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			ask(showtime, ticketNum);
		}
	}

	public String getSelectedSeat() {
		return selectedSeat;
	}

	public int getSelectedRow() {
		return selectedRow;
	}

	public int getSelectedColumn() {
		return selectedColumn;
	}

}