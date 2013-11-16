package model.database;

import java.sql.SQLException;

public class DatabaseException extends SQLException {
	private static final long serialVersionUID = -6252031987727266735L;

	public DatabaseException(Errors error) {
		super(error.getError());
	}

}
