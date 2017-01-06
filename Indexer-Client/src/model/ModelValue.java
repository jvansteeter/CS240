package model;

public class ModelValue 
{
	private int id;
	private String value;
	private String field;
	private int row;
	private int imageId;
	
	/**
	 * Empty constructor
	 */
	public ModelValue()
	{
		
	}
	
	/**
	 * Constructor that designates the number of rows and columns 
	 * @param rows
	 * @param cols
	 */
	public ModelValue(String value, String field, int row, int imageID)
	{
		this.value = value;
		this.field = field;
		this.row = row;
		this.imageId = imageID;
	}
	
	public ModelValue(int id, String value, String field, int row, int imageID)
	{
		this.id = id;
		this.value = value;
		this.field = field;
		this.row = row;
		this.imageId = imageID;
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
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}

	/**
	 * @param field the field to set
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @param row the row to set
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * @return the imageID
	 */
	public int getImageId() {
		return imageId;
	}

	/**
	 * @param imageID the imageID to set
	 */
	public void setImageId(int imageID) {
		this.imageId = imageID;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ModelValue [id=" + id + ", value=" + value + ", field=" + field
				+ ", row=" + row + ", imageId=" + imageId + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((field == null) ? 0 : field.hashCode());
		result = prime * result + id;
		result = prime * result + imageId;
		result = prime * result + row;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		ModelValue other = (ModelValue) obj;
		if (field == null) {
			if (other.field != null)
				return false;
		} else if (!field.equals(other.field))
			return false;
		if (id != other.id)
			return false;
		if (imageId != other.imageId)
			return false;
		if (row != other.row)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	

}
