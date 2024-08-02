package control;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateHelper {
	public static final String TIME_FORMAT = "HH:mm";
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
	public static final String ABBREVIATED_DATE_FORMAT = "yyyy-MMM-dd";

	public DateTimeFormatter getTimeFormat() {
		return DateTimeFormatter.ofPattern(TIME_FORMAT);
	}

	public DateTimeFormatter getDateTimeFormat() {
		return DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
	}

	public DateTimeFormatter getAbbreviatedDateFormat() {
		return DateTimeFormatter.ofPattern(ABBREVIATED_DATE_FORMAT);
	}

	public ZoneId localTimeZone() {
		return ZoneId.of("Asia/Singapore");
	}

	public ZonedDateTime now() {
		return ZonedDateTime.now(localTimeZone());
	}
}
