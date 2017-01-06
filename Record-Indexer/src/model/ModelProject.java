package model;

public class ModelProject 
{
	private int id;
	private String title;
	private int recordsPerImage;
	private int firstYCoord;
	private int recordHeight;
	
	/**
	 * 
	 */
	public ModelProject()
	{
		
	}
	
	/**
	 * 
	 * @param title
	 * @param recordsPerImage
	 * @param firstYCoord
	 * @param recordHeight
	 */
	public ModelProject(String title, int recordsPerImage, int firstYCoord, int recordHeight)
	{
		this.title = title;
		this.recordsPerImage = recordsPerImage;
		this.firstYCoord = firstYCoord;
		this.recordHeight = recordHeight;
	}
	
	public ModelProject(int id, String title, int recordsPerImage, int firstYCoord, int recordHeight)
	{
		this.id = id;
		this.title = title;
		this.recordsPerImage = recordsPerImage;
		this.firstYCoord = firstYCoord;
		this.recordHeight = recordHeight;
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
	public int getRecordsPerImage() {
		return recordsPerImage;
	}

	/**
	 * 
	 * @param recordsPerImage
	 */
	public void setRecordsPerImage(int recordsPerImage) {
		this.recordsPerImage = recordsPerImage;
	}

	/**
	 * 
	 * @return
	 */
	public int getFirstYCoord() {
		return firstYCoord;
	}

	/**
	 * 
	 * @param firstYCoord
	 */
	public void setFirstYCoord(int firstYCoord) {
		this.firstYCoord = firstYCoord;
	}

	/**
	 * 
	 * @return
	 */
	public int getRecordHeight() {
		return recordHeight;
	}

	/**
	 * 
	 * @param recordHeight
	 */
	public void setRecordHeight(int recordHeight) {
		this.recordHeight = recordHeight;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() 
	{
		return "ModelProject [id=" + id + ", title=" + title
				+ ", recordsPerImage=" + recordsPerImage + ", firstYCoord="
				+ firstYCoord + ", recordHeight=" + recordHeight + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + firstYCoord;
		result = prime * result + id;
		result = prime * result + recordHeight;
		result = prime * result + recordsPerImage;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		ModelProject other = (ModelProject) obj;
		if (firstYCoord != other.firstYCoord)
			return false;
		if (id != other.id)
			return false;
		if (recordHeight != other.recordHeight)
			return false;
		if (recordsPerImage != other.recordsPerImage)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	
	

}
