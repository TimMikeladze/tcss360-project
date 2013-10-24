package database;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.skife.jdbi.v2.DBI;

/**
 * This is a singleton class which provides static access to an instance of the
 * database connection.
 * 
 * 
 */
public class Database {

	private static final String HOST = "71.19.151.5";
	private static final int PORT = 3306;
	private static final String DATABASE = "conferences";

	// Fill out this data before testing
	private static final String USERNAME = "";
	private static final String PASSWORD = "";

	private static Database database;
	private static DBI dbi;

	private static boolean isConnected = false;

	private Database() {
		connect();
	}

	private void connect() {
		try {
			if (USERNAME.isEmpty() || PASSWORD.isEmpty()) {
				System.err
						.println("You forgot to set the database username and password;");
				System.exit(0);
			}
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			dbi = new DBI("jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE,
					USERNAME, PASSWORD);
			// TODO directly check connection status status rather than using
			// boolean
			isConnected = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Checks if connected to database
	 * 
	 * @return returns whether the database connection has been established
	 */
	public static synchronized boolean isConnected() {
		return isConnected;
	}

	public static synchronized DBI getInstance() {
		if (database == null) {
			database = new Database();
		}
		// Rather than returning an instance of this class like in typical
		// singleton design we instead return a reference to the DBI object
		return dbi;
	}
}
