package entities;

import java.io.Serializable;
import java.util.HashMap;

import enums.IPriceable;

public class PriceData implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final double DEFAULT_ADDEND = 0;
	private double basePrice;
	private HashMap<IPriceable, Double> addendMap;

	public PriceData() {
		this.addendMap = new HashMap<>();
	}

	public double getBasePrice() {
		return this.basePrice;
	}

	public void setBasePrice(double basePrice) {
		this.basePrice = basePrice;
	}

	public double getAddend(IPriceable priceable) {
		if (addendMap.containsKey(priceable)) {
			return addendMap.get(priceable);
		}
		return DEFAULT_ADDEND;
	}

	public void setAddend(IPriceable priceable, double amount) {
		addendMap.put(priceable, amount);
	}
}
