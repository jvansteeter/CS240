package search;

import java.awt.EventQueue;

public class SearchMain 
{

	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{		
			public void run() 
			{
				SearchFrame frame = new SearchFrame();
				frame.setVisible(true);
			}
		});
	}

}
