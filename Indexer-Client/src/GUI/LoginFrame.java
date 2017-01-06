package GUI;

import javax.swing.*;

import client.ClientFacade;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class LoginFrame extends JFrame
{
	private ClientFacade client;
	private JPanel textPanel, buttonPanel;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton loginButton, exitButton;
	
	public LoginFrame(ClientFacade client)
	{
		this.client = client;
		
		this.setLayout(new BorderLayout());
		this.setTitle("Login to Indexer");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		textPanel = new JPanel();
		textPanel.setLayout(new FlowLayout());
		textPanel.setPreferredSize(new Dimension(400,60));
		
		usernameField = new JTextField(25);
		passwordField = new JPasswordField(25);
		
		
		textPanel.add(new JLabel("Username:"));
		textPanel.add(Box.createHorizontalGlue());
		textPanel.add(usernameField);
		textPanel.add(Box.createRigidArea(new Dimension(400,0)));
		textPanel.add(new JLabel("Password:"));
		textPanel.add(Box.createHorizontalGlue());
		textPanel.add(passwordField);
		
		add(textPanel, BorderLayout.CENTER);
		
		buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(400,35));
		BoxLayout buttonLayout = new BoxLayout(buttonPanel, BoxLayout.X_AXIS);
		buttonPanel.setLayout(buttonLayout);
		loginButton = new JButton("Login");
		loginButton.addActionListener(actionListener);
		exitButton = new JButton("Exit");
		exitButton.addActionListener(actionListener);
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(loginButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(10,0)));
		buttonPanel.add(exitButton);
		buttonPanel.add(Box.createHorizontalGlue());
		
		add(buttonPanel, BorderLayout.SOUTH);
		
		usernameField.setText("test1");
		passwordField.setText("test1");
		
		this.setLocation(100, 100);
		this.pack();
	}
	
	private ActionListener actionListener = new ActionListener()
	{

		@Override
		public void actionPerformed(ActionEvent event) 
		{
			if (event.getSource() == loginButton)
			{
				client.login(usernameField.getText(), passwordField.getPassword());
			}
			
			if (event.getSource() == exitButton)
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
