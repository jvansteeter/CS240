package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.logging.*;

import server.ServerFacade;
import handlers.*;

import com.sun.net.httpserver.*;

public class Server 
{
	private static int SERVER_PORT_NUMBER = 8081;
	private static final int MAX_WAITING_CONNECTIONS = 10;
	
	private static Logger logger;
	
	static 
	{
		try 
		{
			initLog();
		}
		catch (IOException e) 
		{
			System.out.println("Could not initialize log: " + e.getMessage());
		}
	}
	
	private static void initLog() throws IOException 
	{
		Level logLevel = Level.FINE;
		
		logger = Logger.getLogger("contactmanager"); 
		logger.setLevel(logLevel);
		logger.setUseParentHandlers(false);
		
		Handler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(logLevel);
		consoleHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(consoleHandler);

		FileHandler fileHandler = new FileHandler("log.txt", false);
		fileHandler.setLevel(logLevel);
		fileHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(fileHandler);
	}
	
	public static String getURL()
	{
		String result = "";
		try 
		{
			result = "http://" + InetAddress.getLocalHost().getHostName() + ":" + SERVER_PORT_NUMBER;
		} 
		catch (UnknownHostException e) 
		{
			e.printStackTrace();
		}
		return result;
	}
	
	private HttpServer server;
	
	private Server() 
	{
		return;
	}
	
	private void run(String portNumber) 
	{
		if (!portNumber.isEmpty())
		{
			SERVER_PORT_NUMBER = Integer.parseInt(portNumber);
		}
		
		logger.info("Initializing Model");
		
		try 
		{
			ServerFacade.initialize();		
		}
		catch (ServerException e) 
		{
			logger.log(Level.SEVERE, e.getMessage(), e);
			return;
		}
		
		logger.info("Initializing HTTP Server");
		
		try 
		{
			server = HttpServer.create(new InetSocketAddress(SERVER_PORT_NUMBER),
											MAX_WAITING_CONNECTIONS);
		} 
		catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);			
			return;
		}

		server.setExecutor(null); // use the default executor
		
		server.createContext("/ValidateUser", validateUserHandler);
		server.createContext("/GetProjects", getProjectsHandler);
		server.createContext("/GetSampleImage", getSampleImageHandler);
		server.createContext("/DownloadBatch", downloadBatchHandler);
		server.createContext("/SubmitBatch", submitBatchHandler);
		server.createContext("/GetFields", getFieldsHandler);
		server.createContext("/Search", searchHandler);
		server.createContext("/images", downloadFileHandler);
		server.createContext("/knowndata", downloadFileHandler);
		server.createContext("/fieldhelp", downloadFileHandler);
		
		logger.info("Starting HTTP Server");

		server.start();
	}

	private HttpHandler validateUserHandler = new ValidateUserHandler();
	private HttpHandler getProjectsHandler = new GetProjectsHandler();
	private HttpHandler getSampleImageHandler = new GetSampleImageHandler();
	private HttpHandler downloadBatchHandler = new DownloadBatchHandler();
	private HttpHandler submitBatchHandler = new SubmitBatchHandler();
	private HttpHandler getFieldsHandler = new GetFieldsHandler();
	private HttpHandler searchHandler = new SearchHandler();
	private HttpHandler downloadFileHandler = new DownloadFileHandler();
	
	public static void main(String[] args) 
	{
		new Server().run(args[0]);
	}

}
