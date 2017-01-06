package qualityChecker;

import java.awt.event.*;

import javax.swing.*;

import client.ClientFacade;

@SuppressWarnings("serial")
public class PopupMenu extends JPopupMenu
{
	private ClientFacade client;
	private JMenuItem suggestItem;
	private int valueIndex;
	
	public PopupMenu(int valueIndex, ClientFacade client)
	{
		this.client = client;
		this.valueIndex = valueIndex;
		
		suggestItem = new JMenuItem("See Suggestions");
		suggestItem.addActionListener(actionListener);
		add(suggestItem);
	}
	
	private ActionListener actionListener = new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent event) 
		{
			client.seeSuggestions(valueIndex);
		}
	};
}
