package entities.login;

import control.MainApp;
import enums.AccountType;

public class MovieGoerLogin extends Login {

	public MovieGoerLogin(String email) {
		super(email);
	}

	@Override
	public AccountType getAccountType() {
		return AccountType.MOVIE_GOER;
	}

	@Override
	public boolean tryLogIn() {
		String mobileNumber = MainApp.getInputHelper().getStringInput("Mobile Number", false);
		if (mobileNumber.matches("\\d+") && mobileNumber.length() == 8) {
			String preferredName = MainApp.getInputHelper().getStringInput("Preferred Name", false);
			setMobileNumber(mobileNumber);
			setPreferredName(preferredName);
			MainApp.getMovieManager().storeAllGuestReviews(getEmail());
			return true;
		}
		System.out.println("Mobile number must contain exactly 8 digits!");
		return tryLogIn();
	}

}
