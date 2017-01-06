package GUI.dataEntry;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import client.ClientFacade;
import batchState.BatchListener;
import batchState.BatchState;
import qualityChecker.PopupMenu;

@SuppressWarnings("serial")
public class FormEntryPanel extends JPanel implements BatchListener
{
	private BatchState bState;
	private JScrollPane formValuesPane;
	private ArrayList<JTextField> textFields;
	private JList<Integer> rowList;
	private DefaultListModel<Integer> rowListModel;
	private int rowSelected;
	
	private ClientFacade client;
	
	public FormEntryPanel(ClientFacade client, BatchState bState)
	{
		this.client = client;
		this.bState = bState;
		bState.addListener(this);
		this.setLayout(new BorderLayout());
		
		textFields = new ArrayList<JTextField>();
		rowList = new JList<Integer>();
		rowList.setPreferredSize(new Dimension(100,10));
		rowList.addListSelectionListener(listListener);
		rowList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		rowList.setPreferredSize(new Dimension(100,50));
		rowList.setMinimumSize(new Dimension(100,100));
		rowList.setBorder(BorderFactory.createTitledBorder(""));
		this.add(rowList, BorderLayout.CENTER);
	}
	
	private JPanel createEntryPanel()
	{
		JPanel newPanel = new JPanel(new GridLayout(bState.getNumFields(), 2));
		newPanel.setBorder(BorderFactory.createTitledBorder(""));
		
		for (int i = 1; i <= bState.getNumFields(); i++)
		{
			JPanel l = new JPanel(new FlowLayout(FlowLayout.LEFT));
			l.add(new JLabel(bState.getFieldName(i - 1)));
			newPanel.add(l);
			JPanel r = new JPanel();
			JTextField textField = new JTextField(10);
			textField.addFocusListener(focusListener);
			textField.addKeyListener(keyListener);
			textField.addMouseListener(mouseListener);
			textFields.add(textField);
			r.add(textField);
			newPanel.add(r);
		}
		newPanel.setPreferredSize(new Dimension(350,280));
		newPanel.setMinimumSize(newPanel.getPreferredSize());
		return newPanel;
	}
	
	public void clear()
	{
		rowListModel.clear();
		this.remove(formValuesPane);
		textFields.clear();
	}
	
	public void loadBatchState()
	{
		if (bState.getBatchURL() != null)
		{
			rowListModel = new DefaultListModel<Integer>();
			for (int i = 1; i <= bState.getNumRecords(); i++)
			{
				rowListModel.addElement(i);
			}
			rowList.setModel(rowListModel);
			
			formValuesPane = new JScrollPane(createEntryPanel());
			this.add(formValuesPane, BorderLayout.EAST);
		}
	}
	
	public void refresh()
	{
		if (bState.getSelectedCell() > 0)
		{
			if (rowList.getSelectedIndex() > 0)
			{
				rowList.setSelectedIndex(rowList.getSelectedIndex() - 1);
				rowList.setSelectedIndex(rowList.getSelectedIndex() + 1);
			}
			else
			{
				rowList.setSelectedIndex(rowList.getSelectedIndex() + 1);
				rowList.setSelectedIndex(rowList.getSelectedIndex() - 1);
			}
			saveCellValues();
		}
	}

	@Override
	public void cellSelected(int cellNum) 
	{
		int rowNum = (cellNum - 1) / bState.getNumFields();
		rowList.setSelectedIndex(rowNum);
		
		int fieldNum = (bState.getSelectedCell() - 1) % bState.getNumFields();
		textFields.get(fieldNum).grabFocus();
	}
	
	public void saveCellValues()
	{
		for (int i = 0; i < textFields.size(); i++)
		{
			String value = textFields.get(i).getText();
			if (value.length() > 0)
			{
				int index = (rowSelected * bState.getNumFields()) + i;
				bState.setValue(index, value);
				
				if (client.isValidWord(i, value))
				{
					textFields.get(i).setBackground(Color.WHITE);
				}
				else
				{
					textFields.get(i).setBackground(Color.RED);
				}
			}
			else
			{
				textFields.get(i).setBackground(Color.WHITE);
			}
		}
	}
	
	public void useSuggestion(int valueIndex, String suggestion)
	{
		int index = valueIndex % bState.getNumFields();
		int row = valueIndex / bState.getNumFields();
		rowList.setSelectedIndex(row);
		textFields.get(index).setText(suggestion);
		saveCellValues();
	}
	
	public void setBatchState(BatchState bState)
	{
		this.bState = bState;
	}
	
	private ListSelectionListener listListener = new ListSelectionListener()
	{
		@Override
		public void valueChanged(ListSelectionEvent event) 
		{
			if (!rowListModel.isEmpty())
			{
				saveCellValues();
				if (rowList.getSelectedValue() == null)
				{
					cellSelected(bState.getSelectedCell());
				}
				rowSelected = rowList.getSelectedIndex();
				int newCell = ((bState.getSelectedCell() - 1) % bState.getNumFields()) + 1 + (rowSelected * bState.getNumFields());
				bState.aCellIsSelected(newCell);
				
				for (int i = 0; i < textFields.size(); i++)
				{
					int index = (rowSelected * bState.getNumFields()) + i;
					String value = bState.getValue(index);
					textFields.get(i).setText(value);
				}
			}
		}
		
	};
	
	private FocusListener focusListener = new FocusListener()
	{
		@Override
		public void focusGained(FocusEvent event) 
		{
			for (int i = 0; i < textFields.size(); i++)
			{
				if (textFields.get(i).hasFocus())
				{
					int rowSelected = rowList.getSelectedIndex();
					int newCell = (rowSelected * bState.getNumFields()) + i + 1;
					bState.aCellIsSelected(newCell);
				}
			}
		}

		@Override
		public void focusLost(FocusEvent event) 
		{
			saveCellValues();
		}
		
	};
	
	private KeyListener keyListener = new KeyListener()
	{
		@Override
		public void keyPressed(KeyEvent event) 
		{
			if (event.getKeyCode() == KeyEvent.VK_ENTER)
			{
				int newCell = bState.getSelectedCell() + 1;
				if (newCell <= (bState.getNumFields() * bState.getNumRecords()))
				{
					bState.aCellIsSelected(bState.getSelectedCell() + 1);
				}
				else
				{
					bState.aCellIsSelected(1);
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent event) 
		{
			
		}

		@Override
		public void keyTyped(KeyEvent event) 
		{
			
		}
	};	
	
	private MouseListener mouseListener = new MouseListener()
	{
		@Override
		public void mouseClicked(MouseEvent event) 
		{
			return;
		}

		@Override
		public void mouseEntered(MouseEvent event) 
		{
			return;
		}

		@Override
		public void mouseExited(MouseEvent event) 
		{
			return;
		}

		@Override
		public void mousePressed(MouseEvent event) 
		{
			if (event.isPopupTrigger())
			{
				JTextField field = (JTextField)event.getSource();
				int row = rowList.getSelectedIndex();
				int col = 0;
				for (int i = 0; i < textFields.size(); i++)
				{
					if (textFields.get(i).hasFocus())
					{
						col = i;
					}
				}
				int valueIndex = (row * bState.getNumFields()) + col;
				
				if (field.getBackground().equals(Color.RED))
				{ 
					doPop(event,valueIndex);
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent event) 
		{
			if (event.isPopupTrigger())
			{
				JTextField field = (JTextField)event.getSource();
				int row = rowList.getSelectedIndex();
				int col = 0;
				for (int i = 0; i < textFields.size(); i++)
				{
					if (textFields.get(i) == field)
					{
						col = i;
					}
				}
				int valueIndex = (row * bState.getNumFields()) + col;
				
				if (field.getBackground().equals(Color.RED))
				{ 
					doPop(event,valueIndex);
				}
			}
		}
		
		private void doPop(MouseEvent event, int valueIndex)
		{
			PopupMenu menu = new PopupMenu(valueIndex, client);
			menu.show(event.getComponent(), event.getX(), event.getY());
		}
		
	};
}
