package GUI.dataEntry;

import java.awt.*;

import javax.swing.*;
import javax.swing.event.*;

import batchState.*;
import client.*;

@SuppressWarnings("serial")
public class EntryTabbedPane extends JTabbedPane
{
	private FormEntryPanel formEntryPanel;
	private TableEntryPanel tableEntryPanel;
	
	public EntryTabbedPane(ClientFacade client, BatchState bState)
	{
		this.setPreferredSize(new Dimension(500,1000));
		this.addChangeListener(changeListener);
	
		tableEntryPanel = new TableEntryPanel(client, bState);
		this.addTab("Table Entry", tableEntryPanel);
		
		formEntryPanel = new FormEntryPanel(client, bState);
		this.addTab("Form Entry", formEntryPanel);
	}
	
	public void clear()
	{
		formEntryPanel.clear();
		tableEntryPanel.clear();
	}
	
	public void loadBatchState()
	{
		formEntryPanel.loadBatchState();
		tableEntryPanel.loadBatchState();
	}
	
	public void useSuggestion(int valueIndex, String suggestion)
	{
		formEntryPanel.useSuggestion(valueIndex,suggestion);
	}
	
	private ChangeListener changeListener = new ChangeListener()
	{

		@Override
		public void stateChanged(ChangeEvent eventChange) 
		{
			if (formEntryPanel != null)
			{
				formEntryPanel.refresh();
			}
		}
		
	};
}
