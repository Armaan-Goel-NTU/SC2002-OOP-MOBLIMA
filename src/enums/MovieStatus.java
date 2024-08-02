package enums;

import java.util.Arrays;

public enum MovieStatus {
	COMING_SOON("Coming Soon"), PREVIEW("Preview"), NOW_SHOWING("Now Showing"), END_OF_SHOWING("End of Showing");

	private final String name;

	private MovieStatus(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	public static String[] valueStrings() {
		return Arrays.stream(values()).map(Object::toString).toArray(String[]::new);
	}

}
