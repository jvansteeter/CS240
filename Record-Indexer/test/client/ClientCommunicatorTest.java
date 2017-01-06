package client;

//import static org.junit.Assert.*;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import communication.*;
import server.*;


public class ClientCommunicatorTest 
{
	
	/*@Test
	public void test() throws ServerException
	{
		ServerFacade.initialize();
		
		ClientCommunicator communicator = new ClientCommunicator(Server.getURL(), 8081);
		String username = "test1";
		String password = "test1";

		ValidateUserComIn validate = new ValidateUserComIn(username,password);
		String validateUser = communicator.validateUser(validate).toString();
		assert(validateUser != null);
		
		
		
		Contact bob = new Contact(-1, "Bob White", "801-999-9999", "1234 State Street", 
									"bob@white.org", "http://www.white.org/bob");
		addParams.setContact(bob);
		communicator.addContact(addParams);

		contacts = communicator.getAllContacts().getContacts();
		assertEquals(1, contacts.size());
		
		Contact newBob = contacts.get(0);
		compareContacts(bob, newBob, false);
		
		newBob.setName("Robert White");
		newBob.setPhone("801-000-0000");
		newBob.setAddress("1234 Riverside Drive");
		newBob.setEmail("robert@white.org");
		newBob.setUrl("http://www.white.org/robert");
		
		updateParams.setContact(newBob);
		communicator.updateContact(updateParams);
		
		contacts = communicator.getAllContacts().getContacts();
		assertEquals(1, contacts.size());
		
		Contact newerBob = contacts.get(0);
		compareContacts(newBob, newerBob, true);
		
		deleteParams.setContact(newerBob);
		communicator.deleteContact(deleteParams);
		
		contacts = communicator.getAllContacts().getContacts();
		assertEquals(0, contacts.size());
	}*/

	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		ServerFacade.initialize();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception 
	{
		return;
	}
	
	private ClientCommunicator client;

	@Before
	public void setUp() throws Exception 
	{
		int port = 8081;
		client = new ClientCommunicator(InetAddress.getLocalHost().getHostName(), port);
	}

	@After
	public void tearDown() throws Exception 
	{
		client = null;
	}

	@Test
	public void validateUserTest() 
	{
		ValidateUserComIn test = new ValidateUserComIn("test1", "test1");
		String correct = "TRUE\n" +
						"Test\n" +
						"One\n" +
						"0\n";
		String result = client.validateUser(test).toString();
		assert(correct.equals(result));
	}
	
	@Test
	public void getProjectsTest()
	{
		GetProjectsComIn comIn = new GetProjectsComIn("test1", "test1");
		String correct = "1\n" + 
				"1890 Census\n" +
				"2\n" +
				"1900 Census\n" +
				"3\n" +
				"Draft Records\n";
		String result = client.getProjects(comIn).toString();
		assert(correct.equals(result));
	}
	
	@Test
	public void getSampleImageTest()
	{
		GetSampleImageComIn comIn = new GetSampleImageComIn("test1", "test1", 1);
		String result = client.getSampleImage(comIn).toString();
		assert(!result.equals("FAILED"));
		assert(result.contains(Server.getURL()));
	}
	
	@Test
	public void downloadBatchTest()
	{
		DownloadBatchComIn comIn = new DownloadBatchComIn("test1", "test1", 1);
		String result = client.downloadBatch(comIn).toString();
		assert(!result.equals("FAILED"));
		assert(result.contains(Server.getURL()));
	}
	
	@Test
	public void submitBatchTest()
	{
		String temp = "this,is,a,test;of,my,junit,skills;";
		List<ArrayList<String>> values = parseValues(temp);
		SubmitBatchComIn comIn = new SubmitBatchComIn("test1", "test1", 1, values);
		String result = client.submitBatch(comIn).toString();
		assert(!result.equals("FAILED\n"));
		assert(result.equals("TRUE\n"));
	}
	
	@Test
	public void getFieldsTest()
	{
		GetFieldsComIn comIn = new GetFieldsComIn("test1", "test1", "1");
		String correct = "1\n" + 
				"1\n" +
				"Last Name\n" +
				"1\n" +
				"2\n" +
				"First Name\n" +
				"1\n" +
				"3\n" +
				"Gender\n" +
				"1\n" +
				"4\n" +
				"Age\n";
		String result = client.getFields(comIn).toString();
		assert(correct.equals(result));
	}
	
	@Test
	public void searchTest()
	{
		String fields = "1,2";
		String values = "fox,russell";
		SearchComIn comIn = new SearchComIn("test1", "test1", parseSearchIDs(fields), parseSearchValues(values));
		String result = client.search(comIn).toString();
		assert(!result.equals("FAILED\n"));
		assert(result.contains(Server.getURL()));
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
