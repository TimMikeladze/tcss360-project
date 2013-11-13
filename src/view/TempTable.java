package view;
import java.util.ArrayList;
import java.util.Date;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class TempTable {
    
    private String conferenceName, programChair;
    private int authors, reviewers;
    private Date conferenceDate;
    
    public TempTable(String conferenceName, String programChair,
            int authors, int reviewers, Date conferenceDate) {
        super();
        this.conferenceName = conferenceName;
        this.programChair = programChair;
        this.authors = authors;
        this.reviewers = reviewers;
        this.conferenceDate = conferenceDate;
    }
    public String getConferenceName() {
        return conferenceName;
    }
    public void setConferenceName(String conferenceName) {
        this.conferenceName = conferenceName;
    }
    public String getProgramChair() {
        return programChair;
    }
    public void setProgramChair(String programChair) {
        this.programChair = programChair;
    }
    public int getAuthors() {
        return authors;
    }
    public void setAuthors(int authors) {
        this.authors = authors;
    }
    public int getReviewers() {
        return reviewers;
    }
    public void setReviewers(int reviewers) {
        this.reviewers = reviewers;
    }
    public Date getConferenceDate() {
        return conferenceDate;
    }
    public void setConferenceDate(Date conferenceDate) {
        this.conferenceDate = conferenceDate;
    }
    
    public static ArrayList<TableColumn<TempTable, ?>> getColumn(TableView table) {
        int i;
        
        ArrayList<TableColumn<TempTable, ?>> columns = new ArrayList<TableColumn<TempTable, ?>>();
        
        String[] columnsNames = { "Conference Names", "Program Chair", "Authors", "Reviewers", "Date" };
        String[] variableNames = { "conferenceName", "programChair", "authors", "reviwers", "conferenceDate" };
        Integer[] columnWidth = { 30, 25, 10, 10, 25 };
        
        i = 0;
        TableColumn<TempTable, String> conferenceNamesColumn = new TableColumn<TempTable, String>(columnsNames[i++]);
        TableColumn<TempTable, String> programChairColumn = new TableColumn<TempTable, String>(columnsNames[i++]);
        TableColumn<TempTable, Integer> authorsColumn = new TableColumn<TempTable, Integer>(columnsNames[i++]);
        TableColumn<TempTable, Integer> reviewersColumn = new TableColumn<TempTable, Integer>(columnsNames[i++]);
        TableColumn<TempTable, Date> dateColumn = new TableColumn<TempTable, Date>(columnsNames[i++]);
        
        i = 0;
        conferenceNamesColumn.prefWidthProperty().bind(table.widthProperty().divide(100 / columnWidth[i++]));
        programChairColumn.prefWidthProperty().bind(table.widthProperty().divide(100 / columnWidth[i++]));
        authorsColumn.prefWidthProperty().bind(table.widthProperty().divide(100 / columnWidth[i++]));
        reviewersColumn.prefWidthProperty().bind(table.widthProperty().divide(100 / columnWidth[i++]));
        dateColumn.prefWidthProperty().bind(table.widthProperty().divide(100 / columnWidth[i++]));
        
        i = 0;
        conferenceNamesColumn.setCellValueFactory(new PropertyValueFactory<TempTable, String>(variableNames[i++]));
        programChairColumn.setCellValueFactory(new PropertyValueFactory<TempTable, String>(variableNames[i++]));
        authorsColumn.setCellValueFactory(new PropertyValueFactory<TempTable, Integer>(variableNames[i++]));
        reviewersColumn.setCellValueFactory(new PropertyValueFactory<TempTable, Integer>(variableNames[i++]));
        dateColumn.setCellValueFactory(new PropertyValueFactory<TempTable, Date>(variableNames[i++]));
        
        
        columns.add(conferenceNamesColumn);
        columns.add(programChairColumn);
        columns.add(authorsColumn);
        columns.add(reviewersColumn);
        columns.add(dateColumn);
        
        return columns;
    }
}
