package DataImport;

import java.sql.*;
import java.util.ArrayList;
import java.io.*;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import org.apache.commons.io.*;

import server.DatabaseException;
import server.DatabaseRep;
import model.*;

public class DataImporter 
{
	private DatabaseRep db;
	private ArrayList<ModelUser> users;
	private ArrayList<ModelProject> projects;
	private ArrayList<ModelImage> images;
	private ArrayList<ModelField> fields;
	private ArrayList<ModelValue> values;
	//private Element root;
	
	private DataImporter()
	{
		db = new DatabaseRep();
		users = new ArrayList<ModelUser>();
		projects = new ArrayList<ModelProject>();
		images = new ArrayList<ModelImage>();
		fields = new ArrayList<ModelField>();
		values = new ArrayList<ModelValue>();
		
		try 
		{
			DatabaseRep.initialize();
		} 
		catch (DatabaseException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static ArrayList<Element> getChildElements(Node node)
	{
		ArrayList<Element> result = new ArrayList<Element>();
		
		NodeList children = node.getChildNodes();
		for(int i = 0; i < children.getLength(); i++)
		{
			Node child = children.item(i);
			if(child.getNodeType() == Node.ELEMENT_NODE)
			{
				result.add((Element)child);
			}
		}
		return result;
	}
	
	/*private void dropAllTables() throws DatabaseException
	{
		PreparedStatement stmt = null;
		try 
		{
			String query = "drop table ?";
			stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, "users");
			if (stmt.executeUpdate() != 1) 
			{
				//do nothing throw new DatabaseException("Could not delete contact");
			}
		}
		catch (SQLException e) 
		{
			throw new DatabaseException("Could not delete contact", e);
		}
		finally 
		{
			DatabaseRep.safeClose(stmt);
		}
	}*/
	
	public void setup()
	{
		try 
		{
			// create tables
			String createUsersTable = "create table users\n" +
					"(\n" +
					"id integer not null primary key autoincrement,\n" +
					"username varchar(255) not null,\n" +
					"password varchar(255) not null,\n" +
					"firstname varchar(255) not null,\n" +
					"lastname varchar(255) not null,\n" +
					"email varchar(255) not null,\n" +
					"indexedrecords int not null,\n" +
					"currentbatch int\n" +
					");";
			String createProjectsTable = "create table projects\n" +
					"(\n" +
					"id integer not null primary key autoincrement,\n" +
					"title varchar(255) not null,\n" +
					"recordsperimage int not null,\n" +
					"firstYcoord int not null,\n" +
					"recordheight int not null\n" +
					");";
			String createFieldsTable = "create table fields\n" +
					"(\n" +
					"id integer not null primary key autoincrement,\n" +
					"title varchar(255) not null,\n" +
					"xcoord int not null,\n" +
					"width int not null,\n" +
					"relative_position int not null, \n" +
					"helphtml varchar(255) not null,\n" +
					"knowndata varchar(255),\n" +
					"project_id int not null\n" +
					");";
			String createImagesTable = "create table images\n" +
					"(\n" +
					"id integer not null primary key autoincrement,\n" +
					"file varchar(255) not null,\n" +
					"project_id int not null,\n" +
					"checked_to_user int\n" +
					");";
			String createValueTable = "create table value\n" +
					"(\n" +
					"id integer not null primary key autoincrement,\n" +
					"value carchar(255) not null," +
					"field varchar(255) not null,\n" +
					"row int not null,\n" +
					"image_id int\n" +
					");";
			
			
			db.startTransaction();
			//dropAllTables();
			Statement stmt = db.getConnection().createStatement();
			stmt.execute("drop table users");
			stmt.execute(createUsersTable);
			stmt.execute("drop table projects");
			stmt.execute(createProjectsTable);
			stmt.execute("drop table fields");
			stmt.execute(createFieldsTable);
			stmt.execute("drop table images");
			stmt.execute(createImagesTable);
			stmt.execute("drop table value");
			stmt.execute(createValueTable);
			db.endTransaction(true);
			stmt.close();
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (DatabaseException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void importData(String xmlFileName)
	{
		try 
		{
			File xmlFile = new File(xmlFileName);
			File dest = new File("Records");
			
			//copy parent folder into projects folder
			//	We make sure that the directory we are copying is not the the destination
			//	directory.  Otherwise, we delete the directories we are about to copy.
			if(!xmlFile.getParentFile().getCanonicalPath().equals(dest.getCanonicalPath()))
			{
				FileUtils.deleteDirectory(dest);
			}
				
			//	Copy the directories (recursively) from our source to our destination.
			FileUtils.copyDirectory(xmlFile.getParentFile(), dest);
			
			DocumentBuilder docB = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = docB.parse(xmlFile);
			doc.getDocumentElement().normalize();
			
			Element root = doc.getDocumentElement();
			
			ArrayList<Element> rootElements = DataImporter.getChildElements(root);
			
			//user table
			ArrayList<Element> userElements = DataImporter.getChildElements(rootElements.get(0));
			for (Element userElem : userElements)
			{	
				Element usernameElement = (Element) userElem.getElementsByTagName("username").item(0);
				Element passwordElement = (Element) userElem.getElementsByTagName("password").item(0);
				Element firstnameElement = (Element) userElem.getElementsByTagName("firstname").item(0);
				Element lastnameElement = (Element) userElem.getElementsByTagName("lastname").item(0);
				Element emailElement = (Element) userElem.getElementsByTagName("email").item(0);
				Element indexedrecordsElement = (Element) userElem.getElementsByTagName("indexedrecords").item(0);
				
				String username = usernameElement.getTextContent();
				String password = passwordElement.getTextContent();
				String firstname = firstnameElement.getTextContent();
				String lastname = lastnameElement.getTextContent();
				String email = emailElement.getTextContent();
				int indexedrecords = Integer.parseInt(((Node) indexedrecordsElement).getTextContent());
				
				ModelUser newUser = new ModelUser(username, password, firstname, lastname, email, indexedrecords);
				users.add(newUser);
			}
			
			//projects table
			int projectCount = 0;
			int imageCount = 0;
			ArrayList<Element> projectElements = DataImporter.getChildElements(rootElements.get(1));
			for (Element projectElement : projectElements)
			{	
				projectCount++;
				Element titleElement = (Element) projectElement.getElementsByTagName("title").item(0);
				Element recordsperimageElement = (Element) projectElement.getElementsByTagName("recordsperimage").item(0);
				Element firstYcoordElement = (Element) projectElement.getElementsByTagName("firstycoord").item(0);
				Element recordheightElement = (Element) projectElement.getElementsByTagName("recordheight").item(0);
				
				String title = titleElement.getTextContent();
				int recordsperimage = Integer.parseInt(recordsperimageElement.getTextContent());
				int firstYcoord = Integer.parseInt(firstYcoordElement.getTextContent());
				int recordheight = Integer.parseInt(recordheightElement.getTextContent());
				
				ModelProject toAdd = new ModelProject(title, recordsperimage, firstYcoord, recordheight);
				projects.add(toAdd);
				
				//fields table
				int fieldPosition = 0;
				Element fieldsElement = (Element) projectElement.getElementsByTagName("fields").item(0);
				ArrayList<Element> fieldElements = DataImporter.getChildElements(fieldsElement);
				for (Element fieldElement : fieldElements)
				{
					fieldPosition++;
					Element fieldtitleElement = (Element) fieldElement.getElementsByTagName("title").item(0);
					Element fieldXcoordElement = (Element) fieldElement.getElementsByTagName("xcoord").item(0);
					Element fieldWidthElement = (Element) fieldElement.getElementsByTagName("width").item(0);
					Element fieldHelphtmlElement = (Element) fieldElement.getElementsByTagName("helphtml").item(0);
					Element fieldKnownDataElement = (Element) fieldElement.getElementsByTagName("knowndata").item(0);
					
					String fieldTitle = fieldtitleElement.getTextContent();
					int fieldXcoord = Integer.parseInt(fieldXcoordElement.getTextContent());
					int fieldWidth = Integer.parseInt(fieldWidthElement.getTextContent());
					String fieldHelphtml = fieldHelphtmlElement.getTextContent();
					String fieldKnownData;
					if (fieldKnownDataElement == null)
					{
						fieldKnownData = null;
					}
					else
					{
						fieldKnownData = fieldKnownDataElement.getTextContent();
					}
					
					
					ModelField fieldToAdd = new ModelField(fieldTitle, fieldXcoord, fieldWidth, fieldPosition, fieldHelphtml, fieldKnownData, projectCount);
					fields.add(fieldToAdd);
				}
				
				//images table
				Element imagesElement = (Element) projectElement.getElementsByTagName("images").item(0);
				ArrayList<Element> imageElements = DataImporter.getChildElements(imagesElement);
				for (Element imageElement : imageElements)
				{
					imageCount++;
					Element imageFileElement = (Element) imageElement.getElementsByTagName("file").item(0);
					Element recordsElement = (Element) imageElement.getElementsByTagName("records").item(0);
					
					String imageFile = imageFileElement.getTextContent();
					
					//ModelImage imageToAdd = null;
					//value table
					if (recordsElement != null)
					{
						int rowCount = 0;
						ArrayList<Element> recordElements = DataImporter.getChildElements(recordsElement);
						for (Element recordElement : recordElements)
						{
							rowCount++;
							Element valuesElement = (Element) recordElement.getElementsByTagName("values").item(0);
							ArrayList<Element> valueElements = DataImporter.getChildElements(valuesElement);
							int fieldCount = 0;
							for (Element valueElement : valueElements)
							{
								String value = valueElement.getTextContent();
								String valueField = fieldElements.get(fieldCount).getElementsByTagName("title").item(0).getTextContent();
								fieldCount++;
								
								ModelValue valueToAdd = new ModelValue(value, valueField, rowCount, imageCount);
								values.add(valueToAdd);
							}
						}
					}
					
					ModelImage imageToAdd = new ModelImage(imageFile, projectCount, 0);
					images.add(imageToAdd);
				}
			}
			
		} 
		catch (ParserConfigurationException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (SAXException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void exportData()
	{
		try 
		{
			db.startTransaction();
			for(ModelUser toAdd : users)
			{
				db.getUserDAO().add(toAdd);
			}
			
			for(ModelProject toAdd : projects)
			{
				db.getProjectDAO().add(toAdd);
			}
			
			for (ModelField toAdd : fields)
			{
				db.getFieldDAO().add(toAdd);
			}
			
			for (ModelImage toAdd : images)
			{
				db.getImageDAO().add(toAdd);
			}
			
			for (ModelValue toAdd : values)
			{
				db.getValueDAO().add(toAdd);
			}
		} 
		catch (DatabaseException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			db.endTransaction(true);
		}
	}

	public static void main(String[] args) 
	{
		DataImporter main = new DataImporter();
		
		main.setup();
		main.importData(args[0]);
		main.exportData();

	}

}
