package communication;

public class SubmitBatchComOut 
{
	private boolean valid;
	
	/**
	 * 
	 */
	public SubmitBatchComOut()
	{
		valid = false;
	}
	
	/**
	 * 
	 * @param output
	 */
	public SubmitBatchComOut(boolean valid)
	{
		this.valid = valid;
	}
	
	public String toString()
	{
		if (valid)
		{
			return "TRUE\n";
		}
		else
		{
			return "FAILED\n";
		}
	}

	/**
	 * @return the valid
	 */
	public boolean isValid() {
		return valid;
	}

	/**
	 * @param valid the valid to set
	 */
	public void setValid(boolean valid) {
		this.valid = valid;
	}

	
	
	

}
