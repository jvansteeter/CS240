package communication;

public class ValidateUserComOut 
{
	private boolean valid;
	private String output;
	private String firstName;
	private String lastName;
	private int numRecords;
	
	/**
	 * 
	 */
	public ValidateUserComOut(boolean valid)
	{
		this.valid = valid;
	}
	
	/**
	 * 
	 * @param valid whether the operation was successful
	 * @param output the output of the operation
	 * @param firstName user's first name
	 * @param lastName user's last name
	 * @param numRecords number of records user has indexed
	 */
	public ValidateUserComOut(boolean valid, String output, String firstName, String lastName, int numRecords)
	{
		this.valid = valid;
		this.output = output;
		this.firstName = firstName;
		this.lastName = lastName;
		this.numRecords = numRecords;
	}
	
	/**
	 * Return the proper output statement for the method execution
	 */
	@Override
	public String toString()
	{
		String output = "";
		if(valid)
		{
			output = "TRUE\n" +
					firstName + "\n" +
					lastName + "\n" +
					numRecords + "\n";
		}
		else
		{
			output = "FALSE\n";
		}
		return output;
	}

	/**
	 * 
	 * @return if validation was successful
	 */
	public boolean isValid() {
		return valid;
	}

	/**
	 * 
	 * @param validated sets validation
	 */
	public void setValid(boolean validated) {
		this.valid = validated;
	}

	/**
	 * 
	 * @return method's output, same as toString()
	 */
	public String getOutput() {
		return output;
	}

	/**
	 * 
	 * @param output 
	 */
	public void setOutput(String output) {
		this.output = output;
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
	public int getNumRecords() {
		return numRecords;
	}

	/**
	 * 
	 * @param numRecords
	 */
	public void setNumRecords(int numRecords) {
		this.numRecords = numRecords;
	}
	
	

}
