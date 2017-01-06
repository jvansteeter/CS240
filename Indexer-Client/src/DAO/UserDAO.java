package DAO;

import server.*;
import model.*;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

public class UserDAO extends DAO
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
	public UserDAO(DatabaseRep db)
	{
		super(db);
	}

	/**
	 * 
	 * @param user
	 */
	public void add(ModelUser user) throws DatabaseException
	{
		Connection connection = db.getConnection();
		PreparedStatement pstmt = null;
		ResultSet keyRS = null;
		try
		{
			String command = "INSERT INTO users(username,password,firstname,lastname,email,indexedrecords,currentbatch) VALUES(?,?,?,?,?,?,?);";
			pstmt = connection.prepareStatement(command);
			pstmt.setString(1, user.getUserName());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getFirstName());
			pstmt.setString(4, user.getLastName());
			pstmt.setString(5, user.getEmail());
			pstmt.setString(6, "" + user.getIndexedRecords());
			pstmt.setInt(7, user.getCurrentBatch());
			if (pstmt.executeUpdate() == 1) 
			{
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				user.setId(id);
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
	 * @param username
	 * @return
	 * @throws DatabaseException 
	 */
	public ModelUser get(String inputUsername) throws DatabaseException
	{
		logger.entering("server.database.Contacts", "getAll");
		ModelUser result = null;
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			String query = "select * from users where username = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, inputUsername);
			
			rs = stmt.executeQuery();
			
			if (rs.next())
			{
				int id = rs.getInt(1);
				String username = rs.getString(2);
				String password = rs.getString(3);
				String firstname = rs.getString(4);
				String lastname = rs.getString(5);
				String email = rs.getString(6);
				int indexedrecords = rs.getInt(7);
				int currentbatch = rs.getInt(8);
	
				result = new ModelUser(id, username, password, firstname, lastname, email, indexedrecords, currentbatch);
			}
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
	 * @return
	 * @throws DatabaseException
	 */
	public List<ModelUser> getAll() throws DatabaseException
	{
		logger.entering("server.database.Contacts", "getAll");
		
		ArrayList<ModelUser> result = new ArrayList<ModelUser>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			//String query = "select id, username, password, firstname, lastname, email, indexedrecords, current_image from users";
			String query = "select * from users";
			stmt = db.getConnection().prepareStatement(query);

			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String username = rs.getString(2);
				String password = rs.getString(3);
				String firstname = rs.getString(4);
				String lastname = rs.getString(5);
				String email = rs.getString(6);
				int indexedrecords = rs.getInt(7);
				int currentbatch = rs.getInt(8);

				result.add(new ModelUser(id, username, password, firstname, lastname, email, indexedrecords, currentbatch));
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
	 * Replaces the user whose username matches username with ModelUser user
	 * @param username
	 * @param user
	 * @throws DatabaseException 
	 */
	public void update(ModelUser user) throws DatabaseException
	{
		PreparedStatement stmt = null;
		try 
		{
			String query = "update users set username = ?, password = ?, firstname = ?, lastname = ?, email = ?, indexedrecords = ?, currentbatch = ? where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, user.getUserName());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getFirstName());
			stmt.setString(4, user.getLastName());
			stmt.setString(5, user.getEmail());
			stmt.setInt(6, user.getIndexedRecords());
			stmt.setInt(7, user.getCurrentBatch());
			stmt.setInt(8, user.getId());
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
	 * @return
	 * @throws DatabaseException 
	 */
	public void delete(ModelUser deleteUser) throws DatabaseException 
	{
		PreparedStatement stmt = null;
		try {
			String query = "delete from users where id = ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, deleteUser.getId());
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
		List<ModelUser> toDelete = db.getUserDAO().getAll();
		for(ModelUser delete : toDelete)
		{
			db.getUserDAO().delete(delete);
		}
	}
}
