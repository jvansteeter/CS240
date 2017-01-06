package server.DAO;

import static org.junit.Assert.*;

import java.util.List;

import model.ModelValue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.DatabaseException;
import server.DatabaseRep;
import DAO.ValueDAO;

public class ValueDAOTest {

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
	private ValueDAO dbvalues;

	@Before
	public void setUp() throws Exception 
	{
		// Delete all values from the DatabaseRep	
		db = new DatabaseRep();		
		db.startTransaction();
		
		List<ModelValue> values = db.getValueDAO().getAll();
		
		for (ModelValue c : values) 
		{
			db.getValueDAO().delete(c);
		}
		
		db.endTransaction(true);

		// Prepare DatabaseRep for test case	
		db = new DatabaseRep();
		db.startTransaction();
		dbvalues = db.getValueDAO();
	}

	@After
	public void tearDown() throws Exception {
		
		// Roll back transaction so changes to DatabaseRep are undone
		db.endTransaction(false);
		
		db = null;
		dbvalues = null;
	}
	
	@Test
	public void testAdd() throws DatabaseException {
		
		ModelValue bob = new ModelValue("bob", "of dreams", 87, 50);
		ModelValue amy = new ModelValue("amy", "other dreams", 19, 51);
		
		dbvalues.add(bob);
		dbvalues.add(amy);
		
		List<ModelValue> all = dbvalues.getAll();
		assertEquals(2, all.size());
		
		assert(!bob.equals(amy));
		
		assert(all.get(0).equals(bob));
		assert(all.get(1).equals(amy));
		
		boolean foundBob = false;
		boolean foundAmy = false;
		
		for (ModelValue c : all) {
			
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
		ModelValue value = new ModelValue("bob", "of dreams", 87, 50);
		dbvalues.add(value);
		ModelValue test = dbvalues.get("of dreams", 87, 50);
		assertEquals(value, test);
	}
	
	@Test
	public void testGetAll() throws DatabaseException 
	{
		List<ModelValue> all = dbvalues.getAll();
		assertEquals(0, all.size());
		
		ModelValue bob = new ModelValue("bob", "of dreams", 87, 50);
		ModelValue amy = new ModelValue("amy", "other dreams", 19, 51);
		
		dbvalues.add(bob);
		dbvalues.add(amy);
		
		all = dbvalues.getAll();
		assertEquals(2, all.size());
	}

	@Test
	public void testUpdate() throws DatabaseException 
	{
		ModelValue bob = new ModelValue("bob", "of dreams", 87, 50);
		ModelValue amy = new ModelValue("amy", "other dreams", 19, 51);
		
		dbvalues.add(bob);
		dbvalues.add(amy);
		
		bob.setValue("Robert White");
		bob.setField("1000");
		bob.setRow(521);
		bob.setImageId(1);
		
		amy.setValue("Amy Wilson White");
		amy.setField("11111");
		amy.setRow(125);
		amy.setImageId(2);
		
		dbvalues.update(bob);
		dbvalues.update(amy);
		
		List<ModelValue> all = dbvalues.getAll();
		assertEquals(2, all.size());
		
		boolean foundBob = false;
		boolean foundAmy = false;
		
		for (ModelValue c : all) {
			
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
		
		ModelValue bob = new ModelValue("bob", "of dreams", 87, 50);
		ModelValue amy = new ModelValue("amy", "other dreams", 19, 51);
		
		dbvalues.add(bob);
		dbvalues.add(amy);
		
		List<ModelValue> all = dbvalues.getAll();
		assertEquals(2, all.size());
		
		dbvalues.delete(bob);
		dbvalues.delete(amy);
		
		all = dbvalues.getAll();
		assertEquals(0, all.size());
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidAdd() throws DatabaseException {
		
		ModelValue invalidModelValue = new ModelValue(null, null, 0, 0);
		dbvalues.add(invalidModelValue);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidUpdate() throws DatabaseException {
		
		ModelValue invalidModelValue = new ModelValue(null, null, 0, 0);
		dbvalues.update(invalidModelValue);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidDelete() throws DatabaseException {
		
		ModelValue invalidModelValue = new ModelValue(null, null, 0, 0);
		dbvalues.delete(invalidModelValue);
	}
	
	private boolean areEqual(ModelValue a, ModelValue b, boolean compareIDs) 
	{
		if (compareIDs) {
			if (a.getId() != b.getId()) {
				return false;
			}
		}	
		return (a.equals(b));
	}


}
