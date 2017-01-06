package communication;

import java.util.*;

public class SearchComIn 
{
	private String username;
	private String password;
	private ArrayList<String> fields;
	private ArrayList<String> values;
	
	/**
	 * 
	 */
	public SearchComIn()
	{
		
	}
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @param fields
	 * @param searchValues
	 */
	public SearchComIn(String username, String password, ArrayList<String> fields, ArrayList<String> values)
	{
		this.username = username;
		this.password = password;
		this.fields = fields;
		this.values = values;
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
	 * @return the fields
	 */
	public ArrayList<String> getFields() {
		return fields;
	}

	/**
	 * @param fields the fields to set
	 */
	public void setFields(ArrayList<String> fields) {
		this.fields = fields;
	}

	/**
	 * @return the values
	 */
	public ArrayList<String> getValues() {
		return values;
	}

	/**
	 * @param values the values to set
	 */
	public void setValues(ArrayList<String> values) {
		this.values = values;
	}

	
}
