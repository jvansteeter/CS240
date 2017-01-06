package GUI;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.event.*;

import javax.imageio.*;
import javax.swing.*;

import batchState.*;

import java.util.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

@SuppressWarnings("serial")
public class ImagePane extends JComponent implements BatchListener 
{
	private Image batch;
	private BatchState bState;
	
	private int w_translateX;
	private int w_translateY;
	private double scale;
	private boolean invert;
	
	private final Color VISIBLE = new Color(135,191,250,192);
	private final Color INVISIBLE = new Color(0,0,0,0);
	private Color SELECTED_COLOR;
	
	private boolean selecting;
	private boolean dragging;
	private int w_dragStartX;
	private int w_dragStartY;
	private int w_dragStartTranslateX;
	private int w_dragStartTranslateY;
	private AffineTransform dragTransform;

	private ArrayList<DrawingShape> shapes;
	
	public ImagePane(BatchState bState) 
	{
		this.bState = bState;
		this.bState.addListener(this);

		w_translateX = 0;
		w_translateY = 0;
		scale = 1.0;
		
		initDrag();

		shapes = new ArrayList<DrawingShape>();
		
		this.setBackground(Color.LIGHT_GRAY);
		this.setPreferredSize(new Dimension(700, 700));
		this.setMinimumSize(new Dimension(100, 100));
		this.setMaximumSize(new Dimension(1000, 1000));
		
		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);
		this.addMouseWheelListener(mouseAdapter);
		this.addComponentListener(componentAdapter);
		
		loadBatchState();
	}
	
	private void drawArray()
	{
		int yCoord = bState.getFirstYcoord();
		int height = bState.getRecordHeight();
		for (int i = 0; i < bState.getNumRecords(); i++)
		{
			for (int k = 0; k < bState.getNumFields(); k++)
			{
				int xCoord = bState.getXCoord(k);
				int width = bState.getWidth(k);
				shapes.add(new DrawingRect(new Rectangle2D.Double(xCoord, yCoord, width, height),INVISIBLE));
			}
			yCoord += height;
		}
	}
	
	public void loadBatchState()
	{
		if (bState.getBatchURL() != null)
		{
			shapes.clear();
			setImage(bState.getBatchURL());
			setScale(bState.getScale());
			setTranslation(bState.getW_translateX(),bState.getW_translateY());
			if (bState.isInvert())
			{
				invertImage();
				invert = true;
			}
			else
			{
				invert = false;
			}
			
			if (bState.isToggle())
			{
				SELECTED_COLOR = VISIBLE;
			}
			else
			{
				SELECTED_COLOR = INVISIBLE;
			}
			
		}
		else
		{
			shapes.clear();
		}
		repaint();
	}
	
	public void setImage(String imageURL)
	{
		try 
		{
			shapes.clear();
			batch = ImageIO.read(new URL(imageURL));
			shapes.add(new DrawingImage(batch, new Rectangle2D.Double(0, 0, batch.getWidth(null), batch.getHeight(null))));
			drawArray();
			setTranslation(-845,-235);
			repaint();
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
	
	public void zoomIn()
	{
		setScale(scale + 0.2);
	}
	
	public void zoomOut()
	{
		setScale(scale - 0.2);
	}
	
	public void invertImage()
	{
		RescaleOp op = new RescaleOp(-1.0f, 255f, null);
		batch = op.filter((BufferedImage) batch, null); 
		
		shapes.remove(0);
		shapes.add(0, new DrawingImage(batch, new Rectangle2D.Double(0, 0, batch.getWidth(null), batch.getHeight(null))));
		if (invert)
		{
			invert = false;
		}
		else
		{
			invert = true;
		}
		repaint();
	}
	
	public void save()
	{
		bState.setScale(scale);
		bState.setW_translateX(w_translateX);
		bState.setW_translateY(w_translateY);
		bState.setInvert(invert);
	}
	
	public void toggleHighlights()
	{
		if (bState.isToggle())
		{
			SELECTED_COLOR = INVISIBLE;
			bState.setToggle(false);
		}
		else
		{
			SELECTED_COLOR = VISIBLE;
			bState.setToggle(true);
		}
		cellSelected(bState.getSelectedCell());
	}
	
	public void setBatchState(BatchState bState)
	{
		this.bState = bState;
		loadBatchState();
	}
	
	@Override
	public void cellSelected(int cellNum) 
	{
		for (int i = 1; i < shapes.size(); i++)
		{
			shapes.get(i).setColor(INVISIBLE);
		}
		shapes.get(cellNum).setColor(SELECTED_COLOR);
		repaint();
	}
	
	private void initDrag() {
		dragging = false;
		w_dragStartX = 0;
		w_dragStartY = 0;
		w_dragStartTranslateX = 0;
		w_dragStartTranslateY = 0;
		dragTransform = null;
	}
	
	private int midX()
	{
		return this.getWidth()/2;
	}
	
	private int midY()
	{
		return this.getHeight()/2;
	}
	
	public void setScale(double newScale) 
	{
		if (newScale >= 0.2 && newScale <= 2)
		{
			scale = newScale;
			this.repaint();
		}
	}
	
	public void setTranslation(int w_newTranslateX, int w_newTranslateY) {
		w_translateX = w_newTranslateX;
		w_translateY = w_newTranslateY;
		this.repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D)g;
		drawBackground(g2);

		g2.translate(midX(), midY());
		g2.scale(scale, scale);
		g2.translate(w_translateX, w_translateY);

		drawShapes(g2);
	}
	
	private void drawBackground(Graphics2D g2) {
		g2.setColor(getBackground());
		g2.fillRect(0,  0, getWidth(), getHeight());
	}

	private void drawShapes(Graphics2D g2) {
		for (DrawingShape shape : shapes) {
			shape.draw(g2);
		}
	}
	
	private MouseAdapter mouseAdapter = new MouseAdapter() 
	{

		@Override
		public void mousePressed(MouseEvent e) {
			int d_X = e.getX();
			int d_Y = e.getY();
			
			selecting = true;
			
			AffineTransform transform = new AffineTransform();
			transform.translate(midX(), midY());
			transform.scale(scale, scale);
			transform.translate(w_translateX, w_translateY);
			
			Point2D d_Pt = new Point2D.Double(d_X, d_Y);
			Point2D w_Pt = new Point2D.Double();
			try
			{
				transform.inverseTransform(d_Pt, w_Pt);
			}
			catch (NoninvertibleTransformException ex) {
				return;
			}
			int w_X = (int)w_Pt.getX();
			int w_Y = (int)w_Pt.getY();
			
			boolean hitShape = false;
			
			Graphics2D g2 = (Graphics2D)getGraphics();
			for (DrawingShape shape : shapes) {
				if (shape.contains(g2, w_X, w_Y)) {
					hitShape = true;
					break;
				}
			}
			
			if (hitShape) {
				dragging = true;		
				w_dragStartX = w_X;
				w_dragStartY = w_Y;		
				w_dragStartTranslateX = w_translateX;
				w_dragStartTranslateY = w_translateY;
				dragTransform = transform;
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {		
			if (dragging) {
				int d_X = e.getX();
				int d_Y = e.getY();
				
				selecting = false;
				
				Point2D d_Pt = new Point2D.Double(d_X, d_Y);
				Point2D w_Pt = new Point2D.Double();
				try
				{
					dragTransform.inverseTransform(d_Pt, w_Pt);
				}
				catch (NoninvertibleTransformException ex) {
					return;
				}
				int w_X = (int)w_Pt.getX();
				int w_Y = (int)w_Pt.getY();
				
				int w_deltaX = w_X - w_dragStartX;
				int w_deltaY = w_Y - w_dragStartY;
				
				w_translateX = w_dragStartTranslateX + w_deltaX;
				w_translateY = w_dragStartTranslateY + w_deltaY;
				
				repaint();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			initDrag();
			
			if (selecting)
			{
				int d_X = e.getX();
				int d_Y = e.getY();
				
				AffineTransform transform = new AffineTransform();
				transform.translate(midX(), midY());
				transform.scale(scale, scale);
				transform.translate(w_translateX, w_translateY);
				
				Point2D d_Pt = new Point2D.Double(d_X, d_Y);
				Point2D w_Pt = new Point2D.Double();
				try
				{
					transform.inverseTransform(d_Pt, w_Pt);
				}
				catch (NoninvertibleTransformException ex) {
					return;
				}
				int w_X = (int)w_Pt.getX();
				int w_Y = (int)w_Pt.getY();
				
				Graphics2D g2 = (Graphics2D)getGraphics();
				for (int i = 1; i < shapes.size(); i++) 
				{
					if (shapes.get(i).contains(g2, w_X, w_Y)) 
					{
						bState.aCellIsSelected(i);
					}
				}
			}
			repaint();
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) 
		{
			double change = e.getWheelRotation();
			setScale(scale - (change/10));
			return;
		}	
	};
	
	private ComponentAdapter componentAdapter = new ComponentAdapter() {

		@Override
		public void componentHidden(ComponentEvent e) {
			return;
		}

		@Override
		public void componentMoved(ComponentEvent e) {
			return;
		}

		@Override
		public void componentResized(ComponentEvent e) {
			return;
		}

		@Override
		public void componentShown(ComponentEvent e) {
			return;
		}	
	};

	
	/////////////////
	// Drawing Shape
	/////////////////
	
	
	interface DrawingShape {
		boolean contains(Graphics2D g2, double x, double y);
		void draw(Graphics2D g2);
		Rectangle2D getBounds(Graphics2D g2);
		void setColor(Color color);
	}


	class DrawingRect implements DrawingShape {

		private Rectangle2D rect;
		private Color color;
		
		public DrawingRect(Rectangle2D rect, Color color) {
			this.rect = rect;
			this.color = color;
		}

		@Override
		public boolean contains(Graphics2D g2, double x, double y) {
			return rect.contains(x, y);
		}

		@Override
		public void draw(Graphics2D g2) {
			g2.setColor(color);
			g2.fill(rect);
		}
		
		@Override
		public Rectangle2D getBounds(Graphics2D g2) {
			return rect.getBounds2D();
		}

		@Override
		public void setColor(Color color) {
			this.color = color;
		}
	}

	class DrawingImage implements DrawingShape {

		private Image image;
		private Rectangle2D rect;
		
		public DrawingImage(Image image, Rectangle2D rect) {
			this.image = image;
			this.rect = rect;
		}

		@Override
		public boolean contains(Graphics2D g2, double x, double y) {
			return rect.contains(x, y);
		}

		@Override
		public void draw(Graphics2D g2) {
			g2.drawImage(image, (int)rect.getMinX(), (int)rect.getMinY(), (int)rect.getMaxX(), (int)rect.getMaxY(),
							0, 0, image.getWidth(null), image.getHeight(null), null);
		}	
		
		@Override
		public Rectangle2D getBounds(Graphics2D g2) {
			return rect.getBounds2D();
		}

		@Override
		public void setColor(Color color) {
			return;
		}
	}
}