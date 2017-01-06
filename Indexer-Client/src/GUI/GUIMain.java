package GUI;

import java.awt.EventQueue;

import client.ClientFacade;

public class GUIMain 
{

	public static void main(String[] args) 
	{
		final String hostname = args[0];
		final String port = args[1];
		EventQueue.invokeLater(new Runnable() 
		{		
			public void run() 
			{
				ClientFacade client = new ClientFacade(hostname,port);
				LoginFrame loginFrame = new LoginFrame(client);
				loginFrame.setVisible(true);
				client.setLoginFrame(loginFrame);
			}
		});
	}

}
