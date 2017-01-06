package GUI.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import model.ModelProject;
import client.ClientFacade;

@SuppressWarnings("serial")
public class DownloadBatchDialog extends JDialog
{
	private ClientFacade client;
	private JComboBox<String> projectsBox;
	private JButton viewSampleButton, cancelButton, downloadButton;
	private ArrayList<ModelProject> projects;
	
	public DownloadBatchDialog(Frame owner, String title, ClientFacade c)
	{
		super(owner,title);
		client = c;
		this.setLayout(new BorderLayout());
		this.setResizable(false);
		
		JPanel topPanel = new JPanel();
		JLabel projectsLabel = new JLabel("Projects: ");
		projectsBox = new JComboBox<String>();
		projectsBox.setEditable(false);
		projects = client.getProjects();
		for(ModelProject project : projects)
		{
			projectsBox.addItem(project.getTitle());
		}
		viewSampleButton = new JButton("View Sample");
		viewSampleButton.addActionListener(actionListener);
		topPanel.add(projectsLabel);
		topPanel.add(projectsBox);
		topPanel.add(viewSampleButton);
		add(topPanel, BorderLayout.NORTH);
		
		JPanel bottomPanel = new JPanel();
		cancelButton = new JButton("Cancel");
		downloadButton = new JButton("Download");
		cancelButton.addActionListener(actionListener);
		downloadButton.addActionListener(actionListener);
		bottomPanel.add(cancelButton);
		bottomPanel.add(downloadButton);
		add(bottomPanel, BorderLayout.SOUTH);
		this.pack();
	}
	
	private ActionListener actionListener = new ActionListener()
	{

		@Override
		public void actionPerformed(ActionEvent event) 
		{
			if(event.getSource() == downloadButton)
			{
				int projectNum = 0;
				for (ModelProject project : projects)
				{
					if (projectsBox.getSelectedItem().equals(project.getTitle()))
					{
						projectNum = project.getId();
					}
				}
				client.downloadBatch(projectNum);
				close();
			}
			
			if(event.getSource() == viewSampleButton)
			{
				int projectNum = 0;
				for (ModelProject project : projects)
				{
					if (projectsBox.getSelectedItem().equals(project.getTitle()))
					{
						projectNum = project.getId();
					}
				}
				client.viewSample(projectNum);
			}
			
			if (event.getSource() == cancelButton)
			{
				close();
			}
		}
		
	};
	
	private void close()
	{
		this.setVisible(false);
		this.dispose();
	}

}
