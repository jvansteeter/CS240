package server;

import model.*;
import java.util.*;
import communication.*;

public class ServerFacade 
{
	/**
	 * Initialize the Database drivers
	 * @throws ServerException
	 */
	public static void initialize() throws ServerException 
	{		
		try 
		{
			DatabaseRep.initialize();		
		}
		catch (DatabaseException e) 
		{
			throw new ServerException(e.getMessage(), e);
		}		
	}
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws ServerException 
	 */
	private static boolean validateUser(String username, String password) throws ServerException
	{
		DatabaseRep db = new DatabaseRep();
		boolean result = false;
		try 
		{
			db.startTransaction();
			ModelUser user = db.getUserDAO().get(username);
			if (user != null)
			{
				if (password.equals(user.getPassword()))
				{
					result = true;
				}
			}
		} 
		catch (DatabaseException e) 
		{
			e.printStackTrace();
			throw new ServerException(e.getMessage(), e);
		}
		finally
		{
			db.endTransaction(true);
		}
		
		return result;
	}
	
	public static ValidateUserComOut validateUser(ValidateUserComIn params) throws ServerException
	{
		DatabaseRep db = new DatabaseRep();
		ValidateUserComOut result = new ValidateUserComOut(false);
		
		String username = params.getUsername();
		String password = params.getPassword();
		
		if (validateUser(username, password))
		{
			try 
			{
				db.startTransaction();
				result.setValid(true);
				
				ModelUser user = db.getUserDAO().get(username);
				result.setFirstName(user.getFirstName());
				result.setLastName(user.getLastName());
				result.setNumRecords(user.getIndexedRecords());
			} 
			catch (DatabaseException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new ServerException(e.getMessage(), e);
			}
			finally
			{
				db.endTransaction(true);
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws ServerException 
	 */
	public static GetProjectsComOut getProjects(GetProjectsComIn params) throws ServerException
	{
		DatabaseRep db = new DatabaseRep();
		GetProjectsComOut result = null;
		
		String username = params.getUsername();
		String password = params.getPassword();
		if (validateUser(username, password))
		{
			try 
			{
				db.startTransaction();
				List<ModelProject> projects = db.getProjectDAO().getAll();
				result = new GetProjectsComOut(projects);
			} 
			catch (DatabaseException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new ServerException(e.getMessage(), e);
			}
			finally
			{
				db.endTransaction(true);
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @param projectID
	 * @return
	 * @throws ServerException 
	 */
	public static GetSampleImageComOut getSampleImage(GetSampleImageComIn params) throws ServerException
	{
		DatabaseRep db = new DatabaseRep();
		GetSampleImageComOut result = null;
		
		String username = params.getUsername();
		String password = params.getPassword();
		int projectID = params.getProjectID();
		
		if (validateUser(username, password))
		{
			try 
			{
				db.startTransaction();
				ModelProject project = db.getProjectDAO().get(projectID);
				List<ModelImage> images = db.getImageDAO().getAll(project);
				
				Random rand = new Random();
				int randomInt = rand.nextInt(images.size() + 1);
				ModelImage sampleImage = images.get(randomInt);
				result = new GetSampleImageComOut(Server.getURL(), sampleImage.getFileName());
			} 
			catch (DatabaseException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new ServerException(e.getMessage(), e);
			}
			finally
			{
				db.endTransaction(true);
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @param projectID
	 * @return
	 * @throws ServerException 
	 */
	public static DownloadBatchComOut downloadBatch(DownloadBatchComIn params) throws ServerException
	{
		DatabaseRep db = new DatabaseRep();
		DownloadBatchComOut result = null;
		
		String username = params.getUsername();
		String password = params.getPassword();
		int projectID = params.getProjectID();
		
		if (validateUser(username, password))
		{
			try 
			{
				db.startTransaction();
				ModelImage image = db.getImageDAO().downloadBatch(username, projectID);
				ModelProject project = db.getProjectDAO().get(projectID);
				ModelUser user = db.getUserDAO().get(username);
				
				if (user.getCurrentBatch() == 0)
				{
					int batchID = image.getId();
					String imageURL = Server.getURL() + "/" + image.getFileName();
					int firstYcoord = project.getFirstYCoord();
					int recordHeight = project.getRecordHeight();
					int numRecords = project.getRecordsPerImage();
					List<ModelField> allFields = db.getFieldDAO().getAll(project);
					int numFields = allFields.size();
					
					user.setCurrentBatch(batchID);
					db.getUserDAO().update(user);
					image.setCheckedToUser(user.getId());
					db.getImageDAO().update(image);
					
					result = new DownloadBatchComOut(batchID, projectID, imageURL, firstYcoord, recordHeight, numRecords, numFields, allFields, Server.getURL());
				}
			} 
			catch (DatabaseException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new ServerException(e.getMessage(), e);
			}
			finally
			{
				db.endTransaction(true);
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @param batchID
	 * @param values
	 * @return
	 * @throws ServerException 
	 */
	public static SubmitBatchComOut submitBatch(SubmitBatchComIn params) throws ServerException
	{
		DatabaseRep db = new DatabaseRep();
		SubmitBatchComOut result = new SubmitBatchComOut(false);
		
		String username = params.getUsername();
		String password = params.getPassword();
		int batchID = params.getBatchID();
//		List<ModelField> fields = params.getFields();
		List<ArrayList<String>> values = params.getValues();
		
		if (validateUser(username, password))
		{
			try 
			{
				db.startTransaction();
				ModelImage batch = db.getImageDAO().get(batchID);
				ModelProject project = db.getProjectDAO().get(batch.getProjectId());
				ModelUser user = db.getUserDAO().get(username);
				int numberOfFields = db.getFieldDAO().getAll(project).size();
				
				if(user.getCurrentBatch() != 0 && user.getCurrentBatch() == batchID && validNumberOfEntries(numberOfFields, values))
				{
					List<ModelField> fields = db.getFieldDAO().getAll(project);
					db.getValueDAO().submitBatch(values, batchID, fields);
					batch.setCheckedToUser(-1);
					db.getImageDAO().update(batch);
					user.setIndexedRecords(user.getIndexedRecords() + values.size());
					user.setCurrentBatch(0);
					db.getUserDAO().update(user);
					result.setValid(true);
				}
			} 
			catch (DatabaseException e) 
			{
				e.printStackTrace();
				throw new ServerException(e.getMessage(), e);
			}
			finally
			{
				db.endTransaction(true);
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @param projectID
	 * @return
	 * @throws ServerException 
	 */
	public static GetFieldsComOut getFields(GetFieldsComIn params) throws ServerException
	{
		DatabaseRep db = new DatabaseRep();
		GetFieldsComOut result = null;
		
		String username = params.getUsername();
		String password = params.getPassword();
		String projectID = params.getProjectID();
		
		if (validateUser(username, password))
		{
			try 
			{
				db.startTransaction();
				if (!projectID.isEmpty())
				{
					ModelProject project = db.getProjectDAO().get(Integer.parseInt(projectID));
					List<ModelField> fields = db.getFieldDAO().getAll(project);
					result = new GetFieldsComOut(fields);
				}
				else
				{
					List<ModelField> fields = db.getFieldDAO().getAll();
					result = new GetFieldsComOut(fields);
				}
			} 
			catch (DatabaseException e) 
			{
				e.printStackTrace();
				throw new ServerException(e.getMessage(), e);
			}
			finally
			{
				db.endTransaction(true);
			}
		}
		return result;
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @param fieldIDs
	 * @param values
	 * @return
	 * @throws ServerException 
	 */
	public static SearchComOut search(SearchComIn params) throws ServerException
	{
		DatabaseRep db = new DatabaseRep();
		SearchComOut result = null;
		
		String username = params.getUsername();
		String password = params.getPassword();
		ArrayList<String> fieldIDStrings = params.getFields();
		ArrayList<String> values = params.getValues();
		
		ArrayList<Integer> fieldIDs = new ArrayList<Integer>();
		for (String id : fieldIDStrings)
		{
			fieldIDs.add(Integer.parseInt(id));
		}
		
//		System.out.println(fieldIDs);
//		System.out.println(values);
		
		if (validateUser(username, password))
		{
			try 
			{
				db.startTransaction();
				ArrayList<String> fieldValues = db.getFieldDAO().getFieldValues(fieldIDs);
				
				List<ModelValue> modelValues = db.getValueDAO().search(fieldValues, values);

				ArrayList<SearchTuple> tuples = new ArrayList<SearchTuple>();
				for (ModelValue modelValue : modelValues)
				{
					if (modelValue != null)
					{
						int batchID = modelValue.getImageId();
						String imageURL = Server.getURL() + "/" + db.getImageDAO().get(batchID).getFileName();
						int recordNumber = modelValue.getRow();
						//int fieldID = modelValue.getId();
						int fieldID = db.getFieldDAO().get(modelValue.getField(), db.getImageDAO().get(batchID).getProjectId()).getId();
						
						SearchTuple toAdd = new SearchTuple(batchID, imageURL, recordNumber, fieldID);
						tuples.add(toAdd);
					}
				}
				
				result = new SearchComOut(tuples);
			} 
			catch (DatabaseException e) 
			{
				e.printStackTrace();
				throw new ServerException(e.getMessage(), e);
			}
			finally
			{
				db.endTransaction(true);
			}
		}
		return result;
	}
	
	private static boolean validNumberOfEntries(int validNumber, List<ArrayList<String>> values)
	{
		boolean valid = true;
		for (ArrayList<String> value : values)
		{
			if (value.size() != validNumber)
			{
				return false;
			}
		}
		
		
		return valid;
	}
}
