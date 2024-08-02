package entities.rules;

import enums.Seat;

public interface IBookingRule {
	public static final String PASSED = "Passed";

	public RuleResult checkRule(Seat[][] seatLayout, boolean[][] occupied, boolean[][] currentlyBooked, int selectedRow,
			int selectedColumn);
}
