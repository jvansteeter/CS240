package DAO;

import server.*;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Logger;

public class ValueDAO extends DAO
{
	private static Logger logger;
	static 
	{
		logger = Logger.getLogger("contactmanager");
	}
	
	/**
	 * 
	 * @param db
	 */
	public ValueDAO(DatabaseRep db)
	{
		super(db);
	}
	
	/**
	 * 
	 * @param value
	 * @throws DatabaseException 
	 */
	public void add(ModelValue value) throws DatabaseException
	{
		Connection connection = db.getConnection();
		PreparedStatement pstmt = null;
		ResultSet keyRS = null;
		try
		{
			String command = "INSERT INTO value(value, field, row, image_id) VALUES(?,?,?,?);";
			pstmt = connection.prepareStatement(command);
			pstmt.setString(1, value.getValue());
			pstmt.setString(2, value.getField());
			pstmt.setInt(3, value.getRow());
			pstmt.setInt(4, value.getImageId());
			
			if (pstmt.executeUpdate() == 1) 
			{
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				value.setId(id);
			}
			else 
			{
				throw new DatabaseException("Could not insert contact");
			}
		}
		catch (SQLException e) 
		{
			throw new DatabaseException("Could not insert contact", e);
		}
		finally 
		{
			DatabaseRep.safeClose(pstmt);
			DatabaseRep.safeClose(keyRS);
		}
	}
	
	/**
	 * 
	 * @param values
	 * @throws DatabaseException
	 */
	public void addAll(List<ModelValue> values) throws DatabaseException
	{
		for (ModelValue toAdd : values)
		{
			add(toAdd);
		}
	}
	
	/**
	 * 
	 * @param inputField
	 * @param inputRow
	 * @param inputImageId
	 * @return
	 * @throws DatabaseException
	 */
	public ModelValue get(String inputField, int inputRow, int inputImageId) throws DatabaseException
	{
		logger.entering("server.database.Contacts", "get");
		ModelValue result = null;
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			String query = "select * from value where field = ? and row = ? and image_id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, inputField);
			stmt.setInt(2, inputRow);
			stmt.setInt(3, inputImageId);
			
			rs = stmt.executeQuery();
			
			int id = rs.getInt(1);
			String value = rs.getString(2);
			String field = rs.getString(3);
			int row = rs.getInt(4);
			int imageId = rs.getInt(5);

			result = new ModelValue(id, value, field, row, imageId);
		}
		catch (SQLException e) 
		{
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.Contacts", "get", serverEx);
			
			throw serverEx;
		}		
		finally 
		{
			DatabaseRep.safeClose(rs);
			DatabaseRep.safeClose(stmt);
		}

		logger.exiting("server.database.Contacts", "get");
		return result;	
	}
	
	public List<ModelValue> get(String fieldTitle, String inputValue) throws DatabaseException
	{
		logger.entering("server.database.Contacts", "get");
		List<ModelValue> result = new ArrayList<ModelValue>();
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			String query = "select * from value where field = ? and value = ? collate NOCASE";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, fieldTitle);
			stmt.setString(2, inputValue);
			
			rs = stmt.executeQuery();
			
			while (rs.next())
			{
				int id = rs.getInt(1);
				String value = rs.getString(2);
				String field = rs.getString(3);
				int row = rs.getInt(4);
				int imageId = rs.getInt(5);
	
				result.add(new ModelValue(id, value, field, row, imageId));
			}
		}
		catch (SQLException e) 
		{
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.Contacts", "get", serverEx);
			
			throw serverEx;
		}		
		finally 
		{
			DatabaseRep.safeClose(rs);
			DatabaseRep.safeClose(stmt);
		}

		logger.exiting("server.database.Contacts", "get");
		return result;	
	}
	
	/**
	 * 
	 * @param imageFile
	 * @return
	 * @throws DatabaseException 
	 */
	public List<ModelValue> getAll() throws DatabaseException
	{
		logger.entering("server.database.Contacts", "getAll");
		
		List<ModelValue> result = new ArrayList<ModelValue>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			//String query = "select id, username, password, firstname, lastname, email, indexedrecords, current_image from users";
			String query = "select * from value";
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			while (rs.next()) 
			{
				int id = rs.getInt(1);
				String value = rs.getString(2);
				String field = rs.getString(3);
				int row = rs.getInt(4);
				int imageId = rs.getInt(5);

				result.add(new ModelValue(id, value, field, row, imageId));
			}
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.Contacts", "getAll", serverEx);
			
			throw serverEx;
		}		
		finally {
			DatabaseRep.safeClose(rs);
			DatabaseRep.safeClose(stmt);
		}

		logger.exiting("server.database.Contacts", "getAll");
		
		return result;	
	}
	
	public List<ModelValue> getAll(ModelImage image) throws DatabaseException
	{
		logger.entering("server.database.Contacts", "getAll");
		
		ArrayList<ModelValue> result = new ArrayList<ModelValue>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int inputId = image.getId();
		try 
		{
			String query = "select * from value where image_id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, inputId);

			rs = stmt.executeQuery();
			while (rs.next()) 
			{
				int id = rs.getInt(1);
				String value = rs.getString(2);
				String field = rs.getString(3);
				int row = rs.getInt(4);
				int imageId = rs.getInt(5);

				result.add(new ModelValue(id, value, field, row, imageId));
			}
		}
		catch (SQLException e) 
		{
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.Contacts", "getAll", serverEx);
			
			throw serverEx;
		}		
		finally 
		{
			DatabaseRep.safeClose(rs);
			DatabaseRep.safeClose(stmt);
		}

		logger.exiting("server.database.Contacts", "getAll");
		
		return result;	
	}
	
	/**
	 * 
	 * @param projectName
	 * @return
	 * @throws DatabaseException
	 */
	public List<ModelValue> getAll(String imageName) throws DatabaseException
	{
		ModelImage project = db.getImageDAO().get(imageName);
		return getAll(project);
	}
	
	public void submitBatch(List<ArrayList<String>> values, int batchID, List<ModelField> fields) throws DatabaseException
	{
		int rowCount = 0;
		for (ArrayList<String> row : values)
		{
			rowCount++;
			for (int i = 0; i < row.size(); i++)
			{
				String value = row.get(i);
				String field = fields.get(i).getTitle();
				ModelValue modelValue = new ModelValue(value, field, rowCount, batchID);
				add(modelValue);
			}
		}
	}
	
	/**
	 * 
	 * @param fieldIDs
	 * @param values
	 * @return
	 * @throws DatabaseException 
	 */
	public List<ModelValue> search(ArrayList<String> fieldTitles, ArrayList<String> values) throws DatabaseException
	{
		List<ModelValue> result = new ArrayList<ModelValue>();
		for (String title : fieldTitles)
		{
			for (String value : values)
			{
				List<ModelValue> temp = get(title,value);
				if (temp != null)
				{		
					result.addAll(temp);
				}
			}
		}
		return result;
	}
	
	/**
	 * Replaces all values for the image whose file name matches imageFile with values
	 * @param imageFile
	 * @param values
	 * @throws DatabaseException 
	 */
	public void update(ModelValue value) throws DatabaseException
	{
		PreparedStatement stmt = null;
		try 
		{
			String query = "update value set value = ?, field = ?, row = ?, image_id = ? where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, value.getValue());
			stmt.setString(2, value.getField());
			stmt.setInt(3, value.getRow());
			stmt.setInt(4, value.getImageId());
			stmt.setInt(5, value.getId());
			if (stmt.executeUpdate() != 1) 
			{
				throw new DatabaseException("Could not update contact");
			}
		}
		catch (SQLException e) 
		{
			throw new DatabaseException("Could not update contact", e);
		}
		finally 
		{
			DatabaseRep.safeClose(stmt);
		}
	}

	/**
	 * 
	 * @param deleteValue
	 * @throws DatabaseException
	 */
	public void delete(ModelValue deleteValue) throws DatabaseException 
	{
		PreparedStatement stmt = null;
		try 
		{
			String query = "delete from value where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, deleteValue.getId());
			if (stmt.executeUpdate() != 1) 
			{
				throw new DatabaseException("Could not delete contact");
			}
		}
		catch (SQLException e) 
		{
			throw new DatabaseException("Could not delete contact", e);
		}
		finally 
		{
			DatabaseRep.safeClose(stmt);
		}
	}
	
	/**
	 * 
	 * @throws DatabaseException
	 */
	public void deleteAll() throws DatabaseException
	{
		List<ModelValue> toDelete = db.getValueDAO().getAll();
		for(ModelValue delete : toDelete)
		{
			db.getValueDAO().delete(delete);
		}
	}

}
