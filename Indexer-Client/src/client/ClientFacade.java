package client;

import javax.imageio.ImageIO;
import javax.swing.*;

import qualityChecker.*;
import batchState.*;
import model.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.io.*;

import GUI.*;
import GUI.dialog.DownloadBatchDialog;
import GUI.dialog.SuggestionsDialog;
import communication.*;

public class ClientFacade 
{
	private BatchState bState;
	
	private String hostname;
	private int port;
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private int numRecords;
	
	private LoginFrame loginFrame;
	private IndexerFrame indexerFrame;
	
	private ArrayList<SpellCorrector> spellCorrectors;
	
	public ClientFacade(String hostname, String port)
	{
		this.hostname = hostname;
		this.port = Integer.parseInt(port);
	}
	
	private ValidateUserComOut validateUser(ValidateUserComIn comIn)
	{
		ClientCommunicator cc = new ClientCommunicator(hostname,port);
		
		ValidateUserComOut comOut = cc.validateUser(comIn);
		return comOut;
	}
	
	private DownloadBatchComOut downloadBatch(DownloadBatchComIn comIn)
	{
		ClientCommunicator cc = new ClientCommunicator(hostname,port);
		
		DownloadBatchComOut comOut = cc.downloadBatch(comIn);
		return comOut;
	}
	
	private GetProjectsComOut getProjects(GetProjectsComIn comIn)
	{
		ClientCommunicator cc = new ClientCommunicator(hostname,port);
		
		GetProjectsComOut comOut = cc.getProjects(comIn);
		return comOut;
	}
	
	private GetSampleImageComOut getSampleImage(GetSampleImageComIn comIn)
	{
		ClientCommunicator cc = new ClientCommunicator(hostname,port);
		
		GetSampleImageComOut comOut = cc.getSampleImage(comIn);
		return comOut;
	}
	
	private SubmitBatchComOut submitBatch(SubmitBatchComIn comIn)
	{
		ClientCommunicator cc = new ClientCommunicator(hostname,port);
		
		SubmitBatchComOut comOut = cc.submitBatch(comIn);
		return comOut;
	}
	
	public void login(String username, char[] pass)
	{
		this.username = username;
		StringBuilder builder = new StringBuilder();
		builder.append(pass);
		this.password = builder.toString();
		ValidateUserComOut result = validateUser(new ValidateUserComIn(username,password));
		
		if (result != null && result.isValid())
		{
			this.firstname = result.getFirstName();
			this.lastname = result.getLastName();
			this.numRecords = result.getNumRecords();
			loginFrame.setVisible(false);
			String welcome = "Welcome, " + firstname + " " + lastname + ".\nYou have indexed " + numRecords + " records.";
			JOptionPane.showMessageDialog(null, welcome, "Welcome to Indexer", JOptionPane.DEFAULT_OPTION, null);
			
			bState = BatchState.load(username);
			if (bState == null)
			{
				bState = new BatchState();
				indexerFrame = new IndexerFrame(this,bState);
				indexerFrame.setVisible(true);
				System.out.println("No user file");
			}
			else
			{
				String imageURL = bState.getBatchURL();
				indexerFrame = new IndexerFrame(this,bState);
				if (bState.getBatchURL() != null)
				{
					indexerFrame.activateToolBar(true);
					bState.setBatchURL(imageURL);
					indexerFrame.setDownloadBatch(false);
					createSpellCorrectors();
				}
				indexerFrame.loadBatchState();
			}
			
			indexerFrame.setVisible(true);
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Invalid Login Credentials", "Login Failed", JOptionPane.ERROR_MESSAGE, null);
		}
	}
	
	public void logout()
	{
		save();
		indexerFrame.setVisible(false);
		indexerFrame.dispose();
		loginFrame.setVisible(true);
	}
	
	public void downloadBatch(int projectNum)
	{
		save();
		DownloadBatchComOut comOut = downloadBatch(new DownloadBatchComIn(username,password,projectNum));
		String imageURL = comOut.getImageURL();
		indexerFrame.activateToolBar(true);
		bState.setBatchURL(imageURL);
		bState.setBatchID(comOut.getBatchID());
		bState.setFirstYcoord(comOut.getFirstYcoord());
		bState.setRecordHeight(comOut.getRecordHeight());
		bState.setNumRecords(comOut.getNumRecords());
		bState.setNumFields(comOut.getNumFields());
		bState.setFields((ArrayList<ModelField>) comOut.getFields(), comOut.getServerURL());
		indexerFrame.loadBatchState();
		indexerFrame.setDownloadBatch(false);
		
		createSpellCorrectors();
	}
	
	public void downloadBatchDialog()
	{
		DownloadBatchDialog dialog = new DownloadBatchDialog(indexerFrame, "Download Batch",this);
		dialog.setLocationRelativeTo(indexerFrame);
		dialog.setVisible(true);
	}
	
	public ArrayList<ModelProject> getProjects()
	{
		GetProjectsComOut comOut = getProjects(new GetProjectsComIn(username,password));
		ArrayList<ModelProject> projects = (ArrayList<ModelProject>) comOut.getProjects();
		return projects;
	}
	
	public void viewSample(int projectNum)
	{
		GetSampleImageComOut comOut = getSampleImage(new GetSampleImageComIn(username,password,projectNum));
		String imageURL = comOut.getHostURL() + "/" + comOut.getImageFileName();
		BufferedImage bi;
		try 
		{
			bi = ImageIO.read(new URL(imageURL));
			JOptionPane.showMessageDialog(null, null, "don't know", JOptionPane.DEFAULT_OPTION, new ImageIcon(bi));
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
		indexerFrame.zoomIn();
	}
	
	public void zoomOut()
	{
		indexerFrame.zoomOut();
		
	}
	
	public void invertImage()
	{
		indexerFrame.invertImage();
	}
	
	public void save()
	{
		indexerFrame.save();
		BatchState.save(bState, username);
	}
	
	public void toggleHighlights()
	{
		indexerFrame.toggleHighlights();
	}
	
	public void submit()
	{
		List<ArrayList<String>> values = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < bState.getValues().size();)
		{
			ArrayList<String> row = new ArrayList<String>();
			for (int k = 0; k < bState.getNumFields(); k++)
			{
				row.add(bState.getValues().get(i));//value);
				i++;
			}
			values.add(row);
		}
		
		SubmitBatchComIn comIn = new SubmitBatchComIn(username, password, bState.getBatchID(), values);
		SubmitBatchComOut comOut = submitBatch(comIn);
		
		if (comOut.isValid())
		{
			save();
			indexerFrame.submit();
			bState.submit();
			indexerFrame.activateToolBar(false);
			indexerFrame.setDownloadBatch(true);
			indexerFrame.loadBatchState();
			save();
		}
		else
		{
			System.out.println("Failed to submit");
		}
	}
	
	public void close()
	{
		save();
		loginFrame.setVisible(false);
		loginFrame.dispose();
		indexerFrame.setVisible(false);
		indexerFrame.dispose();
	}
	
	public boolean isValidWord(int index, String word)
	{
		SpellCorrector sc = spellCorrectors.get(index);
		
		if (sc.isEmpty())
		{
			return true;
		}
		return sc.contains(word);
	}
	
	public void seeSuggestions(int valueIndex)
	{
		int col = valueIndex % bState.getNumFields();
		SpellCorrector sc = spellCorrectors.get(col);
		TreeSet<String> suggestions = sc.findSimilarWords(bState.getValue(valueIndex));
		SuggestionsDialog dialog = new SuggestionsDialog(indexerFrame,"Suggestions",suggestions,valueIndex,this);
		dialog.setLocationRelativeTo(indexerFrame);
		dialog.setVisible(true);
	}
	
	public void useSuggestion(int valueIndex, String suggestion)
	{
		//bState.setValue(valueIndex, suggestion);
		indexerFrame.useSuggestion(valueIndex, suggestion);
	}

	/**
	 * @return the loginFrame
	 */
	public LoginFrame getLoginFrame() {
		return loginFrame;
	}

	/**
	 * @param loginFrame the loginFrame to set
	 */
	public void setLoginFrame(LoginFrame loginFrame) {
		this.loginFrame = loginFrame;
	}

	/**
	 * @return the indexerFrame
	 */
	public IndexerFrame getIndexerFrame() {
		return indexerFrame;
	}

	/**
	 * @param indexerFrame the indexerFrame to set
	 */
	public void setIndexerFrame(IndexerFrame indexerFrame) {
		this.indexerFrame = indexerFrame;
	}

	/**
	 * @return the bState
	 */
	public BatchState getBatchState() {
		return bState;
	}

	/**
	 * @param bState the bState to set
	 */
	public void setBatchState(BatchState bState) {
		this.bState = bState;
	}
	
	private void createSpellCorrectors()
	{
		spellCorrectors = new ArrayList<SpellCorrector>();
		for (int i = 0; i < bState.getNumFields(); i++)
		{
			SpellCorrector sc = new SpellCorrector();
			
			String input = "";
			try 
			{
				String knownData = bState.getKnownData(i);
				if (knownData != null)
				{
					URL test = new URL(knownData);
					BufferedReader in = new BufferedReader(new InputStreamReader(test.openStream()));
					
					String next;
			        while ((next = in.readLine()) != null)
			        {
			            input += next;
			        }
			        in.close();
				}
			} 
			catch (MalformedURLException e) 
			{
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				//e.printStackTrace();
			}
			
			if (input != null)
			{
				sc.useDictionaryContent(input);
			}
			spellCorrectors.add(sc);
		}
	}

}
