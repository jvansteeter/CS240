package server.DAO;

import static org.junit.Assert.*;

import java.util.List;

import model.ModelImage;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.DatabaseException;
import server.DatabaseRep;
import DAO.ImageDAO;

public class ImageDAOTest 
{

	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{	
		// Load DatabaseRep driver	
		DatabaseRep.initialize();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		return;
	}
		
	private DatabaseRep db;
	private ImageDAO dbimages;

	@Before
	public void setUp() throws Exception 
	{
		// Delete all images from the DatabaseRep	
		db = new DatabaseRep();		
		db.startTransaction();
		
		List<ModelImage> images = db.getImageDAO().getAll();
		
		for (ModelImage c : images) 
		{
			db.getImageDAO().delete(c);
		}
		
		db.endTransaction(true);

		// Prepare DatabaseRep for test case	
		db = new DatabaseRep();
		db.startTransaction();
		dbimages = db.getImageDAO();
	}

	@After
	public void tearDown() throws Exception {
		
		// Roll back transaction so changes to DatabaseRep are undone
		db.endTransaction(false);
		
		db = null;
		dbimages = null;
	}
	
	@Test
	public void testAdd() throws DatabaseException {
		
		ModelImage bob = new ModelImage("bob", 100, 1);
		ModelImage amy = new ModelImage("amy", 200, 12);
		
		dbimages.add(bob);
		dbimages.add(amy);
		
		List<ModelImage> all = dbimages.getAll();
		assertEquals(2, all.size());
		
		assert(!bob.equals(amy));
		
		assert(all.get(0).equals(bob));
		assert(all.get(1).equals(amy));
		
		boolean foundBob = false;
		boolean foundAmy = false;
		
		for (ModelImage c : all) {
			
			assertFalse(c.getId() == -1);
			
			if (!foundBob) {
				foundBob = areEqual(c, bob, false);
			}		
			if (!foundAmy) {
				foundAmy = areEqual(c, amy, false);
			}
		}
		
		assertTrue(foundBob && foundAmy);
	}
	
	@Test
	public void testGet() throws DatabaseException 
	{
		ModelImage image = new ModelImage("bob", 100, 1);
		dbimages.add(image);
		ModelImage test = dbimages.get("bob");
		assertEquals(image, test);
	}
	
	@Test
	public void testGetAll() throws DatabaseException 
	{
		List<ModelImage> all = dbimages.getAll();
		assertEquals(0, all.size());
		
		ModelImage bob = new ModelImage("bob", 100, 1);
		ModelImage amy = new ModelImage("amy", 200, 12);
		
		dbimages.add(bob);
		dbimages.add(amy);
		
		all = dbimages.getAll();
		assertEquals(2, all.size());
	}

	@Test
	public void testUpdate() throws DatabaseException 
	{
		ModelImage bob = new ModelImage("bob", 100, 1);
		ModelImage amy = new ModelImage("amy", 200, 12);
		
		dbimages.add(bob);
		dbimages.add(amy);
		
		bob.setFileName("Robert White");
		bob.setProjectId(1000);
		bob.setCheckedToUser(521);
		
		amy.setFileName("Amy Wilson White");
		amy.setProjectId(11111);
		amy.setCheckedToUser(125);
		
		dbimages.update(bob);
		dbimages.update(amy);
		
		List<ModelImage> all = dbimages.getAll();
		assertEquals(2, all.size());
		
		boolean foundBob = false;
		boolean foundAmy = false;
		
		for (ModelImage c : all) {
			
			if (!foundBob) {
				foundBob = areEqual(c, bob, false);
			}		
			if (!foundAmy) {
				foundAmy = areEqual(c, amy, false);
			}
		}
		
		assertTrue(foundBob && foundAmy);
	}

	@Test
	public void testDelete() throws DatabaseException 
	{
		
		ModelImage bob = new ModelImage("bob", 100, 1);
		ModelImage amy = new ModelImage("amy", 200, 12);
		
		dbimages.add(bob);
		dbimages.add(amy);
		
		List<ModelImage> all = dbimages.getAll();
		assertEquals(2, all.size());
		
		dbimages.delete(bob);
		dbimages.delete(amy);
		
		all = dbimages.getAll();
		assertEquals(0, all.size());
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidAdd() throws DatabaseException {
		
		ModelImage invalidModelImage = new ModelImage(null, 0, 0);
		dbimages.add(invalidModelImage);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidUpdate() throws DatabaseException {
		
		ModelImage invalidModelImage = new ModelImage(null, 0, 0);
		dbimages.update(invalidModelImage);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidDelete() throws DatabaseException {
		
		ModelImage invalidModelImage = new ModelImage(null, 0, 0);
		dbimages.delete(invalidModelImage);
	}
	
	private boolean areEqual(ModelImage a, ModelImage b, boolean compareIDs) 
	{
		if (compareIDs) {
			if (a.getId() != b.getId()) {
				return false;
			}
		}	
		return (a.equals(b));
	}
}
