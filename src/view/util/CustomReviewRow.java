package view.util;

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
