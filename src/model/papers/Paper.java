package model.papers;

import java.sql.Timestamp;

public class Paper {
    private int paperID;
    
    private int conferenceID;
    
    private String title;
    
    private String description;
    
    private Timestamp submissionDate;
    
    private int authorID;
    
    private boolean status;
    
    private boolean revised;
    
    private String fileExtension;
    
    private String file;
    
    private Timestamp revisionDate;

    public int getPaperID() {
        return paperID;
    }

    public int getConferenceID() {
        return conferenceID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getSubmissionDate() {
        return submissionDate;
    }

    public int getAuthorID() {
        return authorID;
    }

    public boolean getStatus() {
        return status;
    }

    public boolean getRevised() {
        return revised;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public String getFile() {
        return file;
    }

    public Timestamp getRevisionDate() {
        return revisionDate;
    }

    @Override
    public String toString() {
        return "Paper [getPaperID()=" + getPaperID() + ", getConferenceID()="
                + getConferenceID() + ", getTitle()=" + getTitle()
                + ", getDescription()=" + getDescription()
                + ", getSubmissionDate()=" + getSubmissionDate()
                + ", getAuthorID()=" + getAuthorID() + ", getStatus()="
                + getStatus() + ", getRevised()=" + getRevised()
                + ", getFileExtension()=" + getFileExtension() + ", getFile()="
                + getFile() + ", getRevisionDate()=" + getRevisionDate() + "]";
    }    
}
