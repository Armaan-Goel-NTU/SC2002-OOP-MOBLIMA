package entities.rules;

import enums.Seat;

public class OccupiedRule implements IBookingRule {

	@Override
	public RuleResult checkRule(Seat[][] seatLayout, boolean[][] occupied, boolean[][] currentlyBooked, int selectedRow,
			int selectedColumn) {
		if (occupied[selectedRow][selectedColumn]) {
			return new RuleResult(false, "Seat Already Occupied!");
		}
		return new RuleResult(true, PASSED);
	}

}
