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

public class ImageDAO extends DAO
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
	public ImageDAO(DatabaseRep db)
	{
		super(db);
	}

	/**
	 * 
	 * @param project
	 * @param image
	 * @throws DatabaseException 
	 */
	public void add(ModelImage image) throws DatabaseException
	{
		Connection connection = db.getConnection();
		PreparedStatement pstmt = null;
		ResultSet keyRS = null;
		try
		{
			String command = "INSERT INTO images(file, project_id, checked_to_user) VALUES(?,?,?);";
			pstmt = connection.prepareStatement(command);
			pstmt.setString(1, image.getFileName());
			pstmt.setInt(2, image.getProjectId());
			pstmt.setInt(3, image.getCheckedToUser());
			
			if (pstmt.executeUpdate() == 1) 
			{
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				image.setId(id);
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
	 * @param project
	 * @param image
	 * @return the image in Project project whose title matches image
	 * @throws DatabaseException 
	 */
	public ModelImage get(String imageFileName) throws DatabaseException
	{
		logger.entering("server.database.Contacts", "get");
		ModelImage result = null;
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			String query = "select * from images where file = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, imageFileName);
			
			rs = stmt.executeQuery();
			
			int id = rs.getInt(1);
			String file = rs.getString(2);
			int project_id = rs.getInt(3);
			int checked_to_user = rs.getInt(4);

			result = new ModelImage(id, file, project_id, checked_to_user);
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
	
	public ModelImage get(int inputid) throws DatabaseException
	{
		logger.entering("server.database.Contacts", "get");
		ModelImage result = null;
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			String query = "select * from images where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, inputid);
			
			rs = stmt.executeQuery();
			
			int id = rs.getInt(1);
			String file = rs.getString(2);
			int project_id = rs.getInt(3);
			int checked_to_user = rs.getInt(4);

			result = new ModelImage(id, file, project_id, checked_to_user);
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
	 * @param project
	 * @return all images associated with the project
	 * @throws DatabaseException 
	 */
	public List<ModelImage> getAll() throws DatabaseException
	{
		logger.entering("server.database.Contacts", "getAll");
		
		ArrayList<ModelImage> result = new ArrayList<ModelImage>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			//String query = "select id, username, password, firstname, lastname, email, indexedrecords, current_image from users";
			String query = "select * from images";
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			while (rs.next()) 
			{
				int id = rs.getInt(1);
				String file = rs.getString(2);
				int project_id = rs.getInt(3);
				int checked_to_user = rs.getInt(4);

				result.add(new ModelImage(id, file, project_id, checked_to_user));
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
	 * @param project
	 * @return
	 * @throws DatabaseException
	 */
	public List<ModelImage> getAll(ModelProject project) throws DatabaseException
	{
		logger.entering("server.database.Contacts", "getAll");
		
		ArrayList<ModelImage> result = new ArrayList<ModelImage>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int inputId = project.getId();
		try 
		{
			//String query = "select id, username, password, firstname, lastname, email, indexedrecords, current_image from users";
			String query = "select * from images where project_id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, inputId);

			rs = stmt.executeQuery();
			while (rs.next()) 
			{
				int id = rs.getInt(1);
				String file = rs.getString(2);
				int project_id = rs.getInt(3);
				int checked_to_user = rs.getInt(4);

				result.add(new ModelImage(id, file, project_id, checked_to_user));
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
	public List<ModelImage> getAll(String projectName) throws DatabaseException
	{
		ModelProject project = db.getProjectDAO().get(projectName);
		return getAll(project);
	}
	
	public ModelImage downloadBatch(String username, int projectID) throws DatabaseException
	{
		logger.entering("DAO.imageDAO", "downloadBatch");
		ModelImage result = null;
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			String query = "select * from images where project_id = ? and checked_to_user = 0;";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, projectID);
			
			rs = stmt.executeQuery();
			
			int id = rs.getInt(1);
			result = get(id);
			//result.setCheckedToUser(db.getUserDAO().get(username).getId());
			//update(result);
		}
		catch (SQLException e) 
		{
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			
			logger.throwing("server.database.Contacts", "get", serverEx);
			
			throw serverEx;
		}		
		finally {
			DatabaseRep.safeClose(rs);
			DatabaseRep.safeClose(stmt);
		}

		logger.exiting("server.database.Contacts", "get");
		
		return result;	
	}
	
	/**
	 * Replaces the image labeled title in project with image
	 * @param project
	 * @param title
	 * @throws DatabaseException 
	 */
	public void update(ModelImage image) throws DatabaseException
	{
		PreparedStatement stmt = null;
		try 
		{
			String query = "update images set file = ?, project_id = ?, checked_to_user = ? where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, image.getFileName());
			stmt.setInt(2, image.getProjectId());
			stmt.setInt(3, image.getCheckedToUser());
			stmt.setInt(4, image.getId());
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
	 * @param deleteImage
	 * @throws DatabaseException
	 */
	public void delete(ModelImage deleteImage) throws DatabaseException 
	{
		PreparedStatement stmt = null;
		try 
		{
			String query = "delete from images where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, deleteImage.getId());
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
		List<ModelImage> toDelete = db.getImageDAO().getAll();
		for(ModelImage delete : toDelete)
		{
			db.getImageDAO().delete(delete);
		}
	}

}
