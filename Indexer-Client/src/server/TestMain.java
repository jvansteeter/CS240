package server;

import java.io.*;

public class TestMain 
{

	private void run()
	{
		File file = new File("database.sqlite");
		try 
		{
			file.createNewFile();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) 
	{
		TestMain main = new TestMain();
		
		main.run();

	}

}
