package search;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class SearchFrame extends JFrame
{
	private Controller controller;
	private LeftPanel leftPanel;
	private RightPanel rightPanel;
	
	public SearchFrame()
	{
		this.setTitle("Search");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		this.addWindowListener(windowAdapter);
		this.addWindowFocusListener(windowAdapter);
		this.addWindowStateListener(windowAdapter);
		
		controller = new Controller();
		leftPanel = new LeftPanel(controller);
		this.add(leftPanel, BorderLayout.WEST);
		
		rightPanel = new RightPanel(controller);
		this.add(rightPanel, BorderLayout.CENTER);
		
		controller.setLeftPanel(leftPanel);
		controller.setRightPanel(rightPanel);
		
		this.setLocation(100, 100);
		
		this.pack();
	}
	
	private WindowAdapter windowAdapter = new WindowAdapter() 
	{
		@Override
		public void windowActivated(WindowEvent e) {
			return;
		}

		@Override
		public void windowClosed(WindowEvent e) {
			return;
		}

		@Override
		public void windowClosing(WindowEvent e) {
			return;
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
			return;
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			return;
		}

		@Override
		public void windowGainedFocus(WindowEvent e) {
			leftPanel.requestFocusInWindow();
		}

		@Override
		public void windowIconified(WindowEvent e) {
			return;
		}

		@Override
		public void windowLostFocus(WindowEvent e) {
			return;
		}

		@Override
		public void windowOpened(WindowEvent e) {
			return;
		}

		@Override
		public void windowStateChanged(WindowEvent e) {
			return;
		}
	};
}
