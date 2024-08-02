package entities.rules;

public class RuleResult {
	private final boolean passed;
	private final String reason;

	public RuleResult(boolean passed, String reason) {
		this.passed = passed;
		this.reason = reason;
	}

	public boolean getPassed() {
		return passed;
	}

	public String getReason() {
		return reason;
	}
}
