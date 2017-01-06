package search;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import client.ClientCommunicator;
import communication.*;
import model.*;

@SuppressWarnings("serial")
public class LeftPanel extends JPanel
{
	private JTextField hostField, portField, userField, passwordField;
	private JButton connButton;
	private JComboBox<String> projectsBox;
	private JList<String> fieldsList;
	private JScrollPane fieldsScroll;
	private DefaultListModel<String> listModel;
	private ArrayList<ModelField> fields;
	
	public LeftPanel(Controller controller)
	{
		this.setPreferredSize(new Dimension(240,400));
		this.setMaximumSize(new Dimension(240,500));
		this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), 
				BorderFactory.createLoweredBevelBorder()));
		
		//create log in panel
		FlowLayout logInLayout = new FlowLayout(FlowLayout.RIGHT,5,5);
		JPanel logInPanel = new JPanel(logInLayout);
		logInPanel.setPreferredSize(new Dimension(230,140));
		logInPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		hostField = new JTextField(12);
		portField = new JTextField(12);
		userField = new JTextField(12);
		passwordField = new JTextField(12);
		connButton = new JButton("Connect");
		connButton.addActionListener(actionListener);
		logInPanel.add(new JLabel("Host Name:"));
		logInPanel.add(hostField);
		logInPanel.add(new JLabel("Port Number:"));
		logInPanel.add(portField);
		logInPanel.add(new JLabel("Username:"));
		logInPanel.add(userField);
		logInPanel.add(new JLabel("Password:"));
		logInPanel.add(passwordField);
		logInPanel.add(connButton);
		add(logInPanel);
		
		FlowLayout fieldsLayout = new FlowLayout(FlowLayout.LEFT,5,5);
		JPanel fieldsPanel = new JPanel(fieldsLayout);
		fieldsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		fieldsPanel.setPreferredSize(new Dimension(230,200));
		projectsBox = new JComboBox<String>();
		projectsBox.addActionListener(actionListener);
		projectsBox.setEditable(false);
		projectsBox.setPreferredSize(new Dimension(150,25));
		
		fieldsList = new JList<String>();
		fieldsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		fieldsList.setLayoutOrientation(JList.VERTICAL_WRAP);
		fieldsScroll = new JScrollPane(fieldsList);
		
		fieldsScroll.setPreferredSize(new Dimension(207,160));
		fieldsScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		fieldsPanel.add(new JLabel("Projects:"));
		fieldsPanel.add(projectsBox);
		fieldsPanel.add(fieldsScroll);
		
		hostField.setText("localhost");
		portField.setText("8081");
		userField.setText("test1");
		passwordField.setText("test1");
		
		add(fieldsPanel);
	}
	
	private ActionListener actionListener = new ActionListener() 
	{	
		public void actionPerformed(ActionEvent event) 
	    {
	    	//when the connect button is pushed
	    	if (event.getSource().equals(connButton))
	    	{
		    	String host = hostField.getText();
		    	int port = Integer.parseInt(portField.getText());
		    	String username = userField.getText();
		    	String password = passwordField.getText();
		    	
		        ClientCommunicator cc = new ClientCommunicator(host,port);
		        ValidateUserComOut result = cc.validateUser(new ValidateUserComIn(username,password));
		        
		        projectsBox.removeAllItems();
		        if (result != null && result.isValid())
		        {
		        	GetProjectsComOut projectsResult = cc.getProjects(new GetProjectsComIn(username,password));
		        	java.util.List<ModelProject> projects = projectsResult.getProjects();
		        	for (ModelProject project : projects)
		        	{
		        		projectsBox.addItem(project.getTitle());
		        	}
		        }
		        else
		        {
		        	projectsBox.addItem("Connection Failed");
		        }
	    	}
	    	
	    	//when something from the projects combo box is selected
	    	if (event.getSource().equals(projectsBox))
	    	{
	    		if (projectsBox.getItemCount() != 0 && projectsBox.getItemAt(0) != "Connection Failed")
				{
					String selectedProject = "" + (projectsBox.getSelectedIndex() + 1);
					
					String host = hostField.getText();
			    	int port = Integer.parseInt(portField.getText());
			    	String username = userField.getText();
			    	String password = passwordField.getText();
			    	
			        ClientCommunicator cc = new ClientCommunicator(host,port);
			        GetFieldsComOut result = cc.getFields(new GetFieldsComIn(username,password,selectedProject));
			        if (result != null)
			        {
			        	fields = (ArrayList<ModelField>) result.getFields();
			        	
			        	listModel = new DefaultListModel<String>();
			        	for (ModelField field : fields)
			        	{
			        		listModel.addElement(field.getTitle());
			        	}
			        	fieldsList.setModel(listModel);
					}
				}
	    	}
	    }
    };
    
	ArrayList<String> getSelectedValues()
    {
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<String> selectedValues = (ArrayList<String>) fieldsList.getSelectedValuesList();
		for (String value : selectedValues)
		{
			for (ModelField field : fields)
			{
				if (value.equals(field.getTitle()))
				{
					result.add("" + field.getId());
				}
			}
		}
    	return result;
    }
    
    String getHost()
    {
    	return hostField.getText();
    }
    
    String getPort()
    {
    	return portField.getText();
    }
    
    String getUsername()
    {
    	return userField.getText();
    }
    
    String getPassword()
    {
    	return passwordField.getText();
    }
}


