package model.database;

public enum Errors {
	EMAIL_EXISTS("This email already exists in the database"),
	EMAIL_DOES_NOT_EXIST("This email does not exist"),
	CONFERENCE_DOES_NOT_EXIST("This conference does not exist"),
	MAX_PAPER_SUBMISSIONS_EXCEEDED("You've submitted the maximum amount of papers allowed into this conference");

	private String error;

	Errors(String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}

	public String toString() {
		return error;
	}

}
