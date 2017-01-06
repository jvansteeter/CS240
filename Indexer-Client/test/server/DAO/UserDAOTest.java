package server.DAO;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import server.DatabaseException;
import server.DatabaseRep;
import DAO.*;
import model.*;


public class UserDAOTest 
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
	private UserDAO dbUsers;

	@Before
	public void setUp() throws Exception 
	{
		// Delete all Users from the DatabaseRep	
		db = new DatabaseRep();		
		db.startTransaction();
		
		List<ModelUser> Users = db.getUserDAO().getAll();
		
		for (ModelUser c : Users) 
		{
			db.getUserDAO().delete(c);
		}
		
		db.endTransaction(true);

		// Prepare DatabaseRep for test case	
		db = new DatabaseRep();
		db.startTransaction();
		dbUsers = db.getUserDAO();
	}

	@After
	public void tearDown() throws Exception {
		
		// Roll back transaction so changes to DatabaseRep are undone
		db.endTransaction(false);
		
		db = null;
		dbUsers = null;
	}
	
	@Test
	public void testAdd() throws DatabaseException {
		
		ModelUser bob = new ModelUser(1, "jvansteeter", "this", "Joshua", "Van Steeter", "jvansteeter@gmail.com", 10, 0);
		ModelUser amy = new ModelUser(2, "mvansteeter", "this", "Macquel", "Madsen", "mmadsen@gmail.com", 11, 0);
		
		dbUsers.add(bob);
		dbUsers.add(amy);
		
		List<ModelUser> all = dbUsers.getAll();
		assertEquals(2, all.size());
		
		assert(!bob.equals(amy));
		
		assert(all.get(0).equals(bob));
		assert(all.get(1).equals(amy));
		
		boolean foundBob = false;
		boolean foundAmy = false;
		
		for (ModelUser c : all) {
			
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
		
		ModelUser user = new ModelUser("jvansteeter", "this", "Joshua", "Van Steeter", "jvansteeter@gmail.com", 10);
		dbUsers.add(user);
		ModelUser test = dbUsers.get("jvansteeter");
		assertEquals(user, test);
	}
	
	@Test
	public void testGetAll() throws DatabaseException 
	{
		List<ModelUser> all = dbUsers.getAll();
		assertEquals(0, all.size());
		
		ModelUser bob = new ModelUser("jvansteeter", "this", "Joshua", "Van Steeter", "jvansteeter@gmail.com", 10);
		ModelUser amy = new ModelUser("mvansteeter", "this", "Macquel", "Madsen", "mmadsen@gmail.com", 11);
		
		dbUsers.add(bob);
		dbUsers.add(amy);
		
		all = dbUsers.getAll();
		assertEquals(2, all.size());
	}

	@Test
	public void testUpdate() throws DatabaseException 
	{
		
		ModelUser bob = new ModelUser("jvansteeter", "this", "Joshua", "Van Steeter", "jvansteeter@gmail.com", 10);
		ModelUser amy = new ModelUser("mvansteeter", "this", "Macquel", "Madsen", "mmadsen@gmail.com", 11);
		
		dbUsers.add(bob);
		dbUsers.add(amy);
		
		bob.setFirstName("Robert White");
		bob.setPassword("000-000-0000");
		bob.setLastName("12 N 13 W");
		bob.setEmail("robert@white.org");
		bob.setUserName("http://www.white.org/robert");
		
		amy.setFirstName("Amy Wilson White");
		amy.setPassword("111-111-1111");
		amy.setLastName("P.O. Box 9876");
		amy.setEmail("amy.white@white.org");
		amy.setUserName("http://www.white.org/amy.white");
		
		dbUsers.update(bob);
		dbUsers.update(amy);
		
		List<ModelUser> all = dbUsers.getAll();
		assertEquals(2, all.size());
		
		boolean foundBob = false;
		boolean foundAmy = false;
		
		for (ModelUser c : all) {
			
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
		
		ModelUser bob = new ModelUser("jvansteeter", "this", "Joshua", "Van Steeter", "jvansteeter@gmail.com", 10);
		ModelUser amy = new ModelUser("mvansteeter", "this", "Macquel", "Madsen", "mmadsen@gmail.com", 11);
		
		dbUsers.add(bob);
		dbUsers.add(amy);
		
		List<ModelUser> all = dbUsers.getAll();
		assertEquals(2, all.size());
		
		dbUsers.delete(bob);
		dbUsers.delete(amy);
		
		all = dbUsers.getAll();
		assertEquals(0, all.size());
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidAdd() throws DatabaseException {
		
		ModelUser invalidModelUser = new ModelUser(null, null, null, null, null, 0);
		dbUsers.add(invalidModelUser);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidUpdate() throws DatabaseException {
		
		ModelUser invalidModelUser = new ModelUser(null, null, null, null, null, 0);
		dbUsers.update(invalidModelUser);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidDelete() throws DatabaseException {
		
		ModelUser invalidModelUser = new ModelUser(null, null, null, null, null, 0);
		dbUsers.delete(invalidModelUser);
	}
	
	private boolean areEqual(ModelUser a, ModelUser b, boolean compareIDs) 
	{
		if (compareIDs) {
			if (a.getId() != b.getId()) {
				return false;
			}
		}	
		return (a.equals(b));
	}
	
}
