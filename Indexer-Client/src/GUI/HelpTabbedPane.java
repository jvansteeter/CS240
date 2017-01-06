package GUI;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;

import batchState.*;

@SuppressWarnings("serial")
public class HelpTabbedPane extends JTabbedPane implements BatchListener
{
	private BatchState bState;
	private JPanel imageNavigationPanel;
	private JEditorPane fieldHelp;
	
	public HelpTabbedPane(BatchState bState)
	{
		this.bState = bState;
		bState.addListener(this);
		this.setPreferredSize(new Dimension(700,1000));
		
		fieldHelp = new JEditorPane();
		fieldHelp.setEditable(false);
		fieldHelp.setBackground(Color.WHITE);
		fieldHelp.setContentType("text/html");
		
		imageNavigationPanel = new JPanel();
		
		this.addTab("Field Help", fieldHelp);
		this.addTab("Image Navigation", imageNavigationPanel);
	}

	@Override
	public void cellSelected(int cellNum) 
	{
		int helpIndex = (cellNum - 1) % bState.getNumFields();
		String url = bState.getHelpURL(helpIndex);
		try 
		{
			fieldHelp.setPage(url);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void clear()
	{
		fieldHelp.setText("");
	}
	
	public void setBatchState(BatchState bState)
	{
		this.bState = bState;
	}

}
