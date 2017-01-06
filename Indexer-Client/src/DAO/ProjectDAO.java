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

public class ProjectDAO extends DAO
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
	public ProjectDAO(DatabaseRep db)
	{
		super(db);
	}

	/**
	 * Inserts project into the projects table
	 * @param project
	 * @throws DatabaseException 
	 */
	public void add(ModelProject project) throws DatabaseException
	{
		Connection connection = db.getConnection();
		PreparedStatement pstmt = null;
		ResultSet keyRS = null;
		try
		{
			String command = "INSERT INTO projects(title, recordsperimage, firstYcoord, recordheight) VALUES(?,?,?,?);";
			pstmt = connection.prepareStatement(command);
			pstmt.setString(1, project.getTitle());
			pstmt.setInt(2, project.getRecordsPerImage());
			pstmt.setInt(3, project.getFirstYCoord());
			pstmt.setInt(4, project.getRecordHeight());
			
			if (pstmt.executeUpdate() == 1) 
			{
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				project.setId(id);
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
	 * @param projectTitle
	 * @return the ModelProject whose title matches projectTitle
	 * @throws DatabaseException 
	 */
	public ModelProject get(String inputTitle) throws DatabaseException
	{
		logger.entering("server.database.Contacts", "getAll");
		ModelProject result = null;
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String query = "select * from projects where title = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, inputTitle);
			
			rs = stmt.executeQuery();
			
			int id = rs.getInt(1);
			String title = rs.getString(2);
			int recordsPerImage = rs.getInt(3);
			int firstYcoord = rs.getInt(4);
			int recordHeight = rs.getInt(5);

			result = new ModelProject(id, title, recordsPerImage, firstYcoord, recordHeight);
			
		}
		catch (SQLException e) {
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
	 * 
	 * @param projectID
	 * @return
	 * @throws DatabaseException
	 */
	public ModelProject get(int projectID) throws DatabaseException
	{
		logger.entering("server.database.Contacts", "get(projectID)");
		ModelProject result = null;
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			String query = "select * from projects where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, projectID);
			
			rs = stmt.executeQuery();
			
			int id = rs.getInt(1);
			String title = rs.getString(2);
			int recordsPerImage = rs.getInt(3);
			int firstYcoord = rs.getInt(4);
			int recordHeight = rs.getInt(5);

			result = new ModelProject(id, title, recordsPerImage, firstYcoord, recordHeight);
		}
		catch (SQLException e) {
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
	
	public List<ModelProject> getAll() throws DatabaseException
	{
		logger.entering("server.database.Contacts", "getAll");
		
		ArrayList<ModelProject> result = new ArrayList<ModelProject>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			//String query = "select id, username, password, firstname, lastname, email, indexedrecords, current_image from users";
			String query = "select * from projects";
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String title = rs.getString(2);
				int recordsPerImage = rs.getInt(3);
				int firstYcoord = rs.getInt(4);
				int recordHeight = rs.getInt(5);

				result.add(new ModelProject(id, title, recordsPerImage, firstYcoord, recordHeight));
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
	 * Replaces the project whose title matches projectTitle with project
	 * @param projectTitle
	 * @param project
	 * @throws DatabaseException 
	 */
	public void update(ModelProject project) throws DatabaseException
	{
		PreparedStatement stmt = null;
		try {
			String query = "update projects set title = ?, recordsperimage = ?, firstYcoord = ?, recordheight = ? where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, project.getTitle());
			stmt.setInt(2, project.getRecordsPerImage());
			stmt.setInt(3, project.getFirstYCoord());
			stmt.setInt(4, project.getRecordHeight());
			stmt.setInt(5, project.getId());
			if (stmt.executeUpdate() != 1) {
				throw new DatabaseException("Could not update contact");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not update contact", e);
		}
		finally {
			DatabaseRep.safeClose(stmt);
		}
	}

	/**
	 * 
	 * @param obj
	 * @throws DatabaseException 
	 */
	public void delete(ModelProject deleteProject) throws DatabaseException 
	{
		PreparedStatement stmt = null;
		try {
			String query = "delete from projects where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, deleteProject.getId());
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
		List<ModelProject> toDelete = db.getProjectDAO().getAll();
		for(ModelProject delete : toDelete)
		{
			db.getProjectDAO().delete(delete);
		}
	}
}
