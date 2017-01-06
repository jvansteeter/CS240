package communication;

import java.util.*;

public class SubmitBatchComIn 
{
	private String username;
	private String password;
	private int batchID;
	//private List<ModelField> fields;
	//private List<ModelValue> values;
	private List<ArrayList<String>> values;
	
	/**
	 * 
	 */
	public SubmitBatchComIn()
	{
		
	}
	
	/**
	 * 
	 * @param user
	 * @param password
	 * @param batchID
	 * @param fieldValues
	 */
	public SubmitBatchComIn(String username, String password, int batchID, List<ArrayList<String>> values)
	{
		this.username = username;
		this.password = password;
		this.batchID = batchID;
		this.values = values;
	}
	
	/**
	 * Return the proper output statement for the method execution
	 */
	public String toString()
	{
		return "";
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
	 * @return the batchID
	 */
	public int getBatchID() {
		return batchID;
	}

	/**
	 * @param batchID the batchID to set
	 */
	public void setBatchID(int batchID) {
		this.batchID = batchID;
	}

//	/**
//	 * @return the fields
//	 */
//	public List<ModelField> getFields() {
//		return fields;
//	}
//
//	/**
//	 * @param fields the fields to set
//	 */
//	public void setFields(List<ModelField> fields) {
//		this.fields = fields;
//	}

	/**
	 * @return the values
	 */
	public List<ArrayList<String>> getValues() {
		return values;
	}

	/**
	 * @param values the values to set
	 */
	public void setValues(List<ArrayList<String>> values) {
		this.values = values;
	}

	
}
