package enums;

import java.util.Arrays;

public enum Seat implements IPriceable {
	REGULAR("Standard"), PREMIUM("Premium"), AISLE("Aisle");

	private final String value;

	private Seat(String name) {
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
