package model;

public class ModelUser 
{
	private int id;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private int indexedRecords;
	private int currentBatch;
	
	/**
	 * 
	 */
	public ModelUser()
	{
		
	}
	
	/**
	 * 
	 * @param userName
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param indexedRecords
	 */
	public ModelUser(String userName, String password, String firstName, String lastName, String email, int indexedRecords)
	{
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.indexedRecords = indexedRecords;
		this.currentBatch = 0;
	}
	
	public ModelUser(int id, String userName, String password, String firstName, String lastName, String email, int indexedRecords, int currentBatch)
	{
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.indexedRecords = indexedRecords;
		this.currentBatch = currentBatch;
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
	public String getUserName() {
		return userName;
	}

	/**
	 * 
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 
	 * @return
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * 
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * 
	 * @return
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * 
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * 
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 
	 * @return
	 */
	public int getIndexedRecords() {
		return indexedRecords;
	}

	/**
	 * 
	 * @param indexedRecords
	 */
	public void setIndexedRecords(int indexedRecords) {
		this.indexedRecords = indexedRecords;
	}
	
	/**
	 * Determines what batch the user is currently working on
	 * @return
	 */
	public int getCurrentBatch() {
		return currentBatch;
	}

	/**
	 * Assigns a batch to user
	 * @param currentProject
	 */
	public void setCurrentBatch(int currentBatch) {
		this.currentBatch = currentBatch;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ModelUser [id=" + id + ", userName=" + userName + ", password="
				+ password + ", firstName=" + firstName + ", lastName="
				+ lastName + ", email=" + email + ", indexedRecords="
				+ indexedRecords + ", currentBatch=" + currentBatch + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + currentBatch;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + id;
		result = prime * result + indexedRecords;
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
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
		ModelUser other = (ModelUser) obj;
		if (currentBatch != other.currentBatch)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (indexedRecords != other.indexedRecords)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	

}
