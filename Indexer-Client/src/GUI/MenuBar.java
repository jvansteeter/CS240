package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import client.ClientFacade;

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar
{
	private ClientFacade client;
	private JMenuItem downloadBatchItem, logoutItem, exitItem;
	
	public MenuBar(ClientFacade c)
	{
		client = c;

        JMenu m_file = new JMenu("File");

        downloadBatchItem = new JMenuItem("Download Batch");
        logoutItem = new JMenuItem("Logout");
        exitItem = new JMenuItem("Exit");
        downloadBatchItem.addActionListener(actionListener);
        logoutItem.addActionListener(actionListener);
        exitItem.addActionListener(actionListener);
        
        add(m_file);
        m_file.add(downloadBatchItem);
        m_file.add(logoutItem);
        m_file.add(exitItem);
	}
	
	private ActionListener actionListener = new ActionListener()
	{

		@Override
		public void actionPerformed(ActionEvent event) 
		{
			if (event.getSource() == exitItem)
			{
				client.close();
			}
			
			if(event.getSource() == logoutItem)
			{
				client.logout();
			}
			
			if (event.getSource() == downloadBatchItem)
			{
				client.downloadBatchDialog();
			}
		}
	};
	
	public void setDownloadBatch(boolean active)
	{
		downloadBatchItem.setEnabled(active);
	}

}
