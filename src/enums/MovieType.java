package enums;

import java.util.Arrays;

public enum MovieType implements IPriceable {
	NORMAL("Non-Blockbuster"), BLOCKBUSTER("Blockbuster");

	private final String name;

	private MovieType(String name) {
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
