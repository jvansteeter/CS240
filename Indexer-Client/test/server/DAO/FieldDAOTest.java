package server.DAO;

import static org.junit.Assert.*;

import java.util.List;

import model.ModelField;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.DatabaseException;
import server.DatabaseRep;
import DAO.FieldDAO;

public class FieldDAOTest 
{

	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{	
		// Load DatabaseRep driver	
		DatabaseRep.initialize();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception 
	{
		return;
	}
		
	private DatabaseRep db;
	private FieldDAO dbfields;

	@Before
	public void setUp() throws Exception 
	{
		// Delete all fields from the DatabaseRep	
		db = new DatabaseRep();		
		db.startTransaction();
		
		List<ModelField> fields = db.getFieldDAO().getAll();
		
		for (ModelField c : fields) 
		{
			db.getFieldDAO().delete(c);
		}
		
		db.endTransaction(true);

		// Prepare DatabaseRep for test case	
		db = new DatabaseRep();
		db.startTransaction();
		dbfields = db.getFieldDAO();
	}

	@After
	public void tearDown() throws Exception {
		
		// Roll back transaction so changes to DatabaseRep are undone
		db.endTransaction(false);
		
		db = null;
		dbfields = null;
	}
	
	@Test
	public void testAdd() throws DatabaseException {
		
		ModelField bob = new ModelField("bob", 100, 1, 1, "some html", "help data");
		ModelField amy = new ModelField("amy", 200, 12, 2, "other html", "data death row");
		
		dbfields.add(bob);
		dbfields.add(amy);
		
		List<ModelField> all = dbfields.getAll();
		assertEquals(2, all.size());
		
		assert(!bob.equals(amy));
		
		assert(all.get(0).equals(bob));
		assert(all.get(1).equals(amy));
		
		boolean foundBob = false;
		boolean foundAmy = false;
		
		for (ModelField c : all) {
			
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
		ModelField field = new ModelField("bob", 100, 1, 1, "some html", "help data", 50);
		dbfields.add(field);
		ModelField test = dbfields.get("bob", 50);
		assertEquals(field, test);
	}
	
	@Test
	public void testGetAll() throws DatabaseException 
	{
		List<ModelField> all = dbfields.getAll();
		assertEquals(0, all.size());
		
		ModelField bob = new ModelField("bob", 100, 1, 1, "some html", "help data");
		ModelField amy = new ModelField("amy", 200, 12, 2, "other html", "data death row");
		
		dbfields.add(bob);
		dbfields.add(amy);
		
		all = dbfields.getAll();
		assertEquals(2, all.size());
	}

	@Test
	public void testUpdate() throws DatabaseException 
	{
		ModelField bob = new ModelField("bob", 100, 1, 1, "some html", "help data");
		ModelField amy = new ModelField("amy", 200, 12, 2, "other html", "data death row");
		
		dbfields.add(bob);
		dbfields.add(amy);
		
		bob.setTitle("Robert White");
		bob.setXCoord(1000);
		bob.setWidth(521);
		bob.setHelpHTML("garbldy gook");
		bob.setKnownData("know what?");
		
		amy.setTitle("Amy Wilson White");
		amy.setXCoord(11111);
		amy.setWidth(125);
		amy.setHelpHTML("blagarog");
		amy.setKnownData("blah.....");
		
		dbfields.update(bob);
		dbfields.update(amy);
		
		List<ModelField> all = dbfields.getAll();
		assertEquals(2, all.size());
		
		boolean foundBob = false;
		boolean foundAmy = false;
		
		for (ModelField c : all) {
			
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
		
		ModelField bob = new ModelField("bob", 100, 1, 1, "some html", "help data");
		ModelField amy = new ModelField("amy", 200, 12, 2, "other html", "data death row");
		
		dbfields.add(bob);
		dbfields.add(amy);
		
		List<ModelField> all = dbfields.getAll();
		assertEquals(2, all.size());
		
		dbfields.delete(bob);
		dbfields.delete(amy);
		
		all = dbfields.getAll();
		assertEquals(0, all.size());
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidAdd() throws DatabaseException {
		
		ModelField invalidModelField = new ModelField(null, 0, 0, 0, null, null);
		dbfields.add(invalidModelField);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidUpdate() throws DatabaseException {
		
		ModelField invalidModelField = new ModelField(null, 0, 0, 0, null, null);
		dbfields.update(invalidModelField);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidDelete() throws DatabaseException {
		
		ModelField invalidModelField = new ModelField(null, 0, 0, 0, null, null);
		dbfields.delete(invalidModelField);
	}
	
	private boolean areEqual(ModelField a, ModelField b, boolean compareIDs) 
	{
		if (compareIDs) {
			if (a.getId() != b.getId()) {
				return false;
			}
		}	
		return (a.equals(b));
	}

}
