package communication;

import model.*;
import java.util.*;

public class GetFieldsComOut 
{
	private List<ModelField> fields;
	
	/**
	 * 
	 */
	public GetFieldsComOut()
	{
		
	}
	
	/**
	 * 
	 * @param valid
	 * @param output
	 * @param fieldInfo
	 * @param fieldID
	 * @param fieldTitle
	 * @param projectID
	 */
	public GetFieldsComOut(List<ModelField> fields)
	{
		this.fields = fields;
	}
	
	/**
	 * Return the proper output statement for the method execution
	 */
	public String toString()
	{
		String output = "";
		for (ModelField field : fields)
		{
			output = output + field.getProjectId() +"\n" +
					field.getId() + "\n" +
					field.getTitle() + "\n";
		}
		return output;
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

	
}
