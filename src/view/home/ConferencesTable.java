package view.home;
import java.util.ArrayList;
import java.util.Date;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ConferencesTable {

	private String conferenceName, programChair;
	private int authors, reviewers;
	private Date conferenceDate;

	public ConferencesTable(String conferenceName, String programChair,
			int authors, int reviewers, Date conferenceDate) {
		this.conferenceName = conferenceName;
		this.programChair = programChair;
		this.authors = authors;
		this.reviewers = reviewers;
		this.conferenceDate = conferenceDate;
	}
	public String getConferenceName() {
		return conferenceName;
	}
	public String getProgramChair() {
		return programChair;
	}

	public int getAuthors() {
		return authors;
	}

	public int getReviewers() {
		return reviewers;
	}

	public Date getConferenceDate() {
		return conferenceDate;
	}

	public static ArrayList<TableColumn<ConferencesTable, ?>> getColumn(
			TableView table) {
		int i;

		ArrayList<TableColumn<ConferencesTable, ?>> columns = new ArrayList<TableColumn<ConferencesTable, ?>>();

		String[] columnsNames = {"Conference Names", "Program Chair",
				"Authors", "Reviewers", "Date"};
		String[] variableNames = {"conferenceName", "programChair", "authors",
				"reviwers", "conferenceDate"};
		Integer[] columnWidth = {30, 25, 10, 10, 25};

		i = 0;
		TableColumn<ConferencesTable, String> conferenceNamesColumn = new TableColumn<ConferencesTable, String>(
				columnsNames[i++]);
		TableColumn<ConferencesTable, String> programChairColumn = new TableColumn<ConferencesTable, String>(
				columnsNames[i++]);
		TableColumn<ConferencesTable, Integer> authorsColumn = new TableColumn<ConferencesTable, Integer>(
				columnsNames[i++]);
		TableColumn<ConferencesTable, Integer> reviewersColumn = new TableColumn<ConferencesTable, Integer>(
				columnsNames[i++]);
		TableColumn<ConferencesTable, Date> dateColumn = new TableColumn<ConferencesTable, Date>(
				columnsNames[i++]);

		i = 0;
		conferenceNamesColumn.prefWidthProperty().bind(
				table.widthProperty().divide(100 / columnWidth[i++]));
		programChairColumn.prefWidthProperty().bind(
				table.widthProperty().divide(100 / columnWidth[i++]));
		authorsColumn.prefWidthProperty().bind(
				table.widthProperty().divide(100 / columnWidth[i++]));
		reviewersColumn.prefWidthProperty().bind(
				table.widthProperty().divide(100 / columnWidth[i++]));
		dateColumn.prefWidthProperty().bind(
				table.widthProperty().divide(100 / columnWidth[i++]));

		i = 0;
		conferenceNamesColumn
				.setCellValueFactory(new PropertyValueFactory<ConferencesTable, String>(
						variableNames[i++]));
		programChairColumn
				.setCellValueFactory(new PropertyValueFactory<ConferencesTable, String>(
						variableNames[i++]));
		authorsColumn
				.setCellValueFactory(new PropertyValueFactory<ConferencesTable, Integer>(
						variableNames[i++]));
		reviewersColumn
				.setCellValueFactory(new PropertyValueFactory<ConferencesTable, Integer>(
						variableNames[i++]));
		dateColumn
				.setCellValueFactory(new PropertyValueFactory<ConferencesTable, Date>(
						variableNames[i++]));

		columns.add(conferenceNamesColumn);
		columns.add(programChairColumn);
		columns.add(authorsColumn);
		columns.add(reviewersColumn);
		columns.add(dateColumn);

		return columns;
	}
}
