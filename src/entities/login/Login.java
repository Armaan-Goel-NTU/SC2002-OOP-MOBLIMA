package entities.login;

import enums.AccountType;

public abstract class Login {
	private String email;
	private String displayName;
	private String mobileNumber;

	protected Login(String email) {
		this.email = email;
	}

	protected void setEmail(String email) {
		this.email = email;
	}

	protected void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	protected void setPreferredName(String displayName) {
		this.displayName = displayName;
	}

	public String getEmail() {
		return email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public String getPreferredName() {
		return displayName;
	}

	public abstract AccountType getAccountType();

	public abstract boolean tryLogIn();
}
