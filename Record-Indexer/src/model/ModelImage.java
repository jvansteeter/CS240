package model;

public class ModelImage 
{
	/**
	 * 
	 * @author jvanstee
	 *
	 */
	
	private int id;
	private String fileName;
	private int projectId;
	private int checkedToUser;
	
	/**
	 * Blank constructor
	 */
	public ModelImage()
	{
		
	}
	
	/**
	 * 
	 * @param fileName
	 * @param projectId
	 * @param checkedToUser
	 */
	public ModelImage(String fileName, int projectId, int checkedToUser)
	{
		this.fileName = fileName;
		this.projectId = projectId;
		this.checkedToUser = checkedToUser;
	}
	
	/**
	 * 
	 * @param id
	 * @param fileName
	 * @param projectId
	 * @param checkedToUser
	 */
	public ModelImage(int id, String fileName, int projectId, int checkedToUser)
	{
		this.id = id;
		this.fileName = fileName;
		this.projectId = projectId;
		this.checkedToUser = checkedToUser;
	}

	/**
	 * 
	 * @return
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * 
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
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
	 * @return the checkedToUser
	 */
	public int getCheckedToUser() {
		return checkedToUser;
	}

	/**
	 * @param checkedToUser the checkedToUser to set
	 */
	public void setCheckedToUser(int checkedToUser) {
		this.checkedToUser = checkedToUser;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ModelImage [id=" + id + ", fileName=" + fileName
				+ ", projectId=" + projectId + ", checkedToUser="
				+ checkedToUser + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + checkedToUser;
		result = prime * result
				+ ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result + id;
		result = prime * result + projectId;
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
		ModelImage other = (ModelImage) obj;
		if (checkedToUser != other.checkedToUser)
			return false;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (id != other.id)
			return false;
		if (projectId != other.projectId)
			return false;
		return true;
	}

	

}
