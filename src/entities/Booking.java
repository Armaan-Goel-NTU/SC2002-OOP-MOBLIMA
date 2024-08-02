package entities;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;

import control.LoginManager;
import control.MainApp;

public class Booking implements Serializable {
	private static final long serialVersionUID = 1L;
	private final String tid;
	private final String name;
	private final String email;
	private final String mobileNumber;
	private final String showtimeInfo;

	public Booking(String hallCode, String showtimeInfo) {
		LoginManager loginManager = MainApp.getLoginManager();
		this.name = loginManager.getDisplayName();
		this.email = loginManager.getEmail();
		this.mobileNumber = loginManager.getMobileNumber();
		this.showtimeInfo = showtimeInfo;
		this.tid = hallCode + MainApp.getDateHelper().now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPhoneNumber() {
		return mobileNumber;
	}

	public String getTid() {
		return tid;
	}

	public String toString() {
		return String.format("%s%n%s", tid, showtimeInfo);
	}

}
