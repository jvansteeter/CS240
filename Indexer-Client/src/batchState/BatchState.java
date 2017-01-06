package batchState;

import java.awt.Dimension;
import java.io.*;
import java.util.ArrayList;

import model.ModelField;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class BatchState 
{
	private transient ArrayList<BatchListener> listeners;
	private String batchURL;
	private double scale;
	private int w_translateX;
	private int w_translateY;
	private boolean invert;
	private boolean toggle;
	private int selectedCell;
	
	private int topDividerLocation;
	private int bottomDividerLocation;
	private Dimension frameSize;
	private int frameX;
	private int frameY;
	
	private int batchID;
	private int firstYcoord;
	private int recordHeight;
	private int numRecords;
	private int numFields;
	private ArrayList<Integer> xCoords;
	private ArrayList<Integer> widths;
	private ArrayList<String> fieldNames;
	private ArrayList<String> helpURLs;
	private ArrayList<String> knownData;
	private ArrayList<String> values;
	
	public BatchState()
	{
		listeners = new ArrayList<BatchListener>();
		xCoords = new ArrayList<Integer>();
		widths = new ArrayList<Integer>();
		fieldNames = new ArrayList<String>();
		helpURLs = new ArrayList<String>();
		knownData = new ArrayList<String>();
		values = new ArrayList<String>();
		
		batchURL = null;
		scale = 1;
		w_translateX = -515;
		w_translateY = -190;
		invert = false;
		toggle = true;
		selectedCell = 0;
		
		topDividerLocation = 367;
		bottomDividerLocation = 469;
		frameSize = new Dimension(1000,800);
		frameX = 100;
		frameY = 100;
	}
	
	public void addListener(BatchListener b)
	{
		listeners.add(b);
	}
	
	public void aCellIsSelected(int cellNum)
	{
		selectedCell = cellNum;
		for (BatchListener listener : listeners)
		{
			listener.cellSelected(cellNum);
		}
	}
	
	public void initialize()
	{
		listeners = new ArrayList<BatchListener>();
	}
	
	public void submit()
	{
		xCoords = new ArrayList<Integer>();
		widths = new ArrayList<Integer>();
		fieldNames = new ArrayList<String>();
		helpURLs = new ArrayList<String>();
		knownData = new ArrayList<String>();
		values = new ArrayList<String>();
		
		batchURL = null;
		scale = 1;
		w_translateX = -515;
		w_translateY = -190;
		invert = false;
		toggle = true;
		selectedCell = 0;
	}
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	public int getWidth(int index)
	{
		return (int)widths.get(index);
	}
	
	public String getFieldName(int index)
	{
		return fieldNames.get(index);
	}
	
	public String getHelpURL(int index)
	{
		return helpURLs.get(index);
	}
	
	public String getKnownData(int index)
	{
		return knownData.get(index);
	}
	
	public String getValue(int index)
	{
		return values.get(index);
	}
	
	public ArrayList<String> getValues()
	{
		return values;
	}
	
	public void setValue(int index, String value)
	{
		values.remove(index);
		values.add(index, value);
	}

	/**
	 * @param fields the fields to set
	 */
	public void setFields(ArrayList<ModelField> fields, String serverURL) 
	{
		for (ModelField field : fields)
		{
			xCoords.add(field.getXCoord());
			widths.add(field.getWidth());
			fieldNames.add(field.getTitle());
			helpURLs.add(serverURL + "/" + field.getHelpHTML());
			if (field.getKnownData() != null)
			{
				knownData.add(serverURL + "/" + field.getKnownData());
			}
			else
			{
				knownData.add(null);
			}
		}
		
		for (int i = 0; i < (numFields * numRecords); i++)
		{
			values.add("");
		}
	}
	
	/**
	 * @return the projectID
	 */
	public int getBatchID() {
		return batchID;
	}

	/**
	 * @param projectID the projectID to set
	 */
	public void setBatchID(int batchID) {
		this.batchID = batchID;
	}

	/**
	 * @return the frameX
	 */
	public int getFrameX() {
		return frameX;
	}

	/**
	 * @param frameX the frameX to set
	 */
	public void setFrameX(int frameX) {
		this.frameX = frameX;
	}

	/**
	 * @return the frameY
	 */
	public int getFrameY() {
		return frameY;
	}

	/**
	 * @param frameY the frameY to set
	 */
	public void setFrameY(int frameY) {
		this.frameY = frameY;
	}

	/**
	 * @return the frameSize
	 */
	public Dimension getFrameSize() {
		return frameSize;
	}

	/**
	 * @param frameSize the frameSize to set
	 */
	public void setFrameSize(Dimension frameSize) {
		this.frameSize = frameSize;
	}

	/**
	 * @return the topDividerLocation
	 */
	public int getTopDividerLocation() {
		return topDividerLocation;
	}

	/**
	 * @param topDividerLocation the topDividerLocation to set
	 */
	public void setTopDividerLocation(int topDividerLocation) {
		this.topDividerLocation = topDividerLocation;
	}

	/**
	 * @return the bottomDividerLocation
	 */
	public int getBottomDividerLocation() {
		return bottomDividerLocation;
	}

	/**
	 * @param bottomDividerLocation the bottomDividerLocation to set
	 */
	public void setBottomDividerLocation(int bottomDividerLocation) {
		this.bottomDividerLocation = bottomDividerLocation;
	}

	/**
	 * @return the showSelected
	 */
	public boolean isToggle() {
		return toggle;
	}

	/**
	 * @param showSelected the showSelected to set
	 */
	public void setToggle(boolean active) {
		this.toggle = active;
	}

	/**
	 * @return the selectedCell
	 */
	public int getSelectedCell() {
		return selectedCell;
	}

	/**
	 * @param selectedCell the selectedCell to set
	 */
	public void setSelectedCell(int selectedCell) {
		this.selectedCell = selectedCell;
	}

	/**
	 * @return the batchURL
	 */
	public String getBatchURL() {
		return batchURL;
	}

	/**
	 * @param batchURL the batchURL to set
	 */
	public void setBatchURL(String batchURL) {
		this.batchURL = batchURL;
	}
	
	/**
	 * @return the scale
	 */
	public double getScale() {
		return scale;
	}

	/**
	 * @param scale the scale to set
	 */
	public void setScale(double scale) {
		this.scale = scale;
	}

	/**
	 * @return the w_translateX
	 */
	public int getW_translateX() {
		return w_translateX;
	}

	/**
	 * @param w_translateX the w_translateX to set
	 */
	public void setW_translateX(int w_translateX) {
		this.w_translateX = w_translateX;
	}

	/**
	 * @return the w_translateY
	 */
	public int getW_translateY() {
		return w_translateY;
	}

	/**
	 * @param w_translateY the w_translateY to set
	 */
	public void setW_translateY(int w_translateY) {
		this.w_translateY = w_translateY;
	}

	/**
	 * @return the invert
	 */
	public boolean isInvert() {
		return invert;
	}

	/**
	 * @param invert the invert to set
	 */
	public void setInvert(boolean invert) {
		this.invert = invert;
	}

	/**
	 * @return the firstYcoord
	 */
	public int getFirstYcoord() {
		return firstYcoord;
	}

	/**
	 * @param firstYcoord the firstYcoord to set
	 */
	public void setFirstYcoord(int firstYcoord) {
		this.firstYcoord = firstYcoord;
	}

	/**
	 * @return the recordHeight
	 */
	public int getRecordHeight() {
		return recordHeight;
	}

	/**
	 * @param recordHeight the recordHeight to set
	 */
	public void setRecordHeight(int recordHeight) {
		this.recordHeight = recordHeight;
	}

	/**
	 * @return the numRecords
	 */
	public int getNumRecords() {
		return numRecords;
	}

	/**
	 * @param numRecords the numRecords to set
	 */
	public void setNumRecords(int numRecords) {
		this.numRecords = numRecords;
	}

	/**
	 * @return the numFields
	 */
	public int getNumFields() {
		return numFields;
	}

	/**
	 * @param numFields the numFields to set
	 */
	public void setNumFields(int numFields) {
		this.numFields = numFields;
	}

	/**
	 * 
	 * @param index
	 * @return
	 */
	public int getXCoord(int index) 
	{
		return (int)xCoords.get(index);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BatchState [batchURL=" + batchURL + ", scale=" + scale
				+ ", w_translateX=" + w_translateX + ", w_translateY="
				+ w_translateY + ", invert=" + invert + "]";
	}

	public static void save(BatchState bState, String username)
	{
		XStream xstream = new XStream(new DomDriver());
		try 
		{
			FileOutputStream out = new FileOutputStream("Users/" + username + ".xml");
			xstream.toXML(bState, out);
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
	}

	public static BatchState load(String username)
	{
		BatchState bState = null;
		XStream xstream = new XStream(new DomDriver());
		FileInputStream in;
		try 
		{
			String user = "Users/" + username + ".xml";
			File file = new File(user);
			if (!file.exists())
			{
				save(new BatchState(), username);
			}
			in = new FileInputStream(user);
			bState = (BatchState) xstream.fromXML(in);
			bState.initialize();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		return bState;
	}

}
