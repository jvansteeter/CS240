package communication;

import java.util.*;

/**
 * 
 * @author Joshua
 *
 */
public class SearchComOut 
{
	private ArrayList<SearchTuple> tuples;
	
	/**
	 * 
	 */
	public SearchComOut()
	{
		
	}
	
	/**
	 * 
	 * @param valid
	 * @param output
	 * @param searchResult
	 * @param batchID
	 * @param imageURL
	 * @param recordNum
	 * @param fieldID
	 */
	public SearchComOut(ArrayList<SearchTuple> tuples)
	{
		this.tuples = tuples;
	}
	
	/**
	 * Return the proper output statement for the method execution
	 */
	public String toString()
	{
		String output = "";
		for (SearchTuple tuple : tuples)
		{
			output = output + tuple.getBatchID() + "\n" +
					tuple.getImageURL() + "\n" +
					tuple.getRecordNumber() + "\n" +
					tuple.getFieldID() + "\n";
		}
		if (output.equals(""))
		{
			return "FAILED\n";
		}
		return output;
	}

	/**
	 * @return the tuples
	 */
	public ArrayList<SearchTuple> getTuples() {
		return tuples;
	}

	/**
	 * @param tuples the tuples to set
	 */
	public void setTuples(ArrayList<SearchTuple> tuples) {
		this.tuples = tuples;
	}

	
}
