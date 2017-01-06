package server;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;
import DAO.*;


public class DatabaseRep 
{
	private static final String DATABASE_DIRECTORY = "database";
	private static final String DATABASE_FILE = "database.sqlite";
	private static final String DATABASE_URL = "jdbc:sqlite:" + DATABASE_DIRECTORY +
												File.separator + DATABASE_FILE;

	private static Logger logger;
	
	static 
	{
		logger = Logger.getLogger("Record-Indexer");
	}

	public static void initialize() throws DatabaseException 
	{
		try 
		{
			final String driver = "org.sqlite.JDBC";
			Class.forName(driver);
		}
		catch(ClassNotFoundException e) 
		{
			
			DatabaseException serverEx = new DatabaseException("Could not load database driver", e);
			
			logger.throwing("server.database.Database", "initialize", serverEx);

			throw serverEx; 
		}
	}

	private UserDAO userDAO;
	private ProjectDAO projectDAO;
	private ImageDAO imageDAO;
	private FieldDAO fieldDAO;
	private ValueDAO valueDAO;
	private Connection connection;
	
	public DatabaseRep() 
	{
		userDAO = new UserDAO(this);
		projectDAO = new ProjectDAO(this);
		imageDAO = new ImageDAO(this);
		fieldDAO = new FieldDAO(this);
		valueDAO = new ValueDAO(this);
		connection = null;
	}
	
	public UserDAO getUserDAO() 
	{
		return userDAO;
	}
	
	public ProjectDAO getProjectDAO()
	{
		return projectDAO;
	}
	
	public ImageDAO getImageDAO()
	{
		return imageDAO;
	}
	
	public FieldDAO getFieldDAO()
	{
		return fieldDAO;
	}
	
	public ValueDAO getValueDAO()
	{
		return valueDAO;
	}
	
	public Connection getConnection() 
	{
		return connection;
	}

	public void startTransaction() throws DatabaseException 
	{
		try 
		{
			assert (connection == null);			
			connection = DriverManager.getConnection(DATABASE_URL);
			connection.setAutoCommit(false);
		}
		catch (SQLException e) 
		{
			throw new DatabaseException("Could not connect to database. Make sure " + 
				DATABASE_FILE + " is available in ./" + DATABASE_DIRECTORY, e);
		}
	}
	
	public void endTransaction(boolean commit) 
	{
		if (connection != null) 
		{		
			try 
			{
				if (commit) 
				{
					connection.commit();
				}
				else 
				{
					connection.rollback();
				}
			}
			catch (SQLException e) 
			{
				System.out.println("Could not end transaction");
				e.printStackTrace();
			}
			finally 
			{
				safeClose(connection);
				connection = null;
			}
		}
	}
	
	public static void safeClose(Connection conn) {
		if (conn != null) {
			try 
			{
				conn.close();
			}
			catch (SQLException e) {
				// ...
			}
		}
	}
	
	public static void safeClose(Statement stmt) {
		if (stmt != null) {
			try 
			{
				stmt.close();
			}
			catch (SQLException e) {
				// ...
			}
		}
	}
	
	public static void safeClose(PreparedStatement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			}
			catch (SQLException e) {
				// ...
			}
		}
	}
	
	public static void safeClose(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			}
			catch (SQLException e) {
				// ...
			}
		}
	}

}
