package communication;

public class GetFieldsComIn 
{
	private String username;
	private String password;
	private String projectID;
	
	/**
	 * 
	 */
	public GetFieldsComIn()
	{
		
	}
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @param project
	 */
	public GetFieldsComIn(String username, String password, String projectID)
	{
		this.username = username;
		this.password = password;
		this.projectID = projectID;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the projectID
	 */
	public String getProjectID() {
		return projectID;
	}

	/**
	 * @param projectID the projectID to set
	 */
	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}
	
}
