
package tests;

import java.io.File;
import java.io.IOException;

import model.database.DatabaseException;
import model.papers.PaperManager;

//TODO delete class when project is finished

/**
 * This is just a class to test stuff
 * 
 * 
 */
public class Tester {
    
    public static void main(final String[] args) throws DatabaseException, InterruptedException, IOException {
        PaperManager.submitPaper(153, 125, "Royal Findings", "A study into the Queen's jewels", new File("tests/paper.txt"));
    }
}
