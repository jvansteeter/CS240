package communication;

public class GetSampleImageComOut 
{
	private String hostURL;
	private String imageFileName;
	
	/**
	 * 
	 */
	public GetSampleImageComOut()
	{
		
	}
	
	/**
	 * 
	 * @param valid
	 * @param output
	 * @param imageURL
	 */
	public GetSampleImageComOut(String hostURL, String imageFileName)
	{
		this.hostURL = hostURL;
		this.imageFileName = imageFileName;
	}
	
	/**
	 * Return the proper output statement for the method execution
	 */
	public String toString()
	{
		return hostURL + "/" + imageFileName;
	}

	/**
	 * @return the hostURL
	 */
	public String getHostURL() {
		return hostURL;
	}

	/**
	 * @param hostURL the hostURL to set
	 */
	public void setHostURL(String hostURL) {
		this.hostURL = hostURL;
	}

	/**
	 * @return the imageFileName
	 */
	public String getImageFileName() {
		return imageFileName;
	}

	/**
	 * @param imageFileName the imageFileName to set
	 */
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	
	
	

}
