package entities.login;

import java.util.Map;

import control.MainApp;
import enums.AccountType;

public class StaffLogin extends Login {

	private static final String DEAFULT_NUMBER = "11111111";
	private final Map<String, String> staffAccounts;

	public StaffLogin(String email, Map<String, String> staffAccounts) {
		super(email);
		this.staffAccounts = staffAccounts;
	}

	@Override
	public AccountType getAccountType() {
		return AccountType.STAFF;
	}

	@Override
	public boolean tryLogIn() {
		String email = getEmail();
		String password = MainApp.getInputHelper().getStringInput("Password", false);
		if (password.equals(staffAccounts.get(email))) {
			setPreferredName(getEmail().split("@")[0]);
			setMobileNumber(DEAFULT_NUMBER);
			return true;
		}
		System.out.println("Invalid Password!");
		return false;
	}

}
