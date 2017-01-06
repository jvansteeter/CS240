package handlers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.*;
import communication.*;

import com.sun.net.httpserver.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ValidateUserHandler implements HttpHandler
{

	private Logger logger = Logger.getLogger("Record-Indexer"); 
	
	private XStream xmlStream = new XStream(new DomDriver());	

	@Override
	public void handle(HttpExchange exchange) throws IOException 
	{
		ValidateUserComOut result = new ValidateUserComOut(false);
		ValidateUserComIn params = (ValidateUserComIn)xmlStream.fromXML(exchange.getRequestBody());
		
		try 
		{
			result = ServerFacade.validateUser(params);
		}
		catch (ServerException e) 
		{
            logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;				
		}
		
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		xmlStream.toXML(result, exchange.getResponseBody());
		exchange.getResponseBody().close();
	}

}
