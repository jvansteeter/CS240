package servertester.controllers;

import java.util.*;

import client.ClientCommunicator;
import communication.*;
import servertester.views.*;

public class Controller implements IController {

	private IView _view;
	
	public Controller() {
		return;
	}
	
	public IView getView() {
		return _view;
	}
	
	public void setView(IView value) {
		_view = value;
	}
	
	// IController methods
	//
	
	@Override
	public void initialize() {
		getView().setHost("localhost");
		getView().setPort("8081");
		operationSelected();
	}

	@Override
	public void operationSelected() {
		ArrayList<String> paramNames = new ArrayList<String>();
		paramNames.add("User");
		paramNames.add("Password");
		
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			break;
		case GET_PROJECTS:
			break;
		case GET_SAMPLE_IMAGE:
			paramNames.add("Project");
			break;
		case DOWNLOAD_BATCH:
			paramNames.add("Project");
			break;
		case GET_FIELDS:
			paramNames.add("Project");
			break;
		case SUBMIT_BATCH:
			paramNames.add("Batch");
			paramNames.add("Record Values");
			break;
		case SEARCH:
			paramNames.add("Fields");
			paramNames.add("Search Values");
			break;
		default:
			assert false;
			break;
		}
		
		getView().setRequest("");
		getView().setResponse("");
		getView().setParameterNames(paramNames.toArray(new String[paramNames.size()]));
	}

	@Override
	public void executeOperation() {
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			validateUser();
			break;
		case GET_PROJECTS:
			getProjects();
			break;
		case GET_SAMPLE_IMAGE:
			getSampleImage();
			break;
		case DOWNLOAD_BATCH:
			downloadBatch();
			break;
		case GET_FIELDS:
			getFields();
			break;
		case SUBMIT_BATCH:
			submitBatch();
			break;
		case SEARCH:
			search();
			break;
		default:
			assert false;
			break;
		}
	}
	
	private void validateUser() 
	{
		String hostName = getView().getHost();
		int portNumber = Integer.parseInt(getView().getPort());
		
		ClientCommunicator client = new ClientCommunicator(hostName, portNumber);
		
		String[] params = getView().getParameterValues();
		String username = params[0];
		String password = params[1];
		ValidateUserComIn comIn = new ValidateUserComIn(username, password);
		ValidateUserComOut comOut = client.validateUser(comIn);
		
		if (comOut != null)
		{
			getView().setResponse(comOut.toString());
		}
		else
		{
			getView().setResponse("FAILED\n");
		}
	}
	
	private void getProjects() 
	{
		String hostName = getView().getHost();
		int portNumber = Integer.parseInt(getView().getPort());
		
		ClientCommunicator client = new ClientCommunicator(hostName, portNumber);
		
		String[] params = getView().getParameterValues();
		String username = params[0];
		String password = params[1];
		
		GetProjectsComIn comIn = new GetProjectsComIn(username, password);
		GetProjectsComOut comOut = client.getProjects(comIn);
		
		if (comOut != null)
		{
			getView().setResponse(comOut.toString());
		}
		else
		{
			getView().setResponse("FAILED\n");
		}
	}
	
	private void getSampleImage() 
	{
		String hostName = getView().getHost();
		int portNumber = Integer.parseInt(getView().getPort());
		
		ClientCommunicator client = new ClientCommunicator(hostName, portNumber);
		
		String[] params = getView().getParameterValues();
		String username = params[0];
		String password = params[1];
		int projectID = Integer.parseInt(params[2]);
		
		GetSampleImageComIn comIn = new GetSampleImageComIn(username, password, projectID);
		GetSampleImageComOut comOut = client.getSampleImage(comIn);
		
		if (comOut != null)
		{
			getView().setResponse(comOut.toString());
		}
		else
		{
			getView().setResponse("FAILED\n");
		}
	}
	
	private void downloadBatch() 
	{
		String hostName = getView().getHost();
		int portNumber = Integer.parseInt(getView().getPort());
		
		ClientCommunicator client = new ClientCommunicator(hostName, portNumber);
		
		String[] params = getView().getParameterValues();
		String username = params[0];
		String password = params[1];
		int projectID = Integer.parseInt(params[2]);
		
		DownloadBatchComIn comIn = new DownloadBatchComIn(username, password, projectID);
		DownloadBatchComOut comOut = client.downloadBatch(comIn);
		
		if (comOut != null)
		{
			getView().setResponse(comOut.toString());
		}
		else
		{
			getView().setResponse("FAILED\n");
		}
	}
	
	private void getFields() 
	{
		String hostName = getView().getHost();
		int portNumber = Integer.parseInt(getView().getPort());
		
		ClientCommunicator client = new ClientCommunicator(hostName, portNumber);
		
		String[] params = getView().getParameterValues();
		String username = params[0];
		String password = params[1];
		String projectID = params[2];
		
		GetFieldsComIn comIn = new GetFieldsComIn(username, password, projectID);
		GetFieldsComOut comOut = client.getFields(comIn);
		
		if (comOut != null)
		{
			getView().setResponse(comOut.toString());
		}
		else
		{
			getView().setResponse("FAILED\n");
		}
	}
	
	private void submitBatch() 
	{
		String hostName = getView().getHost();
		int portNumber = Integer.parseInt(getView().getPort());
		
		ClientCommunicator client = new ClientCommunicator(hostName, portNumber);
		
		String[] params = getView().getParameterValues();
		String username = params[0];
		String password = params[1];
		int projectID = Integer.parseInt(params[2]);
		String valueStream = params[3];
		
		List<ArrayList<String>> values = parseValues(valueStream);
		
		SubmitBatchComIn comIn = new SubmitBatchComIn(username, password, projectID, values);
		SubmitBatchComOut comOut = client.submitBatch(comIn);
		
		if (comOut != null)
		{
			getView().setResponse(comOut.toString());
		}
		else
		{
			getView().setResponse("FAILED\n");
		}
	}
	
	private void search() 
	{
		String hostName = getView().getHost();
		int portNumber = Integer.parseInt(getView().getPort());
		
		ClientCommunicator client = new ClientCommunicator(hostName, portNumber);
		
		String[] params = getView().getParameterValues();
		String username = params[0];
		String password = params[1];
		String fieldIDsString = params[2];
		String valuesString = params[3];
		
		ArrayList<String> fieldIDs = parseSearchIDs(fieldIDsString);
		ArrayList<String> values = parseSearchValues(valuesString);
		
//		System.out.println(fieldIDs);
//		System.out.println(values);
		
		SearchComIn comIn = new SearchComIn(username, password, fieldIDs, values);
		SearchComOut comOut = client.search(comIn);
		
		if (comOut != null)
		{
			getView().setResponse(comOut.toString());
		}
		else
		{
			getView().setResponse("FAILED\n");
		}
	}
	
	private List<ArrayList<String>> parseValues(String valueStream)
	{
		List<ArrayList<String>> values = new ArrayList<ArrayList<String>>();
		List<String> rows = Arrays.asList(valueStream.split(";"));
		
		for (String row : rows)
		{
			List<String> cells = Arrays.asList(row.split(","));
			ArrayList<String> newRow = new ArrayList<String>();
			newRow.addAll(cells);
			values.add(newRow);
		}
		//System.out.println(values.toString());
		
		return values;
	}
	
	private ArrayList<String> parseSearchIDs(String fieldIDsString)
	{
		ArrayList<String> result = new ArrayList<String>();
		List<String> values = Arrays.asList(fieldIDsString.split(","));
		
		for (String value : values)
		{
			result.add(value);
		}
		return result;
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

