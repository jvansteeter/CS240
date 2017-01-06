package GUI.dataEntry;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import qualityChecker.PopupMenu;
import batchState.*;
import client.*;

@SuppressWarnings("serial")
public class TableEntryPanel extends JPanel implements BatchListener
{
	private ClientFacade client;
	private BatchState bState;
	private TableModel tableModel;
	private JTable table;
	private JScrollPane scroll;
	
	public TableEntryPanel(ClientFacade client, BatchState bState)
	{
		this.client = client;
		this.bState = bState;
		bState.addListener(this);
		this.setLayout(new BorderLayout());
	}
	
	public void createTable()
	{
		tableModel = new TableModel(bState);
		table = new JTable(tableModel);
		table.addMouseListener(mouseListener);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		table.setRowHeight(30);
		table.getTableHeader().setReorderingAllowed(false);
		
		table.setCellSelectionEnabled(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		TableColumnModel columnModel = table.getColumnModel();
		for (int i = 0; i < tableModel.getColumnCount(); ++i) 
		{
			int preferredWidth = 0;
			if (i == 0)
			{
				preferredWidth = 10;
			}
			else
			{
				preferredWidth = this.getWidth() / (bState.getNumFields() + 1);
			}
			TableColumn column = columnModel.getColumn(i);
			column.setPreferredWidth(preferredWidth);
			column.setCellRenderer(new MyCellRenderer(client, bState));
		}	
		
		panel.add(table.getTableHeader(), BorderLayout.NORTH);
		panel.add(table, BorderLayout.CENTER);
		
		scroll = new JScrollPane(panel);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scroll, BorderLayout.CENTER);
	}
	
	public void loadBatchState()
	{
		if (scroll != null)
			this.remove(scroll);
		if (bState.getBatchURL() != null)
		{
			createTable();
		}
	}
	
	public void clear()
	{
		this.remove(table.getTableHeader());
		this.remove(table);
	}

	@Override
	public void cellSelected(int cellNum) 
	{
		int row = (cellNum - 1) / (bState.getNumFields());
		int col = (cellNum - 1) % (bState.getNumFields()) + 1;
		
		table.changeSelection(row, col, false, false);
	}
	
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
				int row = table.rowAtPoint(event.getPoint());
				int col = table.columnAtPoint(event.getPoint());
				int index = (row * bState.getNumFields()) + col;
				String word = bState.getValue(index);
				
				if (word.length() > 0 && !client.isValidWord(col - 1, word))
				{
					doPop(event,index);
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent event) 
		{
			if (event.isPopupTrigger())
			{
				int row = table.rowAtPoint(event.getPoint());
				int col = table.columnAtPoint(event.getPoint());
				int index = (row * bState.getNumFields()) + col - 1;
				String word = bState.getValue(index);
				
				if (word.length() > 0 && !client.isValidWord(col - 1, word))
				{
					doPop(event,index);
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

@SuppressWarnings("serial")
class MyCellRenderer extends JLabel implements TableCellRenderer 
{
	private final Color VISIBLE = new Color(135,191,250,192);
	private final Color INVISIBLE = new Color(0,0,0,0);
	
	private Border unselectedBorder = BorderFactory.createLineBorder(new Color(0,0,0,0));//Color.BLACK, 1);
	private Border selectedBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
	
	private ClientFacade client;
	private BatchState bState;

	public MyCellRenderer(ClientFacade client, BatchState bState) 
	{	
		this.client = client;
		this.bState = bState;
		setOpaque(true);
		setFont(getFont().deriveFont(16.0f));
	}

	@Override
	public Component getTableCellRendererComponent(JTable table,
			Object value, boolean isSelected, boolean hasFocus, int row, int column)
	{
		this.setBackground(Color.WHITE);
		
		if (isSelected) 
		{
			this.setBorder(selectedBorder);
			this.setBackground(VISIBLE);
			if (column != 0)
			{
				int cellNum = (row * bState.getNumFields()) + column;
				bState.aCellIsSelected(cellNum);
			}
		}
		else 
		{
			this.setBorder(unselectedBorder);
			
			
			String text = (String)value;
			if (column > 0 && text.length() > 0)
			{
				if (client.isValidWord(column - 1, text))
				{
					this.setBackground(INVISIBLE);
				}
				else
				{
					this.setBackground(Color.RED);
				}
			}
			else
			{
				this.setBackground(INVISIBLE);
			}
		}
		this.setText((String) value);
		return this;
	}
}

