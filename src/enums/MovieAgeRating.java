package enums;

import java.util.Arrays;

public enum MovieAgeRating {
	G("G"), PG("PG"), PG13("PG13"), NC16("NC16"), M18("M18"), R21("R21");

	private final String value;

	private MovieAgeRating(String name) {
		this.value = name;
	}

	@Override
	public String toString() {
		return value;
	}

	public static String[] valueStrings() {
		return Arrays.stream(values()).map(Object::toString).toArray(String[]::new);
	}
}
