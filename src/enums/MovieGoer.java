package enums;

import java.util.Arrays;

public enum MovieGoer implements IPriceable {
	SENIOR_CITIZEN("Senior Citizen"), STUDENT("Student"), ADULT("Regular");

	private final String name;

	private MovieGoer(String name) {
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
