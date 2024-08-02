package control;

import java.util.Arrays;

import entities.PriceData;
import enums.IPriceable;
import enums.MovieFormat;
import enums.MovieGoer;
import enums.MovieType;
import enums.Seat;
import enums.SessionTime;

public class PriceManager extends SavedManager {
	private PriceData priceData;

	public PriceManager(PriceData priceData) {
		this.priceData = priceData;
	}

	// Price accessing methods
	public double getBasePrice() {
		return priceData.getBasePrice();
	}

	public double getAddend(IPriceable priceable) {
		return priceData.getAddend(priceable);
	}

	// Price setting methods
	private double askPrice(String inputLabel, double currentPrice) {
		String input = MainApp.getInputHelper().getStringInput(String.format("%s (%.2f)", inputLabel, currentPrice),
				true);
		if (input.isBlank()) {
			return currentPrice;
		} else {
			try {
				return Double.parseDouble(input);
			} catch (NumberFormatException e) {
				System.out.println("Please enter a valid number!");
				return askPrice(inputLabel, currentPrice);
			}
		}
	}

	public void setBasePrice() {
		priceData.setBasePrice(askPrice("Base Price", priceData.getBasePrice()));
		save();
	}

	private void setAddends(IPriceable[] priceables, String[] names) {
		for (int i = 0; i < priceables.length; i++) {
			priceData.setAddend(priceables[i], askPrice(names[i], priceData.getAddend(priceables[i])));
		}
		save();
	}

	public void setMovieGoerAddends() {
		setAddends(MovieGoer.values(), MovieGoer.valueStrings());
	}

	public void setMovieFormatAddends() {
		setAddends(MovieFormat.values(), MovieFormat.valueStrings());
	}

	public void setMovieTypeAddends() {
		setAddends(MovieType.values(), MovieType.valueStrings());
	}

	public void setSessionTypeAddends() {
		setAddends(SessionTime.values(), SessionTime.valueStrings());
	}

	public void setSeatTypeAddends() {
		Seat[] withoutAisle = Arrays.stream(Seat.values()).filter(x -> x != Seat.AISLE).toArray(Seat[]::new);
		setAddends(withoutAisle, Arrays.stream(withoutAisle).map(Object::toString).toArray(String[]::new));
	}

	@Override
	public String getSaveFilePath() {
		return MainApp.PRICES_FILE;
	}

	@Override
	public Object getObjectToSave() {
		return priceData;
	}

}
