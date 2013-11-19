package model.papers;

public enum PaperStatus {
    ACCEPTED(2),
    
    REJECTED(1),
    
    UNDECIDED(0);
    
    private int paperStatus;
    
    private PaperStatus(final int paperStatus) {
        this.paperStatus = paperStatus;
    }
    
    public int getStatus() {
        return paperStatus;
    }
}
