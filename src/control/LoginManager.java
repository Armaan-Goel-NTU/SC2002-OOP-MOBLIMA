package control;

import java.util.Map;

import entities.login.Login;
import entities.login.MovieGoerLogin;
import entities.login.StaffLogin;
import enums.AccountType;

public class LoginManager {
	// simple email regex taken from
	// https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input/email#validation
	private static final String EMAIL_REGEX = "^[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";

	private final Map<String, String> staffAccounts;
	private boolean loggedIn;
	private Login currentLogin;

	public LoginManager(Map<String, String> staffAccounts) {
		this.staffAccounts = staffAccounts;
		currentLogin = null;
		loggedIn = false;
	}

	// State/Status methods
	public boolean isLoggedIn() {
		return loggedIn;
	}

	public AccountType getAccountType() {
		return currentLogin.getAccountType();
	}

	// User info methods
	public String getEmail() {
		return currentLogin.getEmail();
	}

	public String getMobileNumber() {
		return currentLogin.getMobileNumber();
	}

	public String getDisplayName() {
		return currentLogin.getPreferredName();
	}

	public void logOut() {
		if (loggedIn) {
			currentLogin = null;
			loggedIn = false;
		}
	}

	// Login methods
	private void attemptLogin(Login newLogin) {
		if (newLogin.tryLogIn()) {
			currentLogin = newLogin;
			loggedIn = true;
		}
	}

	public void tryLoggingIn(boolean forceUser) {
		String email = MainApp.getInputHelper().getStringInput("Email", false);
		if (email.matches(EMAIL_REGEX)) {
			if (!forceUser && staffAccounts.containsKey(email)) {
				attemptLogin(new StaffLogin(email, staffAccounts));

			} else {
				attemptLogin(new MovieGoerLogin(email));
			}
		} else {
			System.out.println("Invalid email address!");
		}
	}
}
