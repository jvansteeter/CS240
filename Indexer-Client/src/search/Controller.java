package search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import client.ClientCommunicator;
import communication.*;

public class Controller 
{
	private LeftPanel leftPanel;
	private RightPanel rightPanel;
	
	public Controller()
	{
		
	}
	
	public ArrayList<String> getSelectedValues()
	{
		return leftPanel.getSelectedValues();
	}
	
	public void search() 
	{
		String hostName = leftPanel.getHost();
		int portNumber = Integer.parseInt(leftPanel.getPort());
		
		ClientCommunicator client = new ClientCommunicator(hostName, portNumber);
		
		String username = leftPanel.getUsername();
		String password = leftPanel.getPassword();
		
		ArrayList<String> fieldIDs = leftPanel.getSelectedValues();
		ArrayList<String> values = parseSearchValues(rightPanel.getSearchFor());
		
		SearchComIn comIn = new SearchComIn(username, password, fieldIDs, values);
		SearchComOut comOut = client.search(comIn);
		
		if (comOut.getTuples().size() != 0)
		{
			ArrayList<SearchTuple> result = comOut.getTuples();
			ArrayList<String> imageURLs = new ArrayList<String>();
			for(SearchTuple tuple : result)
			{
				imageURLs.add(tuple.getImageURL());
			}
			rightPanel.setResultList(imageURLs);
		}
		else
		{
			rightPanel.searchFailed();
		}
	}

	/**
	 * @return the leftPanel
	 */
	public LeftPanel getLeftPanel() {
		return leftPanel;
	}

	/**
	 * @param leftPanel the leftPanel to set
	 */
	public void setLeftPanel(LeftPanel leftPanel) {
		this.leftPanel = leftPanel;
	}

	/**
	 * @return the rightPanel
	 */
	public RightPanel getRightPanel() {
		return rightPanel;
	}

	/**
	 * @param rightPanel the rightPanel to set
	 */
	public void setRightPanel(RightPanel rightPanel) {
		this.rightPanel = rightPanel;
	}
	
	private ArrayList<String> parseSearchValues(String valuesString)
	{
		ArrayList<String> result = new ArrayList<String>();
		List<String> values = Arrays.asList(valuesString.split(","));
		
		for (String value: values)
		{
			result.add(value);
		}
		return result;
	}

}
