
package tests;

import java.io.IOException;

import model.database.DatabaseException;

//TODO delete class when project is finished

/**
 * This is just a class to test stuff
 *
 *
 */
public class Tester {
    
    public static void main(final String[] args) throws DatabaseException,
            InterruptedException, IOException {
        //System.out.println(Login.registerUser("Barack", "Obama", "BObama@email.us"));
        //System.out.println(User.userFromEmail("bobama@email.us").getID()); // 138
        
        //System.out.println(Login.registerUser("Mohammad", "Juma", "MJuma@email.us")); // 139
        //System.out.println(Login.registerUser("Tim", "Mikeladze", "TMikeladze@email.us")); // 140
        //System.out.println(Login.registerUser("Trina", "Castillo", "TCastillo@email.us")); // 141
        //System.out.println(Login.registerUser("Srdjan", "Stojic", "SStojic@email.us")); // 142
        
        //System.out.println(ConferenceManager.createConference("Speeches", "Washington D.C.", new Timestamp(1390611600), 138)); // 158
        //System.out.println(ConferenceManager.createConference("State of the Union", "Washington D.C.",  new Timestamp(1390093200), 138)); // 159
        //System.out.println(ConferenceManager.createConference("Seattle Visit", "Seattle, WA", new Timestamp(1396314000), 138)); // 160
        
        /*
        ConferenceManager.addUserToConference(158, 139, PermissionLevel.REVIEWER);
        PaperManager.submitPaper(158, 139, "Inauguration Speech", "Just won speech", new File(
                "tests/paper.txt"));
        ConferenceManager.addUserToConference(158, 140, PermissionLevel.REVIEWER);
        PaperManager.submitPaper(158, 140, "New Years Speech", "Happy New Year", new File(
                "tests/paper.txt"));
        ConferenceManager.addUserToConference(158, 141, PermissionLevel.REVIEWER);
        PaperManager.submitPaper(158, 141, "Back to School Speech", "Schooool!", new File(
                "tests/paper.txt"));
        ConferenceManager.addUserToConference(158, 142, PermissionLevel.REVIEWER);
        PaperManager.submitPaper(158, 142, "Game of Thrones Speech", "Jon Snow", new File(
                "tests/paper.txt"));
        
        ConferenceManager.addUserToConference(159, 139, PermissionLevel.REVIEWER);
        PaperManager.submitPaper(159, 139, "State of the Union 2014", "2014 Address", new File(
                "tests/paper.txt"));
        ConferenceManager.addUserToConference(159, 140, PermissionLevel.REVIEWER);
        PaperManager.submitPaper(159, 140, "State of the Union 2015", "2015 Address", new File(
                "tests/paper.txt"));
        ConferenceManager.addUserToConference(159, 141, PermissionLevel.REVIEWER);
        PaperManager.submitPaper(159, 141, "State of the Union 2016", "2016 Address", new File(
                "tests/paper.txt"));
        ConferenceManager.addUserToConference(159, 142, PermissionLevel.REVIEWER);
        PaperManager.submitPaper(159, 142, "State of the Union 2017", "2017 Address", new File(
                "tests/paper.txt"));
        PaperManager.submitPaper(160, 138, "UDub Address", "Go Huskies", new File(
                "tests/paper.txt"));
        */
        
        /*
        List<Paper> a = PaperManager.getPapers(158);
        for (Paper p : a) {
            System.out.println(p.getTitle() + " : " + p.getPaperID());
        }
        PaperManager.removePaper(115);
        PaperManager.removePaper(116);
        */
        
        /*
        System.out.println(Login.registerUser("Ban", "Ki-moon", "BKimoon@email.un")); // 143
        System.out.println(Login.registerUser("Angela", "Merkel", "AMerkel@email.de")); // 144
        System.out.println(ConferenceManager.createConference("UN Topics", "New York, NY", new Timestamp(1390611600), 143)); // 161
        ConferenceManager.addUserToConference(161, 139, PermissionLevel.REVIEWER);
        PaperManager.submitPaper(161, 138, "World Domination", "Murica'!", new File(
                "tests/paper.txt"));
        ConferenceManager.addUserToConference(161, 143, PermissionLevel.REVIEWER);
        PaperManager.submitPaper(161, 143, "Oktober Fest", "Mmm Beer!", new File(
                "tests/paper.txt"));
        */
    }
}
