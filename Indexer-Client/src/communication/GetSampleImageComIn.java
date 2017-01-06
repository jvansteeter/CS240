package communication;

public class GetSampleImageComIn 
{
	private String username;
	private String password;
	private int projectID;
	
	/**
	 * 
	 */
	public GetSampleImageComIn()
	{
		
	}
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @param projectID
	 */
	public GetSampleImageComIn(String username, String password, int projectID)
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
	public int getProjectID() {
		return projectID;
	}

	/**
	 * @param projectID the projectID to set
	 */
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	
	

}
