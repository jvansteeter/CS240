package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Logger;

import server.*;
import model.*;

public class FieldDAO extends DAO
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
	public FieldDAO(DatabaseRep db)
	{
		super(db);
	}
	
	/**
	 * inserts the field object into the fields table
	 * @param project
	 * @param field
	 * @throws DatabaseException 
	 */
	public void add(ModelField field) throws DatabaseException
	{
		Connection connection = db.getConnection();
		PreparedStatement pstmt = null;
		ResultSet keyRS = null;
		try
		{
			String command = "INSERT INTO fields(title, xcoord, width, relative_position, helphtml, knowndata, project_id) VALUES(?,?,?,?,?,?,?);";
			pstmt = connection.prepareStatement(command);
			pstmt.setString(1, field.getTitle());
			pstmt.setInt(2, field.getXCoord());
			pstmt.setInt(3, field.getWidth());
			pstmt.setInt(4, field.getPosition());
			pstmt.setString(5, field.getHelpHTML());
			pstmt.setString(6, field.getKnownData());
			pstmt.setInt(7, field.getProjectId());
			
			if (pstmt.executeUpdate() == 1) 
			{
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				field.setId(id);
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
	
	public ModelField get(int fieldID) throws DatabaseException
	{
		logger.entering("server.database.Contacts", "getAll");
		
		ModelField result = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select * from fields where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, fieldID);

			rs = stmt.executeQuery();
			while (rs.next()) 
			{
				int id = rs.getInt(1);
				String title = rs.getString(2);
				int xcoord = rs.getInt(3);
				int width = rs.getInt(4);
				int position = rs.getInt(5);
				String helphtml = rs.getString(6);
				String knowndata = rs.getString(7);
				int projectId = rs.getInt(8);

				result = new ModelField(id, title, xcoord, width, position, helphtml, knowndata, projectId);
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
	
	/**
	 * 
	 * @param fieldTitle
	 * @return
	 * @throws DatabaseException
	 *
	private List<ModelField> get(String fieldTitle) throws DatabaseException
	{
		logger.entering("server.database.Contacts", "getAll");
		
		ArrayList<ModelField> result = new ArrayList<ModelField>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select * from fields where title = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, fieldTitle);

			rs = stmt.executeQuery();
			while (rs.next()) 
			{
				int id = rs.getInt(1);
				String title = rs.getString(2);
				int xcoord = rs.getInt(3);
				int width = rs.getInt(4);
				int position = rs.getInt(5);
				String helphtml = rs.getString(6);
				String knowndata = rs.getString(7);
				int projectId = rs.getInt(8);

				result.add(new ModelField(id, title, xcoord, width, position, helphtml, knowndata, projectId));
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
	}*/
	
	/**
	 * 
	 * @param inputTitle
	 * @return the field whose title matches inputTitle
	 * @throws DatabaseException 
	 */
	public ModelField get(String inputTitle, int inputProjectId) throws DatabaseException
	{
		logger.entering("server.database.Contacts", "get");
		ModelField result = null;
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			String query = "select * from fields where title = ? and project_id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, inputTitle);
			stmt.setInt(2, inputProjectId);
			
			rs = stmt.executeQuery();
			
			int id = rs.getInt(1);
			String title = rs.getString(2);
			int xcoord = rs.getInt(3);
			int width = rs.getInt(4);
			int position = rs.getInt(5);
			String helphtml = rs.getString(6);
			String knowndata = rs.getString(7);
			int projectId = rs.getInt(8);

			result = new ModelField(id, title, xcoord, width, position, helphtml, knowndata, projectId);
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
	 * @return
	 * @throws DatabaseException 
	 */
	public List<ModelField> getAll() throws DatabaseException
	{
		logger.entering("server.database.Contacts", "getAll");
		
		ArrayList<ModelField> result = new ArrayList<ModelField>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			//String query = "select id, username, password, firstname, lastname, email, indexedrecords, current_image from users";
			String query = "select * from fields";
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			while (rs.next()) 
			{
				int id = rs.getInt(1);
				String title = rs.getString(2);
				int xcoord = rs.getInt(3);
				int width = rs.getInt(4);
				int position = rs.getInt(5);
				String helphtml = rs.getString(6);
				String knowndata = rs.getString(7);
				int projectId = rs.getInt(8);

				result.add(new ModelField(id, title, xcoord, width, position, helphtml, knowndata, projectId));
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
	
	/**
	 * 
	 * @param projectName
	 * @return all fields in the table assigned to the project
	 * @throws DatabaseException 
	 */
	public List<ModelField> getAll(String projectName) throws DatabaseException
	{
		ModelProject project = db.getProjectDAO().get(projectName);
		return getAll(project);
	}
	
	/**
	 * 
	 * @param project
	 * @return all fields in the table assigned to the project
	 * @throws DatabaseException 
	 */
	public List<ModelField> getAll(ModelProject project) throws DatabaseException
	{
		logger.entering("server.database.Contacts", "getAll");
		
		ArrayList<ModelField> result = new ArrayList<ModelField>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int inputId = project.getId();
		try 
		{
			//String query = "select id, username, password, firstname, lastname, email, indexedrecords, current_image from users";
			String query = "select * from fields where project_id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, inputId);

			rs = stmt.executeQuery();
			while (rs.next()) 
			{
				int id = rs.getInt(1);
				String title = rs.getString(2);
				int xcoord = rs.getInt(3);
				int width = rs.getInt(4);
				int position = rs.getInt(5);
				String helphtml = rs.getString(6);
				String knowndata = rs.getString(7);
				int projectId = rs.getInt(8);

				result.add(new ModelField(id, title, xcoord, width, position, helphtml, knowndata, projectId));
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
	 * @param fieldIDs
	 * @return
	 */
	public ArrayList<String> getFieldValues(ArrayList<Integer> fieldIDs)
	{
		ArrayList<String> fieldValues = new ArrayList<String>();
		try 
		{
			for (int toFind : fieldIDs)
			{
				ModelField field = get(toFind);
				if (field != null)
				{
					fieldValues.add(field.getTitle());
				}
			}
		} 
		catch (DatabaseException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fieldValues;
	}
	
	/**
	 * Replaces the field whos title matches oldFieldTitle with newField
	 * @param project
	 * @param oldFieldTitle
	 * @param newField
	 * @throws DatabaseException 
	 */
	public void update(ModelField field) throws DatabaseException
	{
		PreparedStatement stmt = null;
		try 
		{
			String query = "update fields set title = ?, xcoord = ?, width = ?, helphtml = ?, knowndata = ?, project_id = ? where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, field.getTitle());
			stmt.setInt(2, field.getXCoord());
			stmt.setInt(3, field.getWidth());
			stmt.setString(4, field.getHelpHTML());
			stmt.setString(5, field.getKnownData());
			stmt.setInt(6, field.getProjectId());
			stmt.setInt(7, field.getId());
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
	 * @param obj
	 * @return
	 * @throws DatabaseException 
	 */
	public void delete(ModelField deleteField) throws DatabaseException 
	{
		PreparedStatement stmt = null;
		try 
		{
			String query = "delete from fields where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, deleteField.getId());
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
		List<ModelField> toDelete = db.getFieldDAO().getAll();
		for(ModelField delete : toDelete)
		{
			db.getFieldDAO().delete(delete);
		}
	}

}
