package handlers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class DownloadFileHandler implements HttpHandler
{
	
	@Override
	public void handle(HttpExchange exchange) throws IOException 
	{
		String params = exchange.getRequestURI().toString();
		//params = params.replace("/DownloadFile", "Records");
		params = "Records" + params;
		Path p = Paths.get(params);
		byte[] b = Files.readAllBytes(p);	
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, b.length);
		exchange.getResponseBody().write(b);
		exchange.getResponseBody().close();
	}
}
