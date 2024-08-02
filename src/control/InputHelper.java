package control;

import java.util.InputMismatchException;
import java.util.Scanner;

public final class InputHelper {

	private final Scanner scanner;

	public InputHelper(Scanner scanner) {
		this.scanner = scanner;
	}

	public boolean getConfirmation(String questionLabel) {
		System.out.print(questionLabel + " [y/n]: ");
		String choice = scanner.nextLine();
		if (choice.equalsIgnoreCase("y")) {
			return true;
		} else if (choice.equalsIgnoreCase("n")) {
			return false;
		}
		return getConfirmation(questionLabel);
	}

	public String getStringInput(String inputLabel, boolean canBeEmpty) {
		System.out.print(inputLabel + ": ");
		String input = scanner.nextLine();
		try {
			if (!canBeEmpty && input.isBlank()) {
				throw new IllegalArgumentException("Input cannot be blank!");
			}
			return input;
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return getStringInput(inputLabel, canBeEmpty);
		}
	}

	public double getDoubleInput(String inputLabel, double min, double max) {
		System.out.print(inputLabel + ": ");
		try {
			double choice = scanner.nextDouble();
			scanner.nextLine();
			if (choice < min || choice > max) {
				throw new IllegalArgumentException(String.format("Must be in range of %f - %f!", min, max));
			}
			return choice;
		} catch (InputMismatchException e) {
			System.out.println("Please enter a number!");
			scanner.nextLine();
			return getDoubleInput(inputLabel, min, max);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return getDoubleInput(inputLabel, min, max);
		}
	}

	public int getIntInput(String inputLabel, int min, int max) {
		System.out.print(inputLabel + ": ");
		try {
			int choice = scanner.nextInt();
			scanner.nextLine(); // need to consume the newline character since nextInt does not
			if (choice < min || choice > max) {
				throw new IllegalArgumentException(String.format("Integer must be in range of %d - %d!", min, max));
			}
			return choice;
		} catch (InputMismatchException e) {
			System.out.println("Please enter an integer!");
			scanner.nextLine();
			return getIntInput(inputLabel, min, max);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return getIntInput(inputLabel, min, max);
		}
	}
}
