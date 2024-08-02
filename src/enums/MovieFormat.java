package enums;

import java.util.Arrays;

public enum MovieFormat implements IPriceable {
	REG_2D("Regular 2D"), REG_3D("Regular 3D");

	private final String value;

	private MovieFormat(String name) {
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
