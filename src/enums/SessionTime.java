package enums;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.util.Arrays;

import control.MainApp;

public enum SessionTime implements IPriceable {
	WEEKDAY("Weekday (Mon-Thurs)"), FRIDAY("Friday (Before 6pm)"), WEEKEND("Weekend (Friday After 6pm through Sunday)"),
	PUBLIC_HOLIDAY("Public Holiday (Includes Eve of PH)");

	private final String value;

	private SessionTime(String name) {
		this.value = name;
	}

	@Override
	public String toString() {
		return value;
	}

	public static String[] valueStrings() {
		return Arrays.stream(values()).map(Object::toString).toArray(String[]::new);
	}

	public static SessionTime fromDateTime(ZonedDateTime dateTime) {
		if (MainApp.getHolidayManager().hasHoliday(dateTime.toLocalDate()) ||
				dateTime.getHour() >= 18
						&& MainApp.getHolidayManager().hasHoliday(dateTime.toLocalDate().plusDays(1))) {
			return SessionTime.PUBLIC_HOLIDAY;
		} else {
			DayOfWeek day = dateTime.getDayOfWeek();
			boolean isBefore6PM = dateTime.getHour() < 18;
			if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY || (day == DayOfWeek.FRIDAY && !isBefore6PM)) {
				return SessionTime.WEEKEND;
			} else if (day == DayOfWeek.FRIDAY && isBefore6PM) {
				return SessionTime.FRIDAY;
			} else {
				return SessionTime.WEEKDAY;
			}
		}
	}
}
