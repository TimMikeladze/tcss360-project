
package model.database;

import java.sql.SQLException;
import java.util.List;

import org.sql2o.Sql2o;
import org.sql2o.data.Table;

/**
 * TODO Tim: Can you suppress all logging please? This is a singleton class which provides
 * static access to an instance of the database connection.
 * 
 * @author Tim Mikeladze
 * @version 11-18-2013
 */
public class Database {
    
    private static final String DATABASE = "conferences";
    
    private static final String PORT = "3306";
    
    private static final String USERNAME = "tcss360";
    
    private static final String HOST = "71.19.151.5";
    
    private static final String PASSWORD = "123456";
    
    /**
     * The database.
     */
    private static Database database;
    
    /**
     * The type of database.
     */
    private static Sql2o sql;
    
    /**
     * Checks if connected to the database.
     * 
     * <dt><b>Precondition:</b>
     * <dd>none
     * <dt><b>Postcondition:</b>
     * <dd>ensures The status of the connection to the database is returned.
     * 
     * @return returns whether the database connection has been established
     */
    public static synchronized boolean isConnected() {
        boolean isConnected = false;
        try {
            getInstance().getDataSource()
                         .getConnection();
            isConnected = true;
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
        return isConnected;
    }
    
    /**
     * Gets the single instance of the database.
     * 
     * <dt><b>Precondition:</b>
     * <dd>none
     * <dt><b>Postcondition:</b>
     * <dd>ensures The instance of the database.
     * 
     * @return the single instance of the database
     */
    public static synchronized Sql2o getInstance() {
        if (database == null) {
            database = new Database();
            return Database.sql;
        }
        /* Rather than returning an instance of this class like in typical
           singleton design we instead return a reference to the Sql2o object */
        return sql;
    }
    
    /**
     * Wrapper method to see if there are results.
     * 
     * 
     * <dt><b>Precondition:</b>
     * <dd>requires (TODO Tim: What is this a list of?)
     * <dt><b>Postcondition:</b>
     * <dd>ensures True if there are results, otherwise false.
     * 
     * @param list the list to check
     * @return true if there are results
     */
    /* TODO Tim: This is a dirty way of determining results. Ideally a
        ConcurrentHashMap is needed keyed by thread id and the corresponding
        value being a Stack of query results which is flushed every so often to
        keep memory free. Having this we'll be able to store and access previous
        query results in a thread safe manner. */
    public static synchronized boolean hasResults(final List<?> list) {
        return list != null && !list.isEmpty();
    }
    
    /**
     * Checks a table for results.
     * 
     * <dt><b>Precondition:</b>
     * <dd>requires (TODO Tim: Table of what? The database table?)
     * <dt><b>Postcondition:</b>
     * <dd>ensures True if there are results, otherwise false.
     * 
     * @param table the table to check
     * @return true if there are results
     */
    public static synchronized boolean hasResults(final Table table) {
        return table != null && hasResults(table.rows());
    }
    
    /**
     * Starts the connection to the database.
     * 
     * <dt><b>Precondition:</b>
     * <dd>none
     * <dt><b>Postcondition:</b>
     * <dd>ensures A Connection to the database is made.
     */
    private Database() {
        connect();
    }
    
    /**
     * Connects to database using information from properties file.
     * 
     * <dt><b>Precondition:</b>
     * <dd>none
     * <dt><b>Postcondition:</b>
     * <dd>ensures A connection to the database is made using the properties file.
     */
    private void connect() {
        sql = new Sql2o("jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + "?zeroDateTimeBehavior=convertToNull", USERNAME, PASSWORD);
    }
}
