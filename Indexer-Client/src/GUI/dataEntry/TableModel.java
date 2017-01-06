package GUI.dataEntry;

import javax.swing.table.AbstractTableModel;

import batchState.*;

@SuppressWarnings("serial")
public class TableModel extends AbstractTableModel
{
	private BatchState bState;
	
	public TableModel(BatchState bState)
	{
		this.bState = bState;
	}
	
	@Override
	public String getColumnName(int column)
	{
		String result = null;
		if (column == 0)
		{
			result = "Record #";
		}
		else
		{
			result = bState.getFieldName(column - 1);
		}
		return result;
	}

	@Override
	public int getColumnCount() 
	{
		return bState.getNumFields() + 1;
	}

	@Override
	public int getRowCount() 
	{
		return bState.getNumRecords();
	}

	@Override
	public Object getValueAt(int row, int col) 
	{
		int cell = 0;
		String result = null;
		if (col == 0)
		{
			cell = row + 1;
			result = "" + cell;
		}
		else
		{
			cell = (row * (bState.getNumFields())) + col - 1;
			result = bState.getValue(cell);
		}
		return result;
	}
	
	@Override
	public void setValueAt(Object value, int row, int col)
	{
		int cell = (row * bState.getNumFields()) + col - 1;
		bState.setValue(cell, (String) value);
	}
	
	@Override
	public boolean isCellEditable(int row, int col)
	{
		if (col == 0)
		{
			return false;
		}
		return true;
	}
}
