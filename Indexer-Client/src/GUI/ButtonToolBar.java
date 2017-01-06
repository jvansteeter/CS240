package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import client.ClientFacade;

@SuppressWarnings("serial")
public class ButtonToolBar extends JPanel
{
	private ClientFacade client;
	private JButton zoomInButton, zoomOutButton, invertImageButton,
		toggleHighlightsButton, saveButton, submitButton;
	
	public ButtonToolBar(ClientFacade client)
	{
		this.client = client;
		
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		zoomInButton = new JButton("Zoom In");
		zoomInButton.addActionListener(actionListener);
		zoomOutButton = new JButton("Zoom Out");
		zoomOutButton.addActionListener(actionListener);
		invertImageButton = new JButton("Invert Image");
		invertImageButton.addActionListener(actionListener);
		toggleHighlightsButton = new JButton("Toggle Highlights");
		toggleHighlightsButton.addActionListener(actionListener);
		saveButton = new JButton("Save");
		saveButton.addActionListener(actionListener);
		submitButton = new JButton("Submit");
		submitButton.addActionListener(actionListener);
		
		add(zoomInButton);
		add(zoomOutButton);
		add(invertImageButton);
		add(toggleHighlightsButton);
		add(saveButton);
		add(submitButton);
	}

	public ActionListener actionListener = new ActionListener()
	{

		@Override
		public void actionPerformed(ActionEvent event) 
		{
			if (event.getSource() == zoomInButton)
			{
				client.zoomIn();
			}
			
			if (event.getSource() == zoomOutButton)
			{
				client.zoomOut();
			}
			
			if (event.getSource() == saveButton)
			{
				client.save();
			}
			
			if (event.getSource() == invertImageButton)
			{
				client.invertImage();
			}
			
			if(event.getSource() == toggleHighlightsButton)
			{
				client.toggleHighlights();
			}
			
			if (event.getSource() == submitButton)
			{
				client.submit();
			}
		}
		
	};
	
	public void active(boolean active)
	{
		zoomInButton.setEnabled(active);
		zoomOutButton.setEnabled(active);
		invertImageButton.setEnabled(active);
		toggleHighlightsButton.setEnabled(active);
		saveButton.setEnabled(active);
		submitButton.setEnabled(active);
	}
}
