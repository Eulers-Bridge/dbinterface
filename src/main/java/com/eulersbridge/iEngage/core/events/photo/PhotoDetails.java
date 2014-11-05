/**
 * 
 */
package com.eulersbridge.iEngage.core.events.photo;

/**
 * @author Greg Newitt
 *
 */
public class PhotoDetails 
{
	Long nodeId;
	String url;
	String title;
	String description;
	Long date;
	
	/**
	 * @param nodeId
	 * @param url
	 * @param title
	 * @param description
	 * @param date
	 */
	public PhotoDetails(Long nodeId, String url, String title,
			String description, Long date) {
		super();
		this.nodeId = nodeId;
		this.url = url;
		this.title = title;
		this.description = description;
		this.date = date;
	}

	public PhotoDetails() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the nodeId
	 */
	public Long getNodeId() {
		return nodeId;
	}

	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the date
	 */
	public Long getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Long date) {
		this.date = date;
	}

}
