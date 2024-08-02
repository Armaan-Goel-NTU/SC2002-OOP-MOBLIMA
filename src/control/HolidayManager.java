package control;

import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class HolidayManager extends SavedManager {
	private List<LocalDate> holidays;

	public HolidayManager(List<LocalDate> holidays) {
		this.holidays = removeOldDates(holidays);
		save();
	}

	private LocalDate today() {
		return LocalDate.now(MainApp.getDateHelper().localTimeZone());
	}

	private List<LocalDate> removeOldDates(List<LocalDate> dates) {
		LocalDate today = today();
		for (int i = dates.size() - 1; i >= 0; i--) {
			if (dates.get(i).isBefore(today)) {
				dates.remove(i);
			}
		}
		return dates;
	}

	private void insertHoliday(LocalDate date) {
		int index = -1;
		for (int i = 0; i < holidays.size(); i++) {
			LocalDate holiday = holidays.get(i);
			if (holiday.isAfter(date)) {
				index = i;
				break;
			}
		}
		if (index == -1) {
			holidays.add(date);
		} else {
			holidays.add(index, date);
		}
		save();
	}

	public boolean hasHoliday(LocalDate date) {
		for (LocalDate holiday : holidays) {
			if (date.isEqual(holiday))
				return true;
		}
		return false;
	}

	private LocalDate getValidDate() {
		String dateString = MainApp.getInputHelper().getStringInput("Date in yyyy-MM-dd", false);
		try {
			LocalDate holiday = LocalDate.parse(dateString);
			if (holiday.isBefore(today())) {
				throw new IllegalArgumentException("Holiday must be a future date!");
			}
			if (hasHoliday(holiday)) {
				throw new IllegalArgumentException("Holiday already exists!");
			}
			return holiday;
		} catch (DateTimeParseException e) {
			System.out.println("Please enter a valid date!");
			return getValidDate();
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return getValidDate();
		}
	}

	public void addHoliday() {
		LocalDate newHoliday = getValidDate();
		insertHoliday(newHoliday);
		System.out.printf("Added %s!%n", newHoliday.toString());
	}

	public void removeHoliday(int index) {
		if (MainApp.getInputHelper().getConfirmation("Confirm Removal")) {
			holidays.remove(index);
			save();
		}
	}

	public String[] getHolidaysStringList() {
		return holidays.stream().map(x -> x.toString()).toArray(String[]::new);
	}

	@Override
	public String getSaveFilePath() {
		return MainApp.HOLIDAYS_FILE;
	}

	@Override
	public Object getObjectToSave() {
		return holidays;
	}
}
