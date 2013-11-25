
package tests;

import java.sql.Timestamp;

import model.conferences.ConferenceManager;
import model.database.DatabaseException;

//TODO delete class when project is finished

/**
 * This is just a class to test stuff
 * 
 * 
 */
public class Tester {
    
    public static void main(final String[] args) throws DatabaseException, InterruptedException {
        System.out.println(ConferenceManager.createConference("Another test", "Somewhere", new Timestamp(1231231), 125));
    }
}
