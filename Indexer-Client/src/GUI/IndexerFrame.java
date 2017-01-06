package GUI;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

import client.ClientFacade;
import GUI.dataEntry.EntryTabbedPane;
import batchState.BatchState;

@SuppressWarnings("serial")
public class IndexerFrame extends JFrame
{
	private ClientFacade client;
	private BatchState bState;
	private MenuBar menuBar;
	private ButtonToolBar buttonToolBar;
	private JSplitPane bottomPane, topPane;
	private EntryTabbedPane entryPane;
	private HelpTabbedPane helpPane;
	private ImagePane imagePane;
	
	public IndexerFrame(ClientFacade client, BatchState bState)
	{
		this.client = client;
		this.bState = bState;
		this.addKeyListener(keyListener);
		
		this.setLocation(100,100);
		this.setTitle("Indexer");
		this.setPreferredSize(new Dimension(500,500));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setMinimumSize(new Dimension(700,700));
		
		menuBar = new MenuBar(this.client);
		setJMenuBar(menuBar);
		
		buttonToolBar = new ButtonToolBar(client);
		add(buttonToolBar, BorderLayout.NORTH);
		buttonToolBar.active(false);
		
		imagePane =  new ImagePane(bState);
		entryPane = new EntryTabbedPane(client, bState);
		helpPane = new HelpTabbedPane(bState);
		
		bottomPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, entryPane, helpPane);
		topPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, imagePane, bottomPane);
		bottomPane.setPreferredSize(new Dimension(500,1000));
		bottomPane.setResizeWeight(.5);
		topPane.setPreferredSize(new Dimension(500,1000));
		topPane.setResizeWeight(.5);
		add(topPane, BorderLayout.CENTER);
		
		this.pack();
		this.setSize(1000, 800);
	}
	
	public void loadBatchState()
	{
		topPane.setDividerLocation(bState.getTopDividerLocation());
		bottomPane.setDividerLocation(bState.getBottomDividerLocation());
		this.setLocation(bState.getFrameX(), bState.getFrameY());
		this.setSize(bState.getFrameSize());
		imagePane.loadBatchState();
		entryPane.loadBatchState();
		if (bState.getSelectedCell() > 0)
		{
			bState.aCellIsSelected(bState.getSelectedCell());
		}
	}
	
	public void activateToolBar(boolean active)
	{
		buttonToolBar.active(active);
	}
	
	public void setDownloadBatch(boolean active)
	{
		menuBar.setDownloadBatch(active);
	}
	
	public void zoomIn()
	{
		imagePane.zoomIn();
	}
	
	public void zoomOut()
	{
		imagePane.zoomOut();
	}
	
	public void invertImage()
	{
		imagePane.invertImage();
	}
	
	public void save()
	{
		bState.setTopDividerLocation(topPane.getDividerLocation());
		bState.setBottomDividerLocation(bottomPane.getDividerLocation());
		bState.setFrameSize(this.getSize());
		bState.setFrameX(this.getX());
		bState.setFrameY(this.getY());
		imagePane.save();
	}
	
	public void toggleHighlights()
	{
		imagePane.toggleHighlights();
	}
	
	public void submit()
	{
		entryPane.clear();
		helpPane.clear();
	}
	
	public void useSuggestion(int valueIndex, String suggestion)
	{
		entryPane.useSuggestion(valueIndex,suggestion);
	}
	
	private KeyListener keyListener = new KeyListener()
	{

		@Override
		public void keyPressed(KeyEvent event) 
		{
			if (event.getKeyCode() == KeyEvent.VK_ENTER)
			{
				bState.aCellIsSelected(bState.getSelectedCell() + 1);
			}
			System.out.println("Key pressed");
			
		}

		@Override
		public void keyReleased(KeyEvent event) {
			System.out.println("Key pressed");
			
		}

		@Override
		public void keyTyped(KeyEvent event) 
		{
			if (event.getKeyCode() == KeyEvent.VK_ENTER)
			{
				bState.aCellIsSelected(bState.getSelectedCell() + 1);
			}
			
			System.out.println("Key pressed");
		}
		
	};

}
