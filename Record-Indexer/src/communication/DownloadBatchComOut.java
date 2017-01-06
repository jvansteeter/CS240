package communication;

import java.util.*;
import model.*;

public class DownloadBatchComOut 
{
	private int batchID;
	private int projectID;
	private String imageURL;
	private int firstYcoord;
	private int recordHeight;
	private int numRecords;
	private int numFields;
	private List<ModelField> fields;
	private String serverURL;
	
	/**
	 * Empty constructor
	 */
	public DownloadBatchComOut()
	{
		
	}
	
	/**
	 * 
	 * @param valid
	 * @param output
	 * @param projectID
	 * @param imageURL
	 */
	public DownloadBatchComOut(int batchID, int projectID, String imageURL, int firstYcoord, int recordHeight, int numRecords, int numFields, List<ModelField> fields, String serverURL)
	{
		this.batchID = batchID;
		this.projectID = projectID;
		this.imageURL = imageURL;
		this.firstYcoord = firstYcoord;
		this.recordHeight = recordHeight;
		this.numRecords = numRecords;
		this.numFields = numFields;
		this.fields = fields;
		this.serverURL = serverURL;
	}
	
	/**
	 * Return the proper output statement for the method execution
	 */
	public String toString()
	{
		String output = "";
		output = batchID + "\n" +
				projectID + "\n" +
				imageURL + "\n" + 
				firstYcoord + "\n" + 
				recordHeight + "\n" + 
				numRecords + "\n" + 
				numFields + "\n";
		for (ModelField field : fields)
		{
			output = output + field.getId() + "\n" +
					field.getPosition() + "\n" + 
					field.getTitle() + "\n" +
					serverURL + "/" + field.getHelpHTML() + "\n" + 
					field.getXCoord() + "\n" + 
					field.getWidth() + "\n";
			if (field.getKnownData() != null)
			{
				output = output + serverURL + "/" + field.getKnownData() + "\n";
			}
		}
		return output;
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

	/**
	 * @return the imageURL
	 */
	public String getImageURL() {
		return imageURL;
	}

	/**
	 * @param imageURL the imageURL to set
	 */
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	/**
	 * @return the firstYcoord
	 */
	public int getFirstYcoord() {
		return firstYcoord;
	}

	/**
	 * @param firstYcoord the firstYcoord to set
	 */
	public void setFirstYcoord(int firstYcoord) {
		this.firstYcoord = firstYcoord;
	}

	/**
	 * @return the recordHeight
	 */
	public int getRecordHeight() {
		return recordHeight;
	}

	/**
	 * @param recordHeight the recordHeight to set
	 */
	public void setRecordHeight(int recordHeight) {
		this.recordHeight = recordHeight;
	}

	/**
	 * @return the fields
	 */
	public List<ModelField> getFields() {
		return fields;
	}

	/**
	 * @param fields the fields to set
	 */
	public void setFields(List<ModelField> fields) {
		this.fields = fields;
	}

	/**
	 * @return the numRecords
	 */
	public int getNumRecords() {
		return numRecords;
	}

	/**
	 * @param numRecords the numRecords to set
	 */
	public void setNumRecords(int numRecords) {
		this.numRecords = numRecords;
	}

	/**
	 * @return the numFields
	 */
	public int getNumFields() {
		return numFields;
	}

	/**
	 * @param numFields the numFields to set
	 */
	public void setNumFields(int numFields) {
		this.numFields = numFields;
	}

	/**
	 * @return the serverURL
	 */
	public String getServerURL() {
		return serverURL;
	}

	/**
	 * @param serverURL the serverURL to set
	 */
	public void setServerURL(String serverURL) {
		this.serverURL = serverURL;
	}

	
}
