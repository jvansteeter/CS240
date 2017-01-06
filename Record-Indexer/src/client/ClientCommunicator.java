package client;

import java.io.*;
import java.net.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import communication.*;

/**
 * 
 * @author jvanstee
 *
 */
public class ClientCommunicator 
{
	private String urlBase;
	
	/**
	 * Empty constructor
	 */
	public ClientCommunicator(String hostName, int portNumber)
	{
		urlBase = "http://" + hostName + ":" + portNumber;
	}
	
	/**
	 * Validates a user with a server		
	 * @param comIn ValidateUserComIn object
	 * @return ValidateUserComOut object
	 */
	public ValidateUserComOut validateUser(ValidateUserComIn comIn)
	{
		return (ValidateUserComOut) post("/ValidateUser", comIn);
	}
	
	/**
	 * Gets all current projects information
	 * @param comIn GetProjectsComIn object
	 * @return GetProjectsComOut object
	 */
	public GetProjectsComOut getProjects(GetProjectsComIn comIn)
	{
		return (GetProjectsComOut) post("/GetProjects", comIn);
	}
	
	/**
	 * Gets the url for a sample image
	 * @param comIn GetSampleImageComIn object
	 * @return GetSampleImageComOut object
	 */
	public GetSampleImageComOut getSampleImage(GetSampleImageComIn comIn)
	{
		return (GetSampleImageComOut) post("/GetSampleImage", comIn);
	}
	
	/**
	 * Downloads all information on a given batch
	 * @param comIn DownloadBatchComIn object
	 * @return DownloadBatchComOut object
	 */
	public DownloadBatchComOut downloadBatch(DownloadBatchComIn comIn)
	{
		return (DownloadBatchComOut) post("/DownloadBatch", comIn);
	}
	
	/**
	 * Submits the indexed record field values for a batch to the Server
	 * @param comIn SubmitBatchComIn object
	 * @return SubmitBatchComOut object
	 */
	public SubmitBatchComOut submitBatch(SubmitBatchComIn comIn)
	{
		return (SubmitBatchComOut) post("/SubmitBatch", comIn);
	}
	
	/**
	 * Returns information about all of the fields for the specified project
	 * If no project is specified, returns information about all of the fields for all projects in the systemINPUTS
	 * @param comIn GetFieldsComIn object
	 * @return GetFieldsComOut object
	 */
	public GetFieldsComOut getFields(GetFieldsComIn comIn)
	{
		return (GetFieldsComOut) post("/GetFields", comIn);
	}
	
	/**
	 * Searches the indexed records for the specified strings
	 * @param comIn SearchComIn object
	 * @return SearchComOut object
	 */
	public SearchComOut search(SearchComIn comIn)
	{
		return (SearchComOut) post("/Search", comIn);
	}
	
	public byte[] downloadFile(String fileURL)
	{
		URL url;
		try 
		{
			url = new URL(fileURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(10000);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.connect();
			
			conn.getOutputStream().close();
			
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
			{
				InputStream inFile = new BufferedInputStream(conn.getInputStream());
				ObjectInputStream input = new ObjectInputStream(inFile);
				byte[] result = (byte[])input.readObject();
				return result;
			}
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		} 
		catch (ProtocolException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	private Object post(String urlPath, Object data)
	{
		XStream xstream = new XStream(new DomDriver());
		URL url;
		try 
		{
			url = new URL(urlBase + urlPath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(10000);
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.connect();
			
			xstream.toXML(data, conn.getOutputStream());
			conn.getOutputStream().close();
			
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
			{
				InputStreamReader ipr = new InputStreamReader(conn.getInputStream());
				System.out.println("start IPR");
				while(ipr.ready())
				{
					int iprOut = ipr.read();
					char[] iprOutChat = Character.toChars(iprOut);
					System.out.print(iprOutChat);
				}
				System.out.println("\nend IPR");
				
				Object result = xstream.fromXML(conn.getInputStream());
				return result;
			}
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		} 
		catch (ProtocolException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return null;
	}

}
