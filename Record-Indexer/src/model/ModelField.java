package model;

public class ModelField 
{
	private int id;
	private String title;
	private int xCoord;
	private int width;
	private int position;
	private String helpHTML;
	private String knownData;
	private int projectId;
	
	/**
	 * 
	 */
	public ModelField()
	{
		
	}
	
	/**
	 * 
	 * @param title
	 * @param xCoord
	 * @param width
	 * @param helpHTML
	 * @param knownData
	 */
	public ModelField(String title, int xCoord, int width, int position, String helpHTML, String knownData)
	{
		this.title = title; 
		this.xCoord = xCoord;
		this.width = width;
		this.position = position;
		this.helpHTML = helpHTML;
		this.knownData = knownData;
		this.projectId = 0;
	}
	
	/**
	 * 
	 * @param id
	 * @param title
	 * @param xCoord
	 * @param width
	 * @param helpHTML
	 * @param knownData
	 */
	public ModelField(int id, String title, int xCoord, int width, int position, String helpHTML, String knownData, int projectId)
	{
		this.id = id;
		this.title = title; 
		this.xCoord = xCoord;
		this.width = width;
		this.position = position;
		this.helpHTML = helpHTML;
		this.knownData = knownData;
		this.projectId = projectId;
	}
	
	/**
	 * 
	 * @param title
	 * @param xCoord
	 * @param width
	 * @param helpHTML
	 * @param knownData
	 * @param projectId
	 */
	public ModelField(String title, int xCoord, int width, int position, String helpHTML, String knownData, int projectId)
	{
		this.title = title; 
		this.xCoord = xCoord;
		this.width = width;
		this.position = position;
		this.helpHTML = helpHTML;
		this.knownData = knownData;
		this.projectId = projectId;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 
	 * @return
	 */
	public int getXCoord() {
		return xCoord;
	}

	/**
	 * 
	 * @param xCoord
	 */
	public void setXCoord(int xCoord) {
		this.xCoord = xCoord;
	}

	/**
	 * 
	 * @return
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * 
	 * @param width
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * 
	 * @return
	 */
	public String getHelpHTML() {
		return helpHTML;
	}

	/**
	 * 
	 * @param helpHTML
	 */
	public void setHelpHTML(String helpHTML) {
		this.helpHTML = helpHTML;
	}

	/**
	 * 
	 * @return
	 */
	public String getKnownData() {
		return knownData;
	}

	/**
	 * 
	 * @param knownData
	 */
	public void setKnownData(String knownData) {
		this.knownData = knownData;
	}

	/**
	 * @return the projectId
	 */
	public int getProjectId() {
		return projectId;
	}

	/**
	 * @param projectId the projectId to set
	 */
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	
	

	/**
	 * @return the xCoord
	 */
	public int getxCoord() {
		return xCoord;
	}

	/**
	 * @param xCoord the xCoord to set
	 */
	public void setxCoord(int xCoord) {
		this.xCoord = xCoord;
	}

	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ModelField [id=" + id + ", title=" + title + ", xCoord="
				+ xCoord + ", width=" + width + ", helpHTML=" + helpHTML
				+ ", knownData=" + knownData + ", projectId=" + projectId + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((helpHTML == null) ? 0 : helpHTML.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((knownData == null) ? 0 : knownData.hashCode());
		result = prime * result + projectId;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + width;
		result = prime * result + xCoord;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModelField other = (ModelField) obj;
		if (helpHTML == null) {
			if (other.helpHTML != null)
				return false;
		} else if (!helpHTML.equals(other.helpHTML))
			return false;
		if (id != other.id)
			return false;
		if (knownData == null) {
			if (other.knownData != null)
				return false;
		} else if (!knownData.equals(other.knownData))
			return false;
		if (projectId != other.projectId)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (width != other.width)
			return false;
		if (xCoord != other.xCoord)
			return false;
		return true;
	}

}
