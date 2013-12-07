package view.util;

/**
 * Wrapper class around custom review row data (less data than review row) in the the table
 * @author Cathryn C
 * @version 11-29-13
 */
public class CustomReviewRow {
	
	private int id;
	private String paperTitle;
	private String confName;
	
	public CustomReviewRow(final int id, final String title, final String confName) {
		this.id = id;
		this.paperTitle = title;
		this.confName = confName;
	}
	
	public int getId() {
		return id;
	}
	
	public String getTitle() {
		return paperTitle;
	}
	
	public String getConferenceName() {
		return confName;
	}

}
