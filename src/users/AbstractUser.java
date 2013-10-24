package users;

public abstract class AbstractUser {
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private int id;

	public String getUsername() {
		return username;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public int getId() {
		return id;
	}

}
