package search;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class RightPanel extends JPanel 
{
	private Controller controller;
	private JTextField searchValuesField;
	private JScrollPane resultScroll, imagePane;
	private JList<String> resultList;
	private DefaultListModel<String> listModel;
	private JEditorPane htmlViewer;
	private JButton searchButton;
	
	public RightPanel(Controller controller)
	{
		this.controller = controller;
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), 
				BorderFactory.createLoweredBevelBorder()));
		
		//create the search bar panel
		JPanel valuesPanel = new JPanel(new BorderLayout());
		JPanel valuesLeftPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		searchValuesField = new JTextField(25);
		
		searchButton = new JButton("Search");
		searchButton.addActionListener(actionListener);
		
		resultList = new JList<String>();
		resultList.addListSelectionListener(listListener);
		resultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		resultList.setLayoutOrientation(JList.VERTICAL_WRAP);
		
		resultScroll = new JScrollPane(resultList);
		
		resultScroll.setPreferredSize(new Dimension(300,100));
		
		valuesLeftPanel.setPreferredSize(new Dimension(380,50));
		valuesLeftPanel.add(new JLabel("Search For:"));
		valuesLeftPanel.add(searchValuesField);
		valuesLeftPanel.add(searchButton);
		
		valuesPanel.add(valuesLeftPanel, BorderLayout.WEST);
		valuesPanel.add(resultScroll, BorderLayout.CENTER);
		
		add(valuesPanel, BorderLayout.NORTH);
		
		htmlViewer = new JEditorPane();
        htmlViewer.setOpaque(true);
        htmlViewer.setBackground(Color.white);
        htmlViewer.setPreferredSize(new Dimension(500, 600));
        htmlViewer.setEditable(false);
        imagePane = new JScrollPane();
        imagePane.setPreferredSize(new Dimension(499,500));
        
        add(imagePane, BorderLayout.CENTER);
	}
	
	private ActionListener actionListener = new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent event) 
		{
			controller.search();
		}
		
	};
	
	private ListSelectionListener listListener = new ListSelectionListener()
	{
		@Override
		public void valueChanged(ListSelectionEvent event) 
		{
			String selectedImage = resultList.getSelectedValue();
			
			try 
			{
				BufferedImage bi = ImageIO.read(new URL(selectedImage));
				ImageIcon image = new ImageIcon(bi);
				imagePane.setViewportView(new JLabel(image));
			} 
			catch (MalformedURLException e) 
			{
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	};
	
	public String getSearchFor()
	{
		return searchValuesField.getText();
	}
	
	public void searchFailed()
	{
		listModel = new DefaultListModel<String>();
    	listModel.addElement("Search Failed");
    	resultList.setModel(listModel);
	}
	
	public void setResultList(ArrayList<String> results)
	{
		listModel = new DefaultListModel<String>();
		for (String result : results)
		{
			listModel.addElement(result);
		}
		resultList.setModel(listModel);
	}
}
