package GUI.dialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TreeSet;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import client.ClientFacade;

@SuppressWarnings("serial")
public class SuggestionsDialog extends JDialog
{
	private ClientFacade client;
	private int valueIndex;
	
	private JPanel panel;
	private JScrollPane scroll;
	private JList<String> list;
	private DefaultListModel<String> listModel;
	private JButton cancelButton, suggestButton;
	
	public SuggestionsDialog(Frame owner, String title, TreeSet<String> suggestions, int valueIndex, ClientFacade client)
	{
		super(owner,title);
		
		this.client = client;
		this.valueIndex = valueIndex;
		this.setResizable(false);
		
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(210,200));
		listModel = new DefaultListModel<String>();
		for (String suggest : suggestions)
		{
			listModel.addElement(suggest);
		}
		if (suggestions.size() == 0)
		{
			listModel.addElement("No Suggestions");
		}
		list = new JList<String>(listModel);
		list.grabFocus();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.addListSelectionListener(listSelectionListener);
		scroll = new JScrollPane(list);
		scroll.setPreferredSize(new Dimension(150,150));
		panel.add(scroll);
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(actionListener);
		panel.add(cancelButton);
		
		suggestButton = new JButton("Use Suggestion");
		suggestButton.addActionListener(actionListener);
		suggestButton.setEnabled(false);
		panel.add(suggestButton);
		
		add(panel);
		this.pack();
	}
	
	private void close()
	{
		this.setVisible(false);
		this.dispose();
	}
	
	private ActionListener actionListener = new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent event) 
		{
			if (event.getSource() == cancelButton)
			{
				close();
			}
			
			if (event.getSource() == suggestButton)
			{
				String suggestion = list.getSelectedValue();
				client.useSuggestion(valueIndex, suggestion);
				close();
			}
		}	
	};
	
	private ListSelectionListener listSelectionListener = new ListSelectionListener()
	{

		@Override
		public void valueChanged(ListSelectionEvent event) 
		{
			if (list.getSelectedValue() != "No Suggestions")
			{
				suggestButton.setEnabled(true);
			}
		}
		
	};

}
