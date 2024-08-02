package entities.rules;

import enums.Seat;

public class AlternateSeatsRule implements IBookingRule {

	@Override
	public RuleResult checkRule(Seat[][] seatLayout, boolean[][] occupied, boolean[][] currentlyBooked, int selectedRow,
			int selectedColumn) {

		// Check if the 2nd seat to the left/right of the one selected have been
		// temporarily booked.
		// If either has been and there is no aisle in between, this rule is violated.
		if ((selectedColumn >= 2 && currentlyBooked[selectedRow][selectedColumn - 2]
				&& seatLayout[selectedRow][selectedColumn - 1] != Seat.AISLE
				&& (!occupied[selectedRow][selectedColumn - 1] || !currentlyBooked[selectedRow][selectedColumn - 1])) ||
				(selectedColumn + 2 < currentlyBooked[0].length && currentlyBooked[selectedRow][selectedColumn + 2]
						&& seatLayout[selectedRow][selectedColumn + 1] != Seat.AISLE
						&& (!occupied[selectedRow][selectedColumn + 1]
								|| !currentlyBooked[selectedRow][selectedColumn + 1]))) {
			return new RuleResult(false, "Cannot leave a single unoccupied seat between selected seats!");
		}

		return new RuleResult(true, PASSED);
	}

}
